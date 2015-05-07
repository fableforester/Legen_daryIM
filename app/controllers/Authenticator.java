package controllers;

import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

public class Authenticator extends Security.Authenticator {

    @Override
    public String getUsername(Context context) {
        String userName = context.session().get("email");

        /*
        Falls der Benutzername leer ist muss null returned werden
        damit der Authenticator greift
         */
        if (userName.equals(""))
            return null;
        return userName;
    }

    /*
    Unautorisierte Benutzer werden zum LogIn weitergeleitet
     */
    @Override
    public Result onUnauthorized(Context context) {
        return redirect("/login");

    }
}