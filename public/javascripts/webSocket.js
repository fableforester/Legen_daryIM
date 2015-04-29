
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
    var e = document.getElementById('kontaktListe');
    var strUser = e.options[e.selectedIndex].text;
    var message = document.getElementById('message').value;
    var d = new Date();

    var obj = new Object();
    obj.empfaenger = strUser;
    obj.nachricht = message;
    obj.gesendetAm = d.getTime();

    var jsonString= JSON.stringify(obj);

    writeToScreen("SENT TO "+ strUser + ": " + message);
    websocket.send(jsonString);
}
function writeToScreen(message) {
    var pre = document.createElement("p");

    pre.style.wordWrap = "break-word";
    pre.innerHTML = message;
    output.appendChild(pre);
}

function initChangedHandler() {
    var sel = document.getElementById('kontaktListe');
    sel.onchange = function() {
        output.innerHTML = "";
        writeToScreen('Kontakt zu ' + sel.options[sel.selectedIndex].text + ' geändert');
    }
}

window.addEventListener("load", init, false);