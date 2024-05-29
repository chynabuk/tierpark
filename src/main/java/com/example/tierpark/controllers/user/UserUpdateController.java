package com.example.tierpark.controllers.user;

import com.example.tierpark.entities.FeedAnimal;
import com.example.tierpark.entities.Gender;
import com.example.tierpark.entities.User;
import com.example.tierpark.services.impl.UserService;
import com.example.tierpark.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class UserUpdateController {
    @FXML
    private TextField field_id;

    @FXML
    private TextField login_id;

    @FXML
    private TextField name_id;

    @FXML
    private TextField last_name_id;

    @FXML
    private TextField birthday_id;

    @FXML
    private ChoiceBox<String> gender_id;

    private UserService userService;

    @FXML
    private void initialize() {
        userService = new UserService();
        gender_id.getItems().add("Männlich");
        gender_id.getItems().add("Weiblich");
    }

    public void setFields(User user) {
        field_id.setText(user.getId()+"");
        login_id.setText(user.getLogin());
        name_id.setText(user.getName());
        last_name_id.setText(user.getLastname());
        birthday_id.setText(DateUtil.format(user.getBirthDate()));
        gender_id.getSelectionModel().select(user.getGenderId() == Gender.MAN.getId() ? "Männlich" : "Weiblich");
    }

    @FXML
    private void updateClicked() {
        if (isInputValid()) {
            Gender gender;
            if (gender_id.getValue().equals("Männlich")) {
                gender = Gender.MAN;
            } else {
                gender = Gender.WOMAN;
            }
            User user = User.builder()
                    .id(Integer.parseInt(field_id.getText()))
                    .name(name_id.getText())
                    .lastname(last_name_id.getText())
                    .login(login_id.getText())
                    .birthDate(DateUtil.parse(birthday_id.getText()))
                    .genderId(gender.getId())
                    .roleId(2)
                    .build();
            userService.insert(user);
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (login_id.getText() == null || login_id.getText().length() == 0) {
            errorMessage += "Kein gültiger Benutzername!\n";
        }
        if (name_id.getText() == null || name_id.getText().length() == 0) {
            errorMessage += "Kein gültiger Vorname!\n";
        }
        if (last_name_id.getText() == null || last_name_id.getText().length() == 0) {
            errorMessage += "Kein gültiger Nachname!\n";
        }

        if (gender_id.getValue() == null || gender_id.getValue().length() == 0) {
            errorMessage += "Kein gültiger Geschlecht!\n";
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
