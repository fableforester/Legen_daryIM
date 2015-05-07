
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
    websocket.close();
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

window.addEventListener("load", init, false);