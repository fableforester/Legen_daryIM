/**
 * Created by alexanderberg on 07.05.15.
 */

function init() {
    initChangedHandler();
}

function writeMessageToScreen(message) {

    //var pre = document.createElement("p");
    //pre.innerHTML = message;
    //output.appendChild(pre);
     var newcontent = document.createElement("p");
    newcontent.id = 'message_id';
    newcontent.appendChild(document.createTextNode(message));

     /*
    var theDiv = document.getElementById("chat");
    var content = document.createTextNode(message+"<br>");
    theDiv.appendChild(content);*/


    /* var scr = document.getElementById('chat');
    scr.parentNode.insertBefore(newcontent, scr);

     */
     var div = document.getElementById('chat');
     div.innerHTML = div.innerHTML + message+'<br>';
     div.scrollTop = div.scrollHeight;





}

function initChangedHandler() {
    var kontaktListeElement = document.getElementById('kontaktListe');
    kontaktListeElement.onchange = function() {
       // output.innerHTML = "";
        writeMessageToScreen('Kontakt zu ' + kontaktListeElement.options[kontaktListeElement.selectedIndex].text + ' geändert');

    }
}

window.addEventListener("load", init, false);