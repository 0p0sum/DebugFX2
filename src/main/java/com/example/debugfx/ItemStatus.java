package com.example.debugfx;

public enum ItemStatus {

    IN_INVENTORY("В инвентаре");

    ItemStatus(String name){
        this.stringName = name;
    }

    final private String stringName;
    public String getStringName() {return stringName;}
}
