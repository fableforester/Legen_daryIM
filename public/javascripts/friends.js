/**
 * Created by alexanderberg on 07.05.15.
 */

var friends;

function init() {
    addChatWindows();
    getFriends();
}

function doSendAjaxToAddFriend( ) {
    var emailOfFriendField = document.getElementById('userToAdd');
    var email = emailOfFriendField.value;
    appRoutes.controllers.Application.addFriend(email).ajax( {
        success : function ( data ) {
            var emailOption = document.createElement('option');
            emailOption.innerHTML = data.name;
            var kontaktListeElement = document.getElementById('kontaktListe');
            kontaktListeElement.appendChild(emailOption);
            emailOfFriendField.value = "";
        }
    });
}

function addChatWindows(){
    var emailOfFriendField = document.getElementById('kontaktListe');
    emailOfFriendField.getElementsByTagName('option');
    for (var i = 0; i < emailOfFriendField.length; i++) {
        var friendUserName = emailOfFriendField[i].innerHTML;

        var friendChatWindow = document.createElement('div');
        friendChatWindow.setAttribute("id", friendUserName);

        var chatBox = document.getElementById('chat');
        chatBox.appendChild(friendChatWindow);

    }
}

function getFriends() {
    appRoutes.controllers.Application.getFriends().ajax( {
        success : function ( data ) {
            friends = data;
        }
    });
}

window.addEventListener("load", init, false);

