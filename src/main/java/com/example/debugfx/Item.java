package com.example.debugfx;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Item {
    StringProperty marketHashName;

    ItemStatus itemStatus;

    String itemId;

    public String getItemId() {
        return itemId;
    }

    Game game;

    public Item(String itemId, String marketHashName) {
        this.itemId = itemId;
        this.marketHashName = new SimpleStringProperty(marketHashName);
        this.itemStatus = ItemStatus.IN_INVENTORY;

    }

    public String getMarketHashName(){
        return marketHashName.get();
    }
    public StringProperty marketHashNameProperty(){
        return marketHashName;
    }
}

enum ItemStatus{
    IN_INVENTORY
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