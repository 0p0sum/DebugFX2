package com.example.debugfx;

import javafx.collections.ObservableList;

public class ObListAdaptor {
    private ObservableList<Item> observableList;

    public ObListAdaptor(ObservableList<Item> observableList) {
        this.observableList = observableList;
    }

    public Item getItemByItemID(String itemId){

        for (Item itemO :observableList
             ) {
            if (itemO.getItemId().equals(itemId)) return itemO;
        }
        return null;
    }

    public boolean addItem (Item item){

        for (Item itemO :observableList
        ) {
            if (itemO.getItemId().equals(item.getItemId())) {
                return false;
            }
        }

        observableList.add(item);
        return true;
    }

    public boolean removeItem(String itemId){

        for (Item itemO :observableList
        ) {
            if (itemO.getItemId().equals(itemId)) {
                observableList.remove(itemO);
                return true;
            }
        }
        return false;
    }
}
