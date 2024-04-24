package com.example.tierpark.controllers.animalClass;

import com.example.tierpark.entities.AnimalClass;
import com.example.tierpark.services.impl.AnimalClassService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ClassCreateController {
    @FXML
    private TextField name_id;

    private AnimalClassService service;

    @FXML
    private void initialize() {
        service = new AnimalClassService();
    }

    @FXML
    private void createClicked() {
        if (isInputValid()) {
            service.insert(AnimalClass.builder()
                            .name(name_id.getText())
                    .build());
            ((Stage) name_id.getScene().getWindow()).close();
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (name_id.getText() == null || name_id.getText().length() == 0) {
            errorMessage += "Kein gültiger Name!\n";
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