package com.example.debugfx;

import javafx.beans.property.*;

import java.util.Timer;
import java.util.TimerTask;

public class Users  {

    private IntegerProperty id;
    private StringProperty name, email;
    private BooleanProperty active;
    private Pinger pinger;
    private Timer pingerTimer;
    Integer pingCount = 0;


    public Users( Integer id, String name, String email) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.active = new SimpleBooleanProperty(false);
        this.pinger = new Pinger(name);

        class PingerTask extends TimerTask{
            @Override
            public void run() {
                pingCount++;
                pinger.ping();
                System.out.println("Count "+pingCount);


            }
        }

        active.addListener( observable -> {
            System.out.println("changed " + active.get());

                if (active.get()){
                    this.pingerTimer = new Timer();
                    pingerTimer.schedule(new PingerTask(),0, 1000);

                } else {

                    pingerTimer.cancel();
                    System.out.println("pingerTimer canceled");
                }
            }
        );
    }




    public void setId(Integer value){
        id.set(value);
    }
    public Integer getUserId(){
        return id.get();
    }
    public IntegerProperty idProperty(){
        return id;
    }

    public void setUserName(String name){
        this.name.set(name);
    }
    public String getUserName(){
        return name.get();
    }
    public StringProperty nameProperty(){
        return name;
    }

    public void setEmail(String email){
        this.email.set(email);
    }
    public String getEmail(){
        return email.get();
    }
    public StringProperty emailProperty(){
        return email;
    }

    public BooleanProperty activeProperty(){
        return active;
    }

//    @Override
//    public String toString() {
//        return "Users{" +
//                "id=" + id +
//                ", name=" + name +
//                ", email=" + email +
//                '}';
//    }
}
