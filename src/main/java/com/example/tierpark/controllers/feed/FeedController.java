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
import org.w3c.dom.Element;

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
    private TableColumn<Feed, String> col_price;

    @FXML
    private TableView<Feed> feeds_table;

    private FeedService feedService;
    private ObservableList<Feed> feedList;

    @FXML
    private void initialize() {
        feedService = new FeedService();
        fileName = feedService.getTableName();

        // Load all data once
        try {
            feedList = FXCollections.observableArrayList(feedService.readAll());
            col_id.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
            col_name.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
            col_measure.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getMeasure()));
            col_price.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPricePerUnit() + " €"));

            updateTable();
        }
        catch (NullPointerException e){

        }
    }

    private void updateTable() {
        feeds_table.setItems(feedList);
        feeds_table.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showDetails(newValue));
    }

    private void showDetails(Feed feed) {
        if (feed != null) {
            label_id.setText(String.valueOf(feed.getId()));
            label_name.setText(feed.getName());
            label_measure.setText(feed.getMeasure());
            label_price.setText(feed.getPricePerUnit() + " €");
        } else {
            clearLabels();
        }
    }

    private void clearLabels() {
        label_id.setText("");
        label_name.setText("");
        label_measure.setText("");
        label_price.setText("");
    }

    private boolean noSelectedHandle() {
        int selectedIndex = feeds_table.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            return true;
        }
        showAlert("Keine Auswahl", "Kein Feed ausgewählt", "Bitte wählen Sie einen Feed in der Tabelle aus.");
        return false;
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void createClicked() {
        if (WindowUtil.openWindowWithoutClosing("feed-create.fxml")) {
            refreshFeedList();
        }
    }

    @FXML
    private void editClicked() {
        if (noSelectedHandle() && WindowUtil.openWindowWithoutClosing("feed-edit.fxml", feeds_table.getSelectionModel().getSelectedItem())) {
            refreshFeedList();
        }
    }

    @FXML
    private void deleteClicked() {
        if (noSelectedHandle()) {
            feedService.delete(feeds_table.getSelectionModel().getSelectedItem().getId());
            refreshFeedList();
        }
    }

    private void refreshFeedList() {
        feedList.setAll(feedService.readAll());
        feeds_table.refresh();
    }

    @Override
    protected void setupXML() {
        for (Feed feed : feedList) {
            Element feedElement = document.createElement("Feed");
            rootElement.appendChild(feedElement);

            Element id = document.createElement("ID");
            id.appendChild(document.createTextNode(String.valueOf(feed.getId())));
            feedElement.appendChild(id);

            Element name = document.createElement("Name");
            name.appendChild(document.createTextNode(feed.getName()));
            feedElement.appendChild(name);

            Element measure = document.createElement("Measure");
            measure.appendChild(document.createTextNode(feed.getMeasure()));
            feedElement.appendChild(measure);

            Element price = document.createElement("Price");
            price.appendChild(document.createTextNode(feed.getPricePerUnit() + " €"));
            feedElement.appendChild(price);
        }
    }
}
