package model;

import java.util.LinkedList;
import java.util.List;

public class BenutzerDao {

    public boolean validateUser(String email, String password){
        //TODO Benutzer validieren
        return true;
    }

    public List<String> getFriends(){
        //TODO Freunde aus der Tabelle abrufen
        LinkedList<String> friends = new LinkedList<>();
        friends.add("moritz@web.de");
        friends.add("fabian@web.de");
        friends.add("alex@web.de");
        return friends;
    }

    public boolean registerUser(String email, String password) {
        //TODO Benutzer anlegen
        return true;
    }

    //Schaut ob der User existiert
    public boolean existUser(String email) {
        return true;
    }
}
