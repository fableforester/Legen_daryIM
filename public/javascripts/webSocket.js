
var wsUri = "ws://localhost:9000/webSocket";
var output;
websocket = new WebSocket(wsUri);

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
    writeUserMessageToScreen(evt);
}

function onError(evt) {
    writeMessageToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
}

function doSend() {
    var kontaktListeElement = document.getElementById('kontaktListe');
    var empfangerUserName = kontaktListeElement.options[kontaktListeElement.selectedIndex].innerHTML;
    var empfangerEmail = kontaktListeElement.options[kontaktListeElement.selectedIndex].getAttribute("id");

    var message = document.getElementById('message').value;
    var date = new Date();

    var nachricht = new Object();
    nachricht.empfaenger = empfangerEmail;
    nachricht.sender = document.getElementById('userEmail').innerHTML;
    nachricht.text = message;
    nachricht.gesendetAm = date.getTime();

    var jsonString= JSON.stringify(nachricht);

    writeMessageToScreen('<span style="color: blue;">Gesendet an '+ empfangerUserName + ': </span>' + message);
    websocket.send(jsonString);
}

window.addEventListener("load", init, false);