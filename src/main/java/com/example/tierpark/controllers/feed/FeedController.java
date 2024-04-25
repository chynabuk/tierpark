package com.example.tierpark.controllers.feed;

import com.example.tierpark.controllers.NavbarController;
import com.example.tierpark.entities.Feed;
import com.example.tierpark.services.impl.FeedService;
import com.example.tierpark.util.WindowUtil;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class FeedController extends NavbarController {

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

    private FeedService feedService;


    @FXML
    private void initialize() {
        feedService = new FeedService();
        col_id.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getId()));
        col_name.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        col_measure.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getMeasure()));
        col_price.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getPricePerUnit() + " €"));
        updateTable();
    }

    private void updateTable() {
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
            label_price.setText(feed.getPricePerUnit() + " €");
        } else {
            label_id.setText("");
            label_name.setText("");
            label_measure.setText("");
            label_price.setText("");
        }
    }

    private boolean noSelectedHandle() {
        int selectedIndex = feeds_table.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            return true;
        }
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Keine Auswahl");
        alert.setHeaderText("Kein Feed ausgewählt");
        alert.setContentText("Bitte wählen Sie einen Feed in der Tabelle aus.");

        alert.showAndWait();
        return false;
    }

    @FXML
    private void createClicked() {
        if (WindowUtil.openWindowWithoutClosing("feed-create.fxml")) {
            updateTable();
        }
    }

    @FXML
    private void editClicked() {
        if (noSelectedHandle() && WindowUtil.openWindowWithoutClosing("feed-edit.fxml", feeds_table.getSelectionModel().getSelectedItem())) {
            updateTable();
        }
    }

    @FXML
    private void deleteClicked() {
        if (noSelectedHandle()) {
            feedService.delete(feeds_table.getSelectionModel().getSelectedItem().getId());
            updateTable();
        }
    }
}
