package com.example.tierpark.controllers.care;

import com.example.tierpark.entities.*;
import com.example.tierpark.services.impl.*;
import com.example.tierpark.util.CurrentUser;
import com.example.tierpark.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class CareCreateController {
    @FXML
    private ChoiceBox<String> care_type_id;

    @FXML
    private ChoiceBox<String> animal_id;

    @FXML
    private ChoiceBox<String> keeper_id;

    @FXML
    private TextField done;

    @FXML
    private Label keeper;

    private CareService service;
    private AnimalService animalService;
    private CareTypeService careTypeService;
    private UserService userService;

    private Map<String, Integer> careTypes;
    private Map<String, Integer> animals;
    private Map<String, Integer> keepers;
    @FXML
    private void initialize(){
        service = new CareService();
        careTypeService = new CareTypeService();
        userService = new UserService();
        animalService = new AnimalService();

        careTypes = new HashMap<>();
        for (CareType careType : careTypeService.readAll()){
            care_type_id.getItems().add(careType.getName());
            careTypes.put(careType.getName(), careType.getId());
        }
        keepers = new HashMap<>();
        for (User keeper : userService.readAll()){
            String fullName = keeper.getName() + " " + keeper.getLastname();
            keeper_id.getItems().add(fullName);
            keepers.put(fullName, keeper.getId());
        }

        animals = new HashMap<>();
        for (Animal animal : animalService.readAll()){
            animal_id.getItems().add(animal.getName());
            animals.put(animal.getName(), animal.getId());
        }

        if (CurrentUser.getUser().getRoleId() == 2) {
            keeper_id.setVisible(false);
            keeper.setVisible(false);
        }
    }
    @FXML
    private void createClicked() {
        if (isInputValid()) {
            if (CurrentUser.getUser().getRoleId() == 2) {
                service.insert(
                        Care.builder()
                                .done(DateUtil.parse(done.getText()))
                                .careTypeId(careTypes.get(care_type_id.getValue()))
                                .keeperId(CurrentUser.getUser().getId())
                                .animalId(animals.get(animal_id.getValue()))
                                .build()
                );
            } else {
                service.insert(
                        Care.builder()
                                .done(DateUtil.parse(done.getText()))
                                .careTypeId(careTypes.get(care_type_id.getValue()))
                                .keeperId(keepers.get(keeper_id.getValue()))
                                .animalId(animals.get(animal_id.getValue()))
                                .build()
                );
            }
            ((Stage) done.getScene().getWindow()).close();
        }

    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (animal_id.getValue() == null || animal_id.getValue().length() == 0) {
            errorMessage += "Kein gültiges Tier!\n";
        }
        if (done.getText() == null || done.getText().length() == 0) {
            errorMessage += "Kein gültiges Pflegedatum!\n";
        } else {
            if (!DateUtil.validDate(done.getText())) {
                errorMessage += "Kein gültiges Pflegedatum. Verwenden Sie das Format tt.mm.JJJJ!\n";
            }
        }
        if (care_type_id == null || care_type_id.getValue().length() == 0){
            errorMessage += "Kein gültige Pflegetype!\n";
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
