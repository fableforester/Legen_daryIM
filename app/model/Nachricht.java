package model;

/**
 * Created by Fabian on 10.05.2015.
 */
public class Nachricht {

    int id;
    String text;
    String empfaenger;
    String sender;

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

    public String getEmpfaenger() {
        return empfaenger;
    }

    public void setEmpfaenger(String empfaenger) {
        this.empfaenger = empfaenger;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

}
