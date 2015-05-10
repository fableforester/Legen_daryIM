package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BenutzerDao {

    List<Benutzer> users = new ArrayList<Benutzer>();
    boolean hasDummyData = false;

    //Erstelle dummy Daten für die Dummy Datenbank...
    public void createDummyData(){
        users.add(new Benutzer(0, "Moritz", "moritz@web.de", "geheim"));
        users.add(new Benutzer(1, "Fabian", "fabian@web.de", "geheim"));
        users.add(new Benutzer(1, "Alex", "alex@web.de", "geheim"));
        users.get(0).addKontakt(users.get(1));
        users.get(0).addKontakt(users.get(2));
        users.get(1).addKontakt(users.get(0));
        users.get(1).addKontakt(users.get(2));
        users.get(2).addKontakt(users.get(0));
        users.get(2).addKontakt(users.get(1));
    }

    public boolean validateUser(String email, String password) {
        //TODO Benutzer validieren
        return true;
    }

    //TODO Benutzer übergeben, dafür erstmal alles auf Benutzer-Struktur umstellen
    public List<String> getFriends(){
        //TODO Freunde aus der Tabelle abrufen
        if(!hasDummyData)
            createDummyData();
        LinkedList<String> friends = new LinkedList<>();
        for(Benutzer user : users.get(0).getKontaktliste()){
            friends.add(user.getEmail());
        }
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
