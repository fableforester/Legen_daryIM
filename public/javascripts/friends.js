/**
 * Created by alexanderberg on 07.05.15.
 */

function doSendAjaxToAddFriend( ) {
    //TODO selektierter kontakt wird in die kontaktliste hinzugefügt und verschwindet aus dem suchergebnis
    var emailOfFriendField = document.getElementById('userToAdd');
    var email = emailOfFriendField.value;
    appRoutes.controllers.Application.addFriend(email).ajax( {
        success : function ( data ) {
            var emailOption = document.createElement("option");
            emailOption.innerHTML = email;
            var kontaktListeElement = document.getElementById('kontaktListe');
            kontaktListeElement.appendChild(emailOption);
            emailOfFriendField.value = "";
        }
    });
}

function searchForFriends( ) {
    //TODO so umwandeln dass die Liste von emails in das select hinzugefügt wird
    var emailOfFriendField = document.getElementById('userSearchInput');
    var email = emailOfFriendField.value;
    appRoutes.controllers.Application.searchUsers(email).ajax( {
        success : function ( data ) {
            var emailOption = document.createElement("option");
            emailOption.innerHTML = email;
            var kontaktListeElement = document.getElementById('kontaktListe');
            kontaktListeElement.appendChild(emailOption);
            emailOfFriendField.value = "";
        }
    });
}
