const baseAPIURL = 'http://localhost:8080/';
var globalUserName = '';
var stompClient;
var chatRoomCode = '';
var codes = [];

function setCookie(key, value) {
	document.cookie = key + "=" + value + ";";
}

function getCookie(key) {
	let name = key + "=";
	let decodedCookie = decodeURIComponent(document.cookie);
	let  cookie = decodedCookie.split(';');
	for(let i = 0; i <cookie.length; i++) {
        let c = cookie[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
	}
	return "";
}


$(document).ready( () => {
    $('#register-input').val(getCookie('username'));

    $('#register-input-submit').click( () => {
        let user = $('#register-input').val().trim();
        if(user != '') {
            registerUser(user.trim());
        }
    });

    $('#create-room-button').click( () => {
        if(globalUserName != '') {
            createRoom();
        }
    });

    $('#join-room-input-submit').click( () => {
        let code = $('#join-room-input').val().trim();
        if(globalUserName != '' && code.length === 6) {
            chatRoomCode = code;
            connectChat(code);
        }
    });

    $('#send-chat-button').click( async () => {
        let chat = $('#send-chat-input').val().trim();
        if(chat != '' && globalUserName != '' && chatRoomCode.length === 6) {
            await sendMessage(chat);
            $('#send-chat-input').val('');
        }
    });
});

async function registerUser(userName) {
    $.ajax({
        type: "GET",
        url: baseAPIURL+`register/${userName}`,
        crossDomain: true,
        contentType: "application/json",
        success: async function(data, status, xhr)    {
            registrationErrorMesage(false);
            globalUserName = userName;
            setCookie('username', userName);
        },
        error: function(xhr, status, error) {
            console.log(xhr.status);
            if(xhr.status === 409) {
                registrationErrorMesage(true);
            }
        }
    });
}

function registrationErrorMesage(fail) {
    if(fail) {
        $('#register-input-error').css('display', 'block');
    }
    else {
        $('#register-input-error').css('display', 'none');
        $('#register-container').css('display', 'none');
        $('#chat-connect-container').css('display', 'flex');
    }
}

function createRoom() {
    $.ajax({
        type: "POST",
        url: baseAPIURL+`createRoom/${globalUserName}`,
        crossDomain: true,
        contentType: "application/json",
        success: async function(data, status, xhr)    {
            console.log(data['roomCode']);
            chatRoomCode = data['roomCode'];
            codes.push(chatRoomCode);
            $('#generated-code').text(chatRoomCode);
            $('#join-room-input').val(chatRoomCode);
        },
        error: function(xhr, status, error) {
            console.log(xhr.status);
        }
    });
}

function connectChat(code) {
    console.log('Connecting to chat...');
    $('#current-code').text(`Current Code: ${code}`);
    let socket = new SockJS(baseAPIURL+'chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({'login': globalUserName}, (frame) => {
        console.log(`Connected to: ${frame}`);
        // Subscribe to proper chat room
        
        let groupChat = stompClient.subscribe(`/topic/messages/${code}`, (response) => {
            let data = JSON.parse(response.body);
            displayChat(data);
            //console.log(data);
        });


    });
}

function sendMessage(message) {
    stompClient.send(`/app/room/${chatRoomCode}`, {}, JSON.stringify({
        'fromLogin': globalUserName,
        'message': message
    }));
}

function displayChat(chatArray) {
    let chatHTML = '';
    for(let i=0; i<chatArray.length; i++) {
        if(chatArray[i]['fromLogin'] === globalUserName) {
        chatHTML += `<div class="chat-container-self">
                        <p class="message">${chatArray[i]['message']}</p>
                        <p class="sender">${chatArray[i]['fromLogin']}</p>
                    </div>`;
        }
        else {
            chatHTML += `<div class="chat-container">
                            <p class="sender">${chatArray[i]['fromLogin']}</p>
                            <p class="message">${chatArray[i]['message']}</p>
                        </div>`;
        }
    }
    $('#display-chats-container').html(chatHTML);
}