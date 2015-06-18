
var wsUri = "ws://localhost:9000/webSocket";
var confirmationWsUri = "ws://localhost:9000/ConfirmationWebSocket";

var output;
var websocket = new WebSocket(wsUri);
var confirmationWebsocket = new WebSocket(confirmationWsUri);

function init() {
    output = document.getElementById("chatBox");
    testWebSocket();
}

function testWebSocket() {

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

    confirmationWebsocket.onmessage = function (evt) {
        confirmationRecieved(evt);
    };


    confirmationWebsocket.onerror = function (evt) {
        onError(evt);
    };
}

function closeWebsocket() {
    console.log("CLOSED");
    websocket.close();
}

function onOpen(evt) {
    console.log("CONNECTED");
}

function onClose(evt) {
    console.log("DISCONNECTED");
}

function onMessage(evt) {
    writeRecievedMessageToScreen(evt);
    confirmationWebsocket.send(evt.data);
}

function onError(evt) {
    alert(evt);
}

function doSend() {
    var kontaktListeElement = document.getElementById('kontaktListe');

    try {
        var empfangerUserName = kontaktListeElement.options[kontaktListeElement.selectedIndex].innerHTML;
    } catch (err) {
        alert("Bitte Empfänger auswählen");
    }

    var empfangerEmail = kontaktListeElement.options[kontaktListeElement.selectedIndex].getAttribute("id");

    var message = document.getElementById('message').value;
    document.getElementById('message').value = '';
    var date = new Date();

    var nachricht = new Object();
    nachricht.empfaenger = empfangerEmail;
    nachricht.sender = document.getElementById('userEmail').innerHTML;
    nachricht.text = message;
    nachricht.gesendetAm = date.getTime();

    var jsonString= JSON.stringify(nachricht);

    writeSendedMessageToScreen(nachricht);
    websocket.send(jsonString);
}

function confirmationRecieved(evt){
    alert(evt.data);
}

window.addEventListener("load", init, false);