package model;

/**
 * Created by Fabian on 10.05.2015.
 */
public class Nachricht {

    int id;
    String text;

    public Nachricht(int id, String text){
        this.id = id;
        this.text=text;
    }

    public int getId(){
        return id;
    }

    public String getText(){
        return text;
    }

}
