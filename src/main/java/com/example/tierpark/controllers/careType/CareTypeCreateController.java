package com.example.tierpark.controllers.careType;

import com.example.tierpark.entities.CareType;
import com.example.tierpark.services.impl.CareTypeService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CareTypeCreateController {
    @FXML
    private TextField name_id;

    @FXML
    private TextField description_id;

    private CareTypeService careTypeService;

    @FXML
    private void initialize() {
        careTypeService = new CareTypeService();
    }

    @FXML
    private void createClicked() {
        if (isInputValid()) {
            careTypeService.insert(CareType.builder()
                    .name(name_id.getText())
                    .description(description_id.getText())
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
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ungültige Felder");
            alert.setHeaderText("Bitte korrigieren Sie ungültige Felder");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
