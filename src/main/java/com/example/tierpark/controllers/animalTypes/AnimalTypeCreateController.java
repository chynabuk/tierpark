package com.example.tierpark.controllers.animalTypes;

import com.example.tierpark.entities.AnimalClass;
import com.example.tierpark.entities.AnimalFamily;
import com.example.tierpark.entities.AnimalType;
import com.example.tierpark.services.impl.AnimalClassService;
import com.example.tierpark.services.impl.AnimalFamilyService;
import com.example.tierpark.services.impl.AnimalTypeService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class AnimalTypeCreateController {
    @FXML
    private TextField name_id;

    @FXML
    private ChoiceBox<String> family_id;

    private AnimalTypeService service;
    private AnimalFamilyService animalFamilyService;

    private Map<String, Integer> animalFamilies;


    @FXML
    private void initialize() {
        service = new AnimalTypeService();
        animalFamilyService = new AnimalFamilyService();
        animalFamilies = new HashMap<>();
        for (AnimalFamily i : animalFamilyService.readAll()) {
            family_id.getItems().add(i.getName());
            animalFamilies.put(i.getName(), i.getId());
        }
    }

    @FXML
    private void createClicked() {
        if (isInputValid()) {
            service.insert(AnimalType.builder()
                            .name(name_id.getText())
                            .familyId(animalFamilies.get(family_id.getValue()))
                    .build());
            ((Stage) name_id.getScene().getWindow()).close();
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (name_id.getText() == null || name_id.getText().length() == 0) {
            errorMessage += "Kein gültiger Name!\n";
        }
        if (family_id.getValue() == null || family_id.getValue().length() == 0) {
            errorMessage += "Kein gültige Tierfamilie!\n";
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
