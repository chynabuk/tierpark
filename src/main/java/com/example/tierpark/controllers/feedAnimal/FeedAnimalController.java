package com.example.tierpark.controllers.feedAnimal;

import com.example.tierpark.controllers.NavbarController;
import com.example.tierpark.entities.Care;
import com.example.tierpark.entities.Feed;
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

    private FeedAnimalService service;
    private FeedService feedService;
    private UserService userService;
    private AnimalService animalService;

    @FXML
    private void initialize(){
        service = new FeedAnimalService();
        feedService = new FeedService();
        userService = new UserService();
        animalService = new AnimalService();

        col_id.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        col_feed.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(feedService.readById(cellData.getValue().getFeedId()).getName()));
        col_feed_amount.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getFeedAmount()));
        col_animal.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(animalService.readById(cellData.getValue().getAnimalId()).getName()));
        col_keeper.setCellValueFactory(cellData -> {
            User keeper = userService.readById(cellData.getValue().getKeeperId());
            return new ReadOnlyStringWrapper(keeper.getName() + " " + keeper.getLastname());
        });
        col_feed_date_time.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(DateUtil.format(cellData.getValue().getFeedDateTime())));
        updateTable();
    }

    private void updateTable() {
        ObservableList<FeedAnimal> entities = FXCollections.observableArrayList(service.readAll());
        table_id.setItems(entities);
        table_id.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showDetails(newValue));
    }

    private void showDetails(FeedAnimal feedAnimal) {
        if (feedAnimal != null) {
            label_id.setText(feedAnimal.getId() + "");
            label_feed.setText(feedService.readById(feedAnimal.getFeedId()).getName());
            label_feed_amount.setText(feedAnimal.getFeedAmount() + "");
            label_animal.setText(animalService.readById(feedAnimal.getAnimalId()).getName());
            User keeper = userService.readById(feedAnimal.getKeeperId());
            label_keeper.setText(keeper.getName() + " " + keeper.getLastname());
            label_feed_date_time.setText(DateUtil.format(feedAnimal.getFeedDateTime()));
        } else {
            label_id.setText("");
            label_feed.setText("");
            label_feed_amount.setText("");
            label_animal.setText("");
            label_keeper.setText("");
            label_feed_date_time.setText("");
        }
    }

    private boolean noSelectedHandle() {
        int selectedIndex = table_id.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            return true;
        }
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Keine Auswahl");
        alert.setHeaderText("Kein Tierfutter ausgewählt");
        alert.setContentText("Bitte wählen Sie ein Tierfutter in der Tabelle aus.");

        alert.showAndWait();
        return false;
    }

    @FXML
    private void createClicked() {
        if (WindowUtil.openWindowWithoutClosing("feed-animal-create.fxml")) {
            updateTable();
        }
    }

    @FXML
    private void editClicked() {
    }

    @FXML
    private void deleteClicked() {
        if (noSelectedHandle()) {
            service.delete(table_id.getSelectionModel().getSelectedItem().getId());
            updateTable();
        }
    }

}
