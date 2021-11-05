package dsn;

import javax.swing.*;
import java.util.Date;

public class Msg {
    User from;
    User to;
    String text;
    Date date;
    public Msg(User from, User to, String text, Date date) {
        this.from = from;
        this.to = to;
        this.text = text;
        this.date = (Date)date.clone();
    }
    public String toString(){
        return from+":"+date.toString()+":"+text;
    }
    public JLabel toComponent(){
        return new JLabel(toString());
    }
}
