package model;

import java.util.LinkedList;
import java.util.List;

public class BenutzerDao {
    public boolean validateBenutzer(String email, String password){
        return true;
    }

    public List<String> getFriends(){
        LinkedList<String> friends = new LinkedList<>();
        friends.add("moritz@web.de");
        friends.add("fabian@web.de");
        friends.add("alex@web.de");
        return friends;
    }
}
