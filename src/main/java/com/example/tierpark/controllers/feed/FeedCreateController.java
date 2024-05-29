package com.example.tierpark.controllers.feed;

import com.example.tierpark.entities.Feed;
import com.example.tierpark.services.impl.FeedService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FeedCreateController {
    @FXML
    private TextField name_id;

    @FXML
    private TextField measure_id;

    @FXML
    private TextField price_id;

    private FeedService feedService;

    @FXML
    private void initialize() {
        feedService = new FeedService();
    }

    @FXML
    private void createClicked() {
        if (isInputValid()) {
            feedService.insert(Feed.builder()
                            .name(name_id.getText())
                            .measure(measure_id.getText())
                            .pricePerUnit(Integer.parseInt(price_id.getText()))
                    .build());
            ((Stage) name_id.getScene().getWindow()).close();
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (name_id.getText() == null || name_id.getText().length() == 0) {
            errorMessage += "Kein gültiger Name!\n";
        }
        if (measure_id.getText() == null || measure_id.getText().length() == 0) {
            errorMessage += "Kein gültige Messung!\n";
        }
        if (price_id.getText() == null || price_id.getText().length() == 0) {
            errorMessage += "Kein gültiger Preis pro Einheit!\n";
        } else {
            try {
                Integer.parseInt(price_id.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Kein gültiger Preis pro Einheit (muss eine ganze Zahl sein)!\n";
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
