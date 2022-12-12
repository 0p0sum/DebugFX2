package com.example.debugfx;

import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;


public class Connector {
    private String mainAPIKey;
    private String domainID;
    private Game game;

    Connector(String APIKey, Game game) {
        this.game = game;
        this.mainAPIKey = APIKey;

        switch (game){
            case CSGO ->  domainID = "https://market.csgo.com/api/";
            case DOTA2 -> domainID = "https://market.dota2.net/api/";
            case TF2 -> domainID = "https://tf2.tm/api/";
        }
    }

    public MarketResponse updateInventory(){

        URLRequester setOrderRequester = null;
        MarketResponse response = new MarketResponse();

        try {
            setOrderRequester = new URLRequester(domainID +
                    "v2/update-inventory/?key="+mainAPIKey);
            JSONObject object = new JSONObject(setOrderRequester.getContent());
            try {
                boolean isSuccess = object.getBoolean("success");
                response.setSuccessful(isSuccess);
                response.setErrorType(ErrorType.NO_ERR);
                if (!isSuccess){
                    response.setErrorType(ErrorType.MARKET_ERR);
                    response.setErrorMsg(object.getString("error"));
                }

            } catch (JSONException e) {
                response.setSuccessful(false);
                response.setErrorMsg(object.toString());
                response.setErrorType(ErrorType.MARKET_ERR);
            }

        } catch (IOException e) {
            response.setSuccessful(false);
            response.setErrorMsg(e.getMessage());
            response.setErrorType(ErrorType.CONNECTION_ERR);
        }
        return response;
    }

    public MarketResponse getInventory(ObservableList<Item> items ){

        URLRequester getInventoryRequester = null;
        MarketResponse response = new MarketResponse();

        try {
            getInventoryRequester = new URLRequester(domainID +
                    "v2/my-inventory/?key="+mainAPIKey);
            JSONObject object = new JSONObject(getInventoryRequester.getContent());
            try {

                JSONArray itemJsonArray = object.getJSONArray("items");
                boolean empty = itemJsonArray.isEmpty();

                if (!empty){
                    Iterator<Object> iterator = itemJsonArray.iterator();

                    while (iterator.hasNext()){
                        JSONObject itemObject = (JSONObject) iterator.next();

                        String itemId = itemObject.getString("id");
                        String marketHashName = itemObject.getString("market_hash_name");

                        boolean isBusy = false;

                        for (Item item: items
                             ) {
                            if (item.getItemId().equals(itemId)) isBusy = true;
                        }

                        if (!isBusy){
                            items.add(new Item(itemId, marketHashName));
                        }else System.out.println("Есть в списке");
                    }

                    boolean isSuccess = object.getBoolean("success");
                    response.setSuccessful(isSuccess);
                    response.setErrorType(ErrorType.NO_ERR);
                }else {
                    response.setSuccessful(false);
                    response.setErrorMsg("Итвентарь не загружен");
                    response.setErrorType(ErrorType.MARKET_ERR);
                }

            } catch (JSONException e) {
                response.setSuccessful(false);
                response.setErrorMsg(object.toString());
                response.setErrorType(ErrorType.MARKET_ERR);
            }

        } catch (IOException e) {
            response.setSuccessful(false);
            response.setErrorMsg(e.getMessage());
            response.setErrorType(ErrorType.CONNECTION_ERR);
        }
        return response;
    }

    public String getGame(){
        return game.name();
    }
}
