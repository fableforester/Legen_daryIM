
var wsUri = "ws://localhost:9000/webSocket";
var output;

function init() {
    output = document.getElementById("chatBox");
    testWebSocket();
    initChangedHandler();
}
function testWebSocket() {
    websocket = new WebSocket(wsUri);

    websocket.onopen = function (evt) {
        onOpen(evt);
    };
    websocket.onclose = function (evt) {
        onClose(evt);
    };
    websocket.onmessage = function (evt) {
        onMessage(evt);
    };
    websocket.onerror = function (evt) {
        onError(evt);
    };

    //websocket.close();
}
function onOpen(evt) {
    writeMessageToScreen("CONNECTED");
}
function onClose(evt) {
    writeMessageToScreen("DISCONNECTED");
}
function onMessage(evt) {
    writeMessageToScreen('<span style="color: blue;">RESPONSE: ' + evt.data + '</span>');
}
function onError(evt) {
    writeMessageToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
}
function doSend() {
    var kontaktListeElement = document.getElementById('kontaktListe');
    var empfangerString = kontaktListeElement.options[kontaktListeElement.selectedIndex].text;
    var message = document.getElementById('message').value;
    var date = new Date();

    var nachricht = new Object();
    nachricht.empfaenger = empfangerString;
    nachricht.sender = document.getElementById('userName').innerHTML;
    nachricht.text = message;
    nachricht.gesendetAm = date.getTime();

    var jsonString= JSON.stringify(nachricht);

    writeMessageToScreen("SENT TO "+ empfangerString + ": " + message);
    websocket.send(jsonString);
}
function writeMessageToScreen(message) {
    var pre = document.createElement("p");

    pre.style.wordWrap = "break-word";
    pre.innerHTML = message;
    output.appendChild(pre);
}

function initChangedHandler() {
    var kontaktListeElement = document.getElementById('kontaktListe');
    kontaktListeElement.onchange = function() {
        output.innerHTML = "";
        writeMessageToScreen('Kontakt zu ' + kontaktListeElement.options[kontaktListeElement.selectedIndex].text + ' geändert');
    }
}

function doSendAjaxToAddFriend( ) {
    var emailOfFriendField = document.getElementById('userToAdd');
    var email = emailOfFriendField.value;
    appRoutes.controllers.Application.addFriend(email).ajax( {
        success : function ( data ) {
            var emailOption = document.createElement("option");
            emailOption.innerHTML = email;
            var kontaktListeElement = document.getElementById('kontaktListe');
            kontaktListeElement.appendChild(emailOption);
            emailOfFriendField.value = "";
        }
    });
}

window.addEventListener("load", init, false);