package com.example.debugfx;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Item {
    private final StringProperty marketHashName;
    private ObjectProperty<ItemStatus> statusObjectProperty;

    String itemId;

    public String getItemId() {
        return itemId;
    }

    Game game;

    public Item(String itemId, String marketHashName) {
        this.itemId = itemId;
        this.marketHashName = new SimpleStringProperty(marketHashName);
        this.statusObjectProperty = new SimpleObjectProperty<>(ItemStatus.IN_INVENTORY);

    }

    public String getMarketHashName(){
        return marketHashName.get();
    }
    public StringProperty marketHashNameProperty(){
        return marketHashName;
    }

    public ObjectProperty<ItemStatus> statusObjectProperty() {
        return statusObjectProperty;
    }
    public void setItemStatus(ItemStatus status){
        this.statusObjectProperty.setValue(status);
    }
}

enum Game{
    DOTA2{
        final String appId = "570";
        public String getAppId() {
            return appId;
        }
    },
    CSGO{
        final String appId = "730";
        public String getAppId() {
            return appId;
        }
    },
    TF2{
        final String appId = "440";
        public String getAppId() {
            return appId;
        }
    }
}