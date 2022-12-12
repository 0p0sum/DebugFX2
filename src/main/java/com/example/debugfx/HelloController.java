package com.example.debugfx;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class HelloController {
    @FXML
    TableView testTable;

    @FXML
    TableColumn column1;
    @FXML
    TableColumn<Users, String> column2;
    @FXML
    TableColumn<Users, Number> column3;
    @FXML
    TableColumn<Users, Boolean> column4;

    @FXML
    ChoiceBox<Users> chBox;
    @FXML
    ComboBox<Users> comboBox;
    @FXML
    Accordion accordionTest;
    @FXML
    Accordion manualAccordion;
    @FXML
    Button accordButton;
    @FXML
    TitledPane titlePaneTest;

    @FXML
    TableView<Item> tableViewTitle;
    @FXML
    TableColumn<Item, String> marketHashNameColumn;
    @FXML
    CheckBox csgoCheckBox, dota2CheckBox, tf2CheckBox;

    ObservableList<Users> list = FXCollections.<Users>observableArrayList();

    ObservableList<TitledPane> titledPanes = FXCollections.<TitledPane>observableArrayList();


    public void initialize() throws InterruptedException {


        for (int i = 1; i < 5; i++) {
            list.add(new Users(i, "Name " + i, "Email " + i));
        }

        testTable.setItems(list);
        testTable.getSelectionModel().selectFirst();
        testTable.setEditable(true);

        column1.setCellValueFactory(new PropertyValueFactory<Users, String>("name"));

        column2.setCellValueFactory(
                cellData -> cellData.getValue().emailProperty()
        );

        column3.setCellValueFactory(
                cellData -> cellData.getValue().idProperty()
        );
        column3.setCellFactory(TextFieldTableCell.<Users, Number>forTableColumn(
                new StringConverter<Number>() {
                    @Override
                    public String toString(Number object) {
                        return object == null ? "" : object.toString();
                    }

                    @Override
                    public Number fromString(String string) {
                        return Integer.valueOf(string);
                    }
                }
        ));

        column4.setEditable(true);
        column4.setCellFactory(CheckBoxTableCell.forTableColumn(column4));
        column4.setCellValueFactory(
                cellData -> cellData.getValue().activeProperty()
        );

        StringConverter<Users> converter = new StringConverter<Users>() {
            @Override
            public String toString(Users object) {
                return object != null ? object.getUserName() + " " + object.getUserId() : null;
            }

            @Override
            public Users fromString(String string) {
                return null;
            }
        };


        comboBox.setConverter(converter);
        comboBox.getItems().addAll(list);
        comboBox.setEditable(false);
///////////////////////////////////////////////////////////////////////

        String apiKey = "slwR411N3klAG74v09VwET05b3A3T8S";

        tableViewTitle.setPlaceholder(new Label("Инвентарь не загружен"));

        ObservableList<Item> itemObservableList = FXCollections.<Item>observableArrayList();


        InventoryController csgoInventoryController = new InventoryController(new Connector(apiKey, Game.CSGO), itemObservableList);
        InventoryController dota2InventoryController = new InventoryController(new Connector(apiKey, Game.DOTA2), itemObservableList);
        InventoryController tf2InventoryController = new InventoryController(new Connector(apiKey, Game.TF2), itemObservableList);

        csgoCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue) {
                csgoInventoryController.start();
            } else {
                csgoInventoryController.stop();
                System.out.println("test");
            }

        });

        dota2CheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue) {
                dota2InventoryController.start();
            } else {
                dota2InventoryController.stop();
            }

        });

        tf2CheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue) {
                tf2InventoryController.start();
            } else {
                tf2InventoryController.stop();
            }

        });


        TitledPane titledPane = new TitledPane();
        titledPane.setText("user1");
        titledPane.setOnMouseClicked(event -> {
            System.out.println("test2");
        });


        TitledPane titledPane2 = new TitledPane();
        titledPane2.setText("user2");

        titledPanes.addAll(titledPane);
        titledPanes.addAll(titledPane2);

        accordionTest.getPanes().addAll(titledPanes);

        tableViewTitle.setItems(itemObservableList);

        marketHashNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().marketHashNameProperty()
        );

    }

    int her = 0;

    public void change() {
        System.out.println("test");
        //list.get(2).setId(list.get(2).getId()+1);
        //list.get(2).setUserName("changed");
        her += 10;
        list.get(2).setId(her);


        //list.add(null);
        //list.remove(list.size()-1);
        her++;
        System.out.println("Херачит " + her);

        Users value = comboBox.getValue();
        comboBox.getItems().removeAll(list);
        comboBox.getItems().addAll(list);
        comboBox.setValue(value);


        //list.add(list.size(), new Users(12, "testik", "emails"));

    }

    public void accordTest() {
        titledPanes.remove(1);
        accordionTest.getPanes().remove(1);
    }
}




// Обработчик открытия titlePane ObjectProperty<TitledPane> testProp = manualAccordion.expandedPaneProperty();

//        testProp.addListener((observable, oldValue, newValue) -> {
//            if (oldValue == null) {
//                if (itemObservableList.isEmpty()) {
//                    MarketResponse inventoryUpdateResponse = connector.updateInventory();
//                    getInventoryTimer.schedule(getInventoryTask, 0, 5 * 1000);
//                    System.out.println("test open");
//                }
//            }
//        });

