/**
 * Created by alexanderberg on 07.05.15.
 */

function init() {
    initChangedHandler();
}

function writeMessageToScreen(message) {
    var pre = document.createElement("p");
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

window.addEventListener("load", init, false);