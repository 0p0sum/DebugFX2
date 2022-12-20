package com.example.debugfx;



import java.util.Timer;

public class InventoryController {

    private final Connector connector;
    private GetInventoryTask getCsgoInventoryTask;
    private final ObListAdaptor obListAdaptor;
    private Timer timer;

    public InventoryController(Connector connector, ObListAdaptor obListAdaptor) {
        this.connector = connector;
        this.obListAdaptor = obListAdaptor;
    }

    public void start() {
        timer = new Timer();
        getCsgoInventoryTask = new GetInventoryTask(connector, obListAdaptor);
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
