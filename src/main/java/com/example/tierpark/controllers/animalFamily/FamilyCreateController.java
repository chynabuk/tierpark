package com.example.tierpark.controllers.animalFamily;

import com.example.tierpark.entities.AnimalClass;
import com.example.tierpark.entities.AnimalFamily;
import com.example.tierpark.services.impl.AnimalClassService;
import com.example.tierpark.services.impl.AnimalFamilyService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class FamilyCreateController {
    @FXML
    private TextField name_id;

    @FXML
    private ChoiceBox<String> class_id;

    private AnimalFamilyService service;
    private AnimalClassService animalClassService;

    private Map<String, Integer> animalClasses;


    @FXML
    private void initialize() {
        service = new AnimalFamilyService();
        animalClassService = new AnimalClassService();
        animalClasses = new HashMap<>();
        for (AnimalClass i : animalClassService.readAll()) {
            class_id.getItems().add(i.getName());
            animalClasses.put(i.getName(), i.getId());
        }
    }

    @FXML
    private void createClicked() {
        if (isInputValid()) {
            service.insert(AnimalFamily.builder()
                            .name(name_id.getText())
                            .classId(animalClasses.get(class_id.getValue()))
                    .build());
            ((Stage) name_id.getScene().getWindow()).close();
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (name_id.getText() == null || name_id.getText().length() == 0) {
            errorMessage += "Kein gültiger Name!\n";
        }
        if (class_id.getValue() == null || class_id.getValue().length() == 0) {
            errorMessage += "Kein gültige Tierklasse!\n";
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
