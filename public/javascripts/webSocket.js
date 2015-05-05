
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
    writeToScreen("CONNECTED");
}
function onClose(evt) {
    writeToScreen("DISCONNECTED");
}
function onMessage(evt) {
    writeToScreen('<span style="color: blue;">RESPONSE: ' + evt.data + '</span>');
}
function onError(evt) {
    writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
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

    writeToScreen("SENT TO "+ empfangerString + ": " + message);
    websocket.send(jsonString);
}
function writeToScreen(message) {
    var pre = document.createElement("p");

    pre.style.wordWrap = "break-word";
    pre.innerHTML = message;
    output.appendChild(pre);
}

function initChangedHandler() {
    var kontaktListeElement = document.getElementById('kontaktListe');
    kontaktListeElement.onchange = function() {
        output.innerHTML = "";
        writeToScreen('Kontakt zu ' + kontaktListeElement.options[kontaktListeElement.selectedIndex].text + ' geändert');
    }
}

window.addEventListener("load", init, false);