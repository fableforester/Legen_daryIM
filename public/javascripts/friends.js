/**
 * Created by alexanderberg on 07.05.15.
 */

function doSendAjaxToAddFriend( ) {
    var userToAddField = document.getElementById('userToAdd');
    var email = userToAddField.value;
    appRoutes.controllers.Application.addFriend(email).ajax( {
        success : function ( data ) {
            var emailOption = document.createElement('option');
            emailOption.innerHTML = data.name;
            var kontaktListeElement = document.getElementById('kontaktListe');
            kontaktListeElement.appendChild(emailOption);
            userToAddField.value = "";
        },
        error: function ( data ) {
            alert("Benutzer nicht gefunden");
        }
    });
}

