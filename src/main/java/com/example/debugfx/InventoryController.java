package com.example.debugfx;

import javafx.collections.ObservableList;

import java.util.Timer;

public class InventoryController {

    private final Connector connector;
    private GetInventoryTask getCsgoInventoryTask;
    private final ObservableList<Item> itemObservableList;
    private Timer timer;

    public InventoryController(Connector connector, ObservableList<Item> itemObservableList) {
        this.connector = connector;
        this.itemObservableList = itemObservableList;
    }

    public void start() {
        timer = new Timer();
        getCsgoInventoryTask = new GetInventoryTask(connector, itemObservableList);
        timer.schedule(getCsgoInventoryTask, 0, 5 * 1000);
    }

    public void stop() {
        getCsgoInventoryTask.cancel();
        timer.cancel();
        timer.purge();
        timer = null;
        getCsgoInventoryTask = null;
    }
}
