/**
 * Created by alexanderberg on 07.05.15.
 */

function doSendAjaxToAddFriend( ) {
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
