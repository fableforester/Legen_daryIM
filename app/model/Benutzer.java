package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fabian on 10.05.2015.
 */
public class Benutzer {

    String name;
    String email;
    String passwort;
    List<Benutzer> kontaktliste = new ArrayList<>();

    public Benutzer(String name, String email, String passwort){
        this.name = name;
        this.email = email;
        this.passwort = passwort;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public String getPasswort(){
        return passwort;
    }

    public boolean addKontakt(Benutzer user){
        kontaktliste.add(user);
        return true;
    }

    public boolean removeKontakt(Benutzer user){
        kontaktliste.remove(user);
        return true;
    }

    public List<Benutzer> getKontaktliste(){
        return kontaktliste;
    }

}
