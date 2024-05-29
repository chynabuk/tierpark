package com.example.tierpark.controllers.feedAnimal;

import com.example.tierpark.entities.*;
import com.example.tierpark.services.impl.AnimalService;
import com.example.tierpark.services.impl.FeedAnimalService;
import com.example.tierpark.services.impl.FeedService;
import com.example.tierpark.services.impl.UserService;
import com.example.tierpark.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.HashMap;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedAnimalCreateController {
    @FXML
    ChoiceBox<String> feed_id;
    @FXML
    TextField feed_amount;
    @FXML
    ChoiceBox<String> animal_id;
    @FXML
    ChoiceBox<String> keeper_id;
    @FXML
    TextField feed_date_time;

    FeedAnimalService service;
    FeedService feedService;
    AnimalService animalService;
    UserService userService;

    Map<String, Integer> feeds;
    Map<String, Integer> animals;
    Map<String, Integer> keepers;

    @FXML
    private void initialize(){
        service = new FeedAnimalService();
        feedService = new FeedService();
        animalService = new AnimalService();
        userService = new UserService();

        feeds = new HashMap<>();
        for (Feed feed : feedService.readAll()){
            feed_id.getItems().add(feed.getName());
            feeds.put(feed.getName(), feed.getId());
        }

        animals = new HashMap<>();
        for (Animal animal : animalService.readAll()){
            animal_id.getItems().add(animal.getName());
            animals.put(animal.getName(), animal.getId());
        }

        keepers = new HashMap<>();
        for (User keeper : userService.readAll()){
            String fullName = keeper.getName() + " " + keeper.getLastname();
            keeper_id.getItems().add(fullName);
            keepers.put(fullName, keeper.getId());
        }
    }

    @FXML
    private void createClicked() {
        if (isInputValid()) {
            service.insert(
                    FeedAnimal.builder()
                            .feedId(feeds.get(feed_id.getValue()))
                            .feedAmount(Integer.parseInt(feed_amount.getText()))
                            .keeperId(keepers.get(keeper_id.getValue()))
                            .animalId(animals.get(animal_id.getValue()))
                            .feedDateTime(DateUtil.parseDatetime(feed_date_time.getText()))
                            .build()
            );
            ((Stage) feed_id.getScene().getWindow()).close();
        }

    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (feed_id == null || feed_id.getValue().length() == 0){
            errorMessage += "Keine gültige Futter!\n";
        }
        if (feed_amount == null || feed_amount.getText().length() == 0){
            errorMessage += "Keine gültige Futtermenge";
        }
        else {
            try {
                int n = Integer.parseInt(feed_amount.getText());
                if (n < 1){
                    errorMessage += "Keine gültige Futtermenge, Futtermenge muss größer als 0 sein";
                }
            }
            catch (NumberFormatException e){
                errorMessage += "Keine gültige Futtermenge, Futtermenge muss eine Zahl sein!";
            }
        }

        if (animal_id.getValue() == null || animal_id.getValue().length() == 0) {
            errorMessage += "Kein gültiges Tier!\n";
        }
        if (keeper_id.getValue() == null || keeper_id.getValue().length() == 0){
            errorMessage += "Kein gültiger Tierpfleger!\n";
        }
        if (feed_date_time.getText() == null || feed_date_time.getText().length() == 0) {
            errorMessage += "Keine gültige Fütterungsdatum und -uhrzeit!\n";
        } else {
            if (!DateUtil.validDatetime(feed_date_time.getText())) {
                errorMessage += "Keine gültige Fütterungsdatum und -uhrzeit. Verwenden Sie das Format dd.mm.yyyy HH:mm:ss!\n";
            }
        }


        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ungültige Felder");
            alert.setHeaderText("Bitte korrigieren Sie ungültige Felder");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
