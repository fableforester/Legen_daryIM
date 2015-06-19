package model;

import jdbc.JdbcCypherExecutor;
import org.neo4j.helpers.collection.IteratorUtil;
import org.neo4j.helpers.collection.MapUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BenutzerDao {

    static JdbcCypherExecutor exec = new JdbcCypherExecutor("http://localhost:7474", "neo4j", "mypass");

    private static BenutzerDao benutzerDao;

    public static BenutzerDao getInstance(){
        if(benutzerDao == null){
            benutzerDao = new BenutzerDao();
        }
        return benutzerDao;
    }

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

    
    public List<Benutzer> getFriends(String email){
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

    public Benutzer createLink(String email1, String email2) {
        Map result = null;
        if (existLink(email1, email2) || email1==null || email2==null || !existUser(email1) || !existUser(email2)) return null;
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

        return getUser(email2);
    }

    public boolean persistMessage(String sender, String empfaenger, String message, String timestamp){
        Map result = null;
        if (!existLink(sender, empfaenger) || sender==null || empfaenger==null || "".equals(sender) || "".equals(empfaenger) || !existUser(sender) || !existUser(empfaenger)) return false;
        try{
            result = IteratorUtil.singleOrNull(exec.query(
                    "MATCH (sender:Benutzer {email:{2}}) " +
                            "MATCH (empfaenger:Benutzer {email:{3}}) " +
                            "CREATE (message:Nachricht {text:{1}, timestamp:{4}})" +
                            "CREATE sender-[:SENDET_NACHRICHT]->message " +
                            "CREATE empfaenger-[:EMPFAENGT_NACHRICHT]->message " +
                            "RETURN message",
                    MapUtil.map("1", message, "2", sender, "3", empfaenger, "4", timestamp)));
        }catch(Exception e){
            return false;
        }

        return true;
    }

    //Parser, um die abgefragten User zu Benutzer zu parsen.
    //WELL FORMED STRING: {name=name, email=email, passwort=passwort)
    private Benutzer parseUser(String user){
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

    public boolean existLink(String email1, String email2){
        Map result = null;
        if (email1==null || email2==null || !existUser(email1) || !existUser(email2)) return false;
        try{
            result = IteratorUtil.singleOrNull(exec.query(
                    "MATCH (usereins:Benutzer {email:{1}}) " +
                            "MATCH (userzwei:Benutzer {email:{2}}) " +
                            "MATCH usereins-[:BEFREUNDET_MIT]->userzwei " +
                            "RETURN count(*)",
                    MapUtil.map("1", email1, "2", email2)));
        }catch(Exception e){
            return false;
        }
        System.out.println("Count: "+Integer.parseInt(result.get("count(*)").toString()));
        if(Integer.parseInt(result.get("count(*)").toString())>0)
            return true;
        return false;
    }

    //Schaut ob der User existiert
    public boolean existUser(String email) {
        if(getUser(email)!=null)
            return true;
        return false;
    }

    public boolean resetDB(){
        Map result = null;
        try{
            result = IteratorUtil.singleOrNull(exec.query(
                            "MATCH n-[r]-m " +
                            "DELETE n, r, m; ",
                    MapUtil.map()));
        }catch(Exception e){
            return false;
        }
        try{
            result = IteratorUtil.singleOrNull(exec.query(
                            "MATCH (x:Benutzer) " +
                            "DELETE x;",
                    MapUtil.map()));
        }catch(Exception e){
            return false;
        }
        try{
            result = IteratorUtil.singleOrNull(exec.query(
                    "CREATE (alex:Benutzer { name:\"Alex Berg\", email:\"alex@web.de\", passwort:\"geheim\"})\n" +
                            "CREATE (moritz:Benutzer { name:\"Moritz Hielscher\", email:\"moritz@web.de\", passwort:\"geheim\"})\n" +
                            "CREATE (fabian:Benutzer { name:\"Fabian Lewalder\", email:\"fabian@web.de\", passwort:\"geheim\"})\n" +
                            "CREATE (testnutzer:Benutzer { name:\"Test\", email:\"email1@web.de\", passwort:\"geheim\"})\n" +
                            "CREATE (alex)-[:BEFREUNDET_MIT]->(moritz)\n" +
                            "CREATE (moritz)-[:BEFREUNDET_MIT]->(alex)\n" +
                            "CREATE (alex)-[:BEFREUNDET_MIT]->(fabian)\n" +
                            "CREATE (fabian)-[:BEFREUNDET_MIT]->(alex)",
                    MapUtil.map()));
        }catch(Exception e){
            return false;
        }
        return true;
    }

}
