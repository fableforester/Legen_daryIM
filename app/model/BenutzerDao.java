package model;

import jdbc.JdbcCypherExecutor;
import org.neo4j.helpers.collection.IteratorUtil;
import org.neo4j.helpers.collection.MapUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BenutzerDao {

    static JdbcCypherExecutor exec = new JdbcCypherExecutor("http://localhost:7474", "neo4j", "mypass");

    /*List<Benutzer> users = new ArrayList<Benutzer>();
    boolean hasDummyData = false;

    //Erstelle dummy Daten fuer die Dummy Datenbank...
    public void createDummyData(){
        users.add(new Benutzer("Moritz", "moritz@web.de", "geheim"));
        users.add(new Benutzer("Fabian", "fabian@web.de", "geheim"));
        users.add(new Benutzer("Alex", "alex@web.de", "geheim"));
        users.get(0).addKontakt(users.get(1));
        users.get(0).addKontakt(users.get(2));
        users.get(1).addKontakt(users.get(0));
        users.get(1).addKontakt(users.get(2));
        users.get(2).addKontakt(users.get(0));
        users.get(2).addKontakt(users.get(1));
        hasDummyData = true;
    }*/

    public boolean validateUser(String email, String password) {
        Benutzer user = getUser(email);
        if(user != null) {
            if (user.getPasswort().equals(password))
                return true;
            else
                return false;
        }
        return false;
    }

    //TODO Benutzer uebergeben, dafuer erstmal alles auf Benutzerstruktur umstellen
    public List<Benutzer> getFriends(String email){
        //TODO siehe oben, diese Email ist erstmal irgendeine
        List<Benutzer> kontakte = new ArrayList<Benutzer>();
        if (email==null) return null;
        Iterable<Map<String,Object>> result = IteratorUtil.asCollection(exec.query(
                "MATCH (user:Benutzer {email:{1}}) " +
                        "MATCH user-[:BEFREUNDET_MIT]->friends " +
                        "RETURN friends",
                MapUtil.map("1", email)));
        if(result==null) return null;
        for(Map<String, Object> kontakt: result){
            String user = kontakt.get("friends").toString();
            kontakte.add(parseUser(user));
        }
        return kontakte;
    }

    public Benutzer getUser(String email) {
        if (email==null) return null;
        Map result = IteratorUtil.singleOrNull(exec.query(
                "MATCH (user:Benutzer {email:{1}}) " +
                        "RETURN user",
                MapUtil.map("1", email)));
        if(result==null || result.isEmpty()) return null;
        String user = result.get("user").toString();
        return parseUser(user);
    }

    public boolean registerUser(String name, String email, String password) {
        //Test, ob es den User schon gibt
        Map result = null;
        if(name == null || email == null || password == null || getUser(email)!=null){
            return false;
        }
        try{
            result = IteratorUtil.singleOrNull(exec.query(
                    "CREATE (user:Benutzer {name:{1}, email:{2}, passwort:{3}}) " +
                            "RETURN user",
                    MapUtil.map("1", name, "2", email, "3", password)));
        }catch(Exception e){
            return false;
        }
        if(result==null || result.isEmpty()) return false;
        return true;
    }

    public String createLink(String email1, String email2) {
        Map result = null;
        if (email1==null || email2==null || !existUser(email1) || !existUser(email2)) return null;
        try{
            result = IteratorUtil.singleOrNull(exec.query(
                    "MATCH (usereins:Benutzer {email:{1}}) " +
                            "MATCH (userzwei:Benutzer {email:{2}}) " +
                            "CREATE usereins-[:BEFREUNDET_MIT]->userzwei " +
                            "CREATE userzwei-[:BEFREUNDET_MIT]->usereins " +
                            "RETURN userzwei",
                    MapUtil.map("1", email1, "2", email2)));
        }catch(Exception e){
            return null;
        }
        String user = result.get("user").toString();
        return parseUser(user).getName();
    }

    //Schaut ob der User existiert
    public boolean existUser(String email) {
        if(getUser(email)!=null)
            return true;
        return false;
    }

    //Parser, um die abgefragten User zu Benutzer zu parsen.
    private static Benutzer parseUser(String user){
        int namestartindex=user.toString().indexOf("name=")+5;
        int nameendindex=user.toString().indexOf(",", namestartindex);
        String name = user.substring(namestartindex, nameendindex);
        int emailstartindex=user.toString().indexOf("email=")+6;
        int emailendindex=user.toString().indexOf(",", emailstartindex);
        String email = user.substring(emailstartindex, emailendindex);
        int passwortstartindex=user.toString().indexOf("passwort=")+9;
        int passwortendindex=user.toString().indexOf("}", passwortstartindex);
        String passwort = user.substring(passwortstartindex, passwortendindex);
        return new Benutzer(name, email, passwort);
    }

}
