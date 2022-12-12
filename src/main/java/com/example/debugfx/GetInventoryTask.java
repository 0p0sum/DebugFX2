package com.example.debugfx;

import javafx.collections.ObservableList;

import java.util.TimerTask;

public class GetInventoryTask extends TimerTask {

    private final Connector connector;
    private final ObListAdaptor obListAdaptor;
    private boolean inventoryUpdate = false;

    public GetInventoryTask(Connector connector, ObListAdaptor obListAdaptor) {
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
            this.cancel();
        } else System.out.println("Инвентарь загружен " + getInventoryResponse.isSuccessful() + " " + connector.getGame());

    }
}
