package com.example.debugfx;

import javafx.collections.ObservableList;

public class RunnableTask implements Runnable {

    private final Connector connector;
    private final ObservableList<Item> itemObservableList;
    private boolean inventoryUpdate = false;

    public RunnableTask(Connector connector, ObservableList<Item> itemObservableList) {
        this.connector = connector;
        this.itemObservableList = itemObservableList;
    }

    @Override
    public void run() {

        MarketResponse inventoryUpdateResponse;
        MarketResponse getInventoryResponse;

        if (!inventoryUpdate) {
            inventoryUpdateResponse = connector.updateInventory();
            inventoryUpdate = inventoryUpdateResponse.isSuccessful();
            System.out.println("Инвентарь обновлён " + inventoryUpdateResponse.isSuccessful() + " " + connector.getGame());
        }

        getInventoryResponse = connector.getInventory(itemObservableList);
        if (getInventoryResponse.isSuccessful()) {
            System.out.println("Инвентарь загружен " + getInventoryResponse.isSuccessful() + " " + connector.getGame());
        } else System.out.println("Инвентарь загружен " + getInventoryResponse.isSuccessful() + " " + connector.getGame());

    }
}
