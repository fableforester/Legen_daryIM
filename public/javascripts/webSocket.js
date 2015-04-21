/**
 * Created by alexanderberg on 21.04.15.
 */

var wsUri = "ws://localhost:9000/wstest";
var output;

function init() {
    output = document.getElementById("output");
    testWebSocket();
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
}
function onOpen(evt) {
    writeToScreen("CONNECTED");
    doSend("WebSocket rocks");
}
function onClose(evt) {
    writeToScreen("DISCONNECTED");
}
function onMessage(evt) {
    writeToScreen('<span style="color: blue;">RESPONSE: ' + evt.data + '</span>');
    //websocket.close();
}
function onError(evt) {
    writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
}
function doSend() {
    var e = document.getElementById("kontaktListe");
    var strUser = e.options[e.selectedIndex].text;


    var message = document.getElementById('message').value
    writeToScreen("SENT TO "+ strUser + ": " + message);
    websocket.send(message);
}
function writeToScreen(message) {
    var pre = document.createElement("p");
    //var pre = document.body.createElement("div");
    pre.style.wordWrap = "break-word";
    pre.innerHTML = message;
    output.appendChild(pre);
}

window.addEventListener("load", init, false);