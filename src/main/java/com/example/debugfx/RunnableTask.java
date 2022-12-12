package com.example.debugfx;

import javafx.collections.ObservableList;

public class RunnableTask implements Runnable {

    private final Connector connector;
    private final ObListAdaptor obListAdaptor;
    private boolean inventoryUpdate = false;

    public RunnableTask(Connector connector, ObListAdaptor obListAdaptor) {
        this.connector = connector;
        this.obListAdaptor = obListAdaptor;
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

        getInventoryResponse = connector.getInventory(obListAdaptor);
        if (getInventoryResponse.isSuccessful()) {
            System.out.println("Инвентарь загружен " + getInventoryResponse.isSuccessful() + " " + connector.getGame());
        } else System.out.println("Инвентарь загружен " + getInventoryResponse.isSuccessful() + " " + connector.getGame());

    }
}
