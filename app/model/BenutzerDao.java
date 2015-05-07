package model;

import java.util.LinkedList;
import java.util.List;

public class BenutzerDao {

    public boolean validateBenutzer(String email, String password){
        //TODO Benutzer validieren
        return true;
    }

    public List<String> getFreunde(){
        //TODO Freunde aus der Tabelle abrufen
        LinkedList<String> friends = new LinkedList<>();
        friends.add("moritz@web.de");
        friends.add("fabian@web.de");
        friends.add("alex@web.de");
        return friends;
    }

    public boolean registerBenutzer(String email, String password) {
        //TODO Benutzer anlegen
        return false;
    }
}
