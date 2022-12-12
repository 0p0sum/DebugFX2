package com.example.debugfx;

public class Pinger {

    private String name;

    public Pinger(String name) {
        this.name = name;
    }

    public void ping(){
        System.out.println("Ping !"+name);
    }

}
