/**
 * Created by alexanderberg on 07.05.15.
 */

function init() {
    initChangedHandler();
}

function writeSendedMessageToScreen(message) {

    var uDiv = document.getElementById(message.empfaenger + 'Chat');
    var mDiv = document.createElement("div");

    var date = new Date(message.gesendetAm);

    mDiv.innerHTML = '<span style="color: blue;">' + date.toTimeString() + '</br> Gesendet an '+ message.empfaenger + ': </span>' + message.text

    uDiv.appendChild(mDiv);

}


function writeRecievedMessageToScreen(evt) {
    var msg = JSON.parse(evt.data);
    var div = document.getElementById(msg.sender + "Chat");
    var date = new Date(msg.gesendetAm);

    var messageDiv = document.createElement("div");
    messageDiv.innerHTML = '<span style="color: green;">' + date.toTimeString() + '</br> Empfangen von '+ msg.sender + ': </span>' + msg.text + '';
    div.appendChild(messageDiv);

}

function initChangedHandler() {
    var kontaktListeElement = document.getElementById('kontaktListe');
    kontaktListeElement.onchange = function() {
        var benutzerAuswahl = kontaktListeElement.options[kontaktListeElement.selectedIndex].text;

        for (var i = 0; i < kontaktListeElement.options.length; i++) {
            var id = kontaktListeElement.options[i].getAttribute("id") + 'Chat';
            var el = document.getElementById(id);

            if (i != kontaktListeElement.selectedIndex) {
                el.style.display = 'none';
            } else {
                el.style.display = 'block';
            }
        }
    }
}

window.addEventListener("load", init, false);