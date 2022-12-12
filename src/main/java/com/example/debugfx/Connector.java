package com.example.debugfx;

import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
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

//    public LocalPositionResponse getLocalPosition(Item checkItem) {
//
//        URLRequester setOrderRequester = null;
//        LocalPositionResponse response = new LocalPositionResponse();
//
//        try {
//            setOrderRequester = new URLRequester(domainID + "SellOffers/" + checkItem.getClassId() + "_" + checkItem.getInstanceId() + "/?key=" + mainAPIKey);
//            JSONObject object = new JSONObject(setOrderRequester.getContent());
//            try {
//                boolean result = object.getBoolean("success");
//                int bestOffer = object.getInt("best_offer");
//                response.setCurrentLocalPrice(bestOffer);
//
//                JSONArray offers = object.getJSONArray("offers");
//                JSONObject firstOffer = offers.getJSONObject(0);
//                String myCount = firstOffer.getString("my_count");
//
//                response.setMyFirst(!myCount.equals("0"));
//                response.setSuccessful(true);
//                response.setErrorType(ErrorType.NO_ERR);
//
//            } catch (JSONException e) {
//                response.setSuccessful(false);
//                response.setErrorMsg(object.toString());
//                response.setErrorType(ErrorType.MARKET_ERR);
//            }
//
//        } catch (IOException e) {
//            response.setSuccessful(false);
//            response.setErrorMsg(e.getMessage());
//            response.setErrorType(ErrorType.CONNECTION_ERR);
//
//        }
//        return response;
//    }
//
//    public GlobalPositionResponse getGlobalPosition(ArrayList<Item> checkItems) {
//
//
//        URLRequester globalPriceRequester = null;
//        GlobalPositionResponse response = new GlobalPositionResponse();
//
//        try {
//
//            String hashNameList = "";
//            for (int i = 0; i < checkItems.size(); i++) {
//                hashNameList += ("&list_hash_name[]=" + URLEncoder.encode(checkItems.get(i).getMarketHashName(), "UTF-8").replaceAll("\\+", "%20"));
//            }
//            String marketPriceLink = domainID + "v2/search-list-items-by-hash-name-all?key=" + mainAPIKey + hashNameList;
//
//
//            globalPriceRequester = new URLRequester(marketPriceLink);
//            JSONObject object = new JSONObject(globalPriceRequester.getContent());
//            try {
//
//                boolean result = object.getBoolean("success");
//                JSONObject responseObject = object.getJSONObject("data");
//                Map<String, Object> responseMap = responseObject.toMap();
//
//                //GlobalPosition[] globalPositions = new GlobalPosition[checkItems.size()];
//                HashMap<String, GlobalPosition> globalPositions = new HashMap<>();
//
//
//                for (Item item : checkItems
//                ) {
//                    ArrayList<HashMap<String, Object>> checkPositionArray = (ArrayList<HashMap<String, Object>>) responseMap.get(item.getMarketHashName());
//                    HashMap<String, Object> firstPosition = checkPositionArray.get(0);
//                    HashMap<String, Object> secondPosition = checkPositionArray.get(1);
//
//
//                    String id = firstPosition.get("id").toString();
//
//                    int firstPositionPrice = Integer.parseInt(firstPosition.get("price").toString());
//                    int secondPositionPrice = Integer.parseInt(secondPosition.get("price").toString());
//
//                    boolean isFirst = item.getItemId().equals(id);
//
//                    globalPositions.put(item.getItemId(), new GlobalPosition(isFirst,
//                            item.getMarketHashName(),
//                            item.getAssetId(),
//                            firstPositionPrice,
//                            secondPositionPrice
//                    ));
//
//                }
//                response.setSuccessful(result);
//                response.setGlobalPositions(globalPositions);
//                response.setErrorType(ErrorType.NO_ERR);
//
//
//            } catch (JSONException e) {
//                response.setSuccessful(false);
//                response.setErrorMsg(object.toString());
//                response.setErrorType(ErrorType.MARKET_ERR);
//
//            }
//        } catch (IOException e) {
//
//            response.setSuccessful(false);
//            response.setErrorMsg(e.getMessage());
//            response.setErrorType(ErrorType.CONNECTION_ERR);
//
//        }
//        return response;
//    }


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
                    ArrayList<Item> itemsToAdd = new ArrayList<>();

                    while (iterator.hasNext()){
                        JSONObject itemObject = (JSONObject) iterator.next();

                        itemsToAdd.add(new Item(
                                itemObject.getString("id"),
                                itemObject.getString("market_hash_name")
                        ));
                    }
                    items.addAll(itemsToAdd);

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
