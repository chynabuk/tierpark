package com.example.tierpark.controllers.feedAnimal;

import com.example.tierpark.controllers.NavbarController;
import com.example.tierpark.entities.FeedAnimal;
import com.example.tierpark.entities.User;
import com.example.tierpark.services.impl.AnimalService;
import com.example.tierpark.services.impl.FeedAnimalService;
import com.example.tierpark.services.impl.FeedService;
import com.example.tierpark.services.impl.UserService;
import com.example.tierpark.util.DateUtil;
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
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedAnimalController extends NavbarController {
    @FXML
    Label label_id;
    @FXML
    Label label_feed;
    @FXML
    Label label_feed_amount;
    @FXML
    Label label_animal;
    @FXML
    Label label_keeper;
    @FXML
    Label label_feed_date_time;

    @FXML
    TableColumn<FeedAnimal, Integer> col_id;
    @FXML
    TableColumn<FeedAnimal, String> col_feed;
    @FXML
    TableColumn<FeedAnimal, Integer> col_feed_amount;
    @FXML
    TableColumn<FeedAnimal, String> col_animal;
    @FXML
    TableColumn<FeedAnimal, String> col_keeper;
    @FXML
    TableColumn<FeedAnimal, String> col_feed_date_time;

    @FXML
    TableView<FeedAnimal> table_id;

    FeedAnimalService service;
    FeedService feedService;
    UserService userService;
    AnimalService animalService;
    ObservableList<FeedAnimal> feedAnimalList;

    Map<Integer, String> feedCache = new HashMap<>();
    Map<Integer, String> animalCache = new HashMap<>();
    Map<Integer, User> userCache = new HashMap<>();

    @FXML
    private void initialize(){
        service = new FeedAnimalService();
        feedService = new FeedService();
        userService = new UserService();
        animalService = new AnimalService();

        // Load all data once
        feedAnimalList = FXCollections.observableArrayList(service.readAll());
        loadFeeds();
        loadAnimals();
        loadUsers();

        col_id.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        col_feed.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(getFeedName(cellData.getValue().getFeedId())));
        col_feed_amount.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getFeedAmount()));
        col_animal.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(getAnimalName(cellData.getValue().getAnimalId())));
        col_keeper.setCellValueFactory(cellData -> {
            User keeper = getUser(cellData.getValue().getKeeperId());
            return new ReadOnlyStringWrapper(keeper.getName() + " " + keeper.getLastname());
        });
        col_feed_date_time.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(DateUtil.format(cellData.getValue().getFeedDateTime())));

        updateTable();
    }

    private void loadFeeds() {
        feedService.readAll().forEach(feed -> feedCache.put(feed.getId(), feed.getName()));
    }

    private void loadAnimals() {
        animalService.readAll().forEach(animal -> animalCache.put(animal.getId(), animal.getName()));
    }

    private void loadUsers() {
        userService.readAll().forEach(user -> userCache.put(user.getId(), user));
    }

    private String getFeedName(int feedId) {
        return feedCache.getOrDefault(feedId, "Unknown");
    }

    private String getAnimalName(int animalId) {
        return animalCache.getOrDefault(animalId, "Unknown");
    }

    private User getUser(int userId) {
        return userCache.getOrDefault(userId, new User());
    }

    private void updateTable() {
        table_id.setItems(feedAnimalList);
        table_id.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showDetails(newValue));
    }

    private void showDetails(FeedAnimal feedAnimal) {
        if (feedAnimal != null) {
            label_id.setText(String.valueOf(feedAnimal.getId()));
            label_feed.setText(getFeedName(feedAnimal.getFeedId()));
            label_feed_amount.setText(String.valueOf(feedAnimal.getFeedAmount()));
            label_animal.setText(getAnimalName(feedAnimal.getAnimalId()));
            User keeper = getUser(feedAnimal.getKeeperId());
            label_keeper.setText(keeper.getName() + " " + keeper.getLastname());
            label_feed_date_time.setText(DateUtil.format(feedAnimal.getFeedDateTime()));
        } else {
            clearLabels();
        }
    }

    private void clearLabels() {
        label_id.setText("");
        label_feed.setText("");
        label_feed_amount.setText("");
        label_animal.setText("");
        label_keeper.setText("");
        label_feed_date_time.setText("");
    }

    private boolean noSelectedHandle() {
        int selectedIndex = table_id.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            return true;
        }
        showAlert("Keine Auswahl", "Kein Tierfutter ausgewählt", "Bitte wählen Sie ein Tierfutter in der Tabelle aus.");
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
        if (WindowUtil.openWindowWithoutClosing("feed-animal-create.fxml")) {
            refreshFeedAnimalList();
        }
    }

    @FXML
    private void editClicked() {
        if (noSelectedHandle() && WindowUtil.openWindowWithoutClosing("feed-animal-edit.fxml", table_id.getSelectionModel().getSelectedItem())) {
            refreshFeedAnimalList();
        }
    }

    @FXML
    private void deleteClicked() {
        if (noSelectedHandle()) {
            service.delete(table_id.getSelectionModel().getSelectedItem().getId());
            refreshFeedAnimalList();
        }
    }

    private void refreshFeedAnimalList() {
        feedAnimalList.setAll(service.readAll());
        table_id.refresh();
    }

    @Override
    protected void setupXML() {
        for (FeedAnimal feedAnimal : feedAnimalList) {
            Element feedAnimalElement = document.createElement("FeedAnimal");
            rootElement.appendChild(feedAnimalElement);

            Element id = document.createElement("ID");
            id.appendChild(document.createTextNode(String.valueOf(feedAnimal.getId())));
            feedAnimalElement.appendChild(id);

            Element feed = document.createElement("Feed");
            feed.appendChild(document.createTextNode(getFeedName(feedAnimal.getFeedId())));
            feedAnimalElement.appendChild(feed);

            Element feedAmount = document.createElement("FeedAmount");
            feedAmount.appendChild(document.createTextNode(String.valueOf(feedAnimal.getFeedAmount())));
            feedAnimalElement.appendChild(feedAmount);

            Element animal = document.createElement("Animal");
            animal.appendChild(document.createTextNode(getAnimalName(feedAnimal.getAnimalId())));
            feedAnimalElement.appendChild(animal);

            Element keeper = document.createElement("Keeper");
            User keeperUser = getUser(feedAnimal.getKeeperId());
            keeper.appendChild(document.createTextNode(keeperUser.getName() + " " + keeperUser.getLastname()));
            feedAnimalElement.appendChild(keeper);

            Element feedDateTime = document.createElement("FeedDateTime");
            feedDateTime.appendChild(document.createTextNode(DateUtil.format(feedAnimal.getFeedDateTime())));
            feedAnimalElement.appendChild(feedDateTime);
        }
    }
}
