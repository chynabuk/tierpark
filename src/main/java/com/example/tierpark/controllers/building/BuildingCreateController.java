package com.example.tierpark.controllers.building;

import com.example.tierpark.entities.Building;
import com.example.tierpark.services.impl.BuildingService;
import com.example.tierpark.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BuildingCreateController {
    @FXML
    private TextField name_id;

    @FXML
    private TextField built_date_id;

    private BuildingService buildingService;

    @FXML
    private void initialize() {
        buildingService = new BuildingService();
    }

    @FXML
    private void createClicked() {
        if (isInputValid()) {
            buildingService.insert(Building.builder()
                    .name(name_id.getText())
                    .builtDate(DateUtil.parse(built_date_id.getText()))
                    .build());
            ((Stage) name_id.getScene().getWindow()).close();
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (name_id.getText() == null || name_id.getText().length() == 0) {
            errorMessage += "Kein gültiger Name!\n";
        }
        if (built_date_id.getText() == null || built_date_id.getText().length() == 0) {
            errorMessage += "Kein gültiges Baudatum!\n";
        } else {
            if (!DateUtil.validDate(built_date_id.getText())) {
                errorMessage += "Kein gültiges Baudatum. Verwenden Sie das Format tt.mm.JJJJ!\n";
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
