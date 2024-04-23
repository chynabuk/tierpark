package com.example.tierpark.controllers;

import com.example.tierpark.entities.Animal;
import com.example.tierpark.entities.Feed;
import com.example.tierpark.services.impl.FeedService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class FeedController {

    @FXML
    private Label label_id;

    @FXML
    private Label label_name;

    @FXML
    private Label label_measure;

    @FXML
    private Label label_price;


    @FXML
    private TableColumn<Feed, Integer> col_id;

    @FXML
    private TableColumn<Feed, String> col_name;

    @FXML
    private TableColumn<Feed, String> col_measure;

    @FXML
    private TableColumn<Feed, Integer> col_price;

    @FXML
    private TableView<Feed> feeds_table;


    @FXML
    public void initialize() {
        FeedService feedService = new FeedService();
        col_id.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getId()));
        col_name.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        col_measure.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getMeasure()));
        col_price.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getPricePerUnit()));

        ObservableList<Feed> feeds = FXCollections.observableArrayList(feedService.readAll());
        feeds_table.setItems(feeds);
        feeds_table.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showDetails(newValue));
    }

    private void showDetails(Feed feed) {
        if (feed != null) {
            label_id.setText(feed.getId() + "");
            label_name.setText(feed.getName());
            label_measure.setText(feed.getMeasure());
            label_price.setText(feed.getPricePerUnit() + "");
        } else {
            label_id.setText("");
            label_name.setText("");
            label_measure.setText("");
            label_price.setText("");
        }
    }
}
