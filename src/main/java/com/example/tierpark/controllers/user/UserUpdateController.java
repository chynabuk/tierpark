package com.example.tierpark.controllers.user;

import com.example.tierpark.entities.User;
import com.example.tierpark.services.impl.UserService;
import com.example.tierpark.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserUpdateController {
    @FXML
    private TextField field_id;

    @FXML
    private TextField name_id;

    @FXML
    private TextField last_name_id;

    @FXML
    private TextField birthday_id;


    private UserService userService;

    @FXML
    private void initialize() {
        userService = new UserService();
    }

    public void setFields(User user) {
        field_id.setText(user.getId()+"");
        name_id.setText(user.getName());
        last_name_id.setText(user.getLastname());
        birthday_id.setText(DateUtil.format(user.getBirthDate()));
    }

    @FXML
    private void updateClicked() {
        if (isInputValid()) {
            User user = User.builder()
                    .id(Integer.parseInt(field_id.getText()))
                    .name(name_id.getText())
                    .lastname(last_name_id.getText())
                    .birthDate(DateUtil.parse(birthday_id.getText()))
                    .build();
            userService.update(user);
            ((Stage) field_id.getScene().getWindow()).close();
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (name_id.getText() == null || name_id.getText().length() == 0) {
            errorMessage += "Kein gültiger Vorname!\n";
        }
        if (last_name_id.getText() == null || last_name_id.getText().length() == 0) {
            errorMessage += "Kein gültiger Nachname!\n";
        }
        if (birthday_id.getText() == null || birthday_id.getText().length() == 0) {
            errorMessage += "Kein gültiger Geburtstag!\n";
        } else {
            if (!DateUtil.validDate(birthday_id.getText())) {
                errorMessage += "Kein gültiger Geburtstag. Verwenden Sie das Format tt.mm.JJJJ!\n";
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
