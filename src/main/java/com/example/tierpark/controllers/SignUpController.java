package com.example.tierpark.controllers;

import com.example.tierpark.StartApplication;
import com.example.tierpark.entities.Gender;
import com.example.tierpark.entities.Role;
import com.example.tierpark.entities.User;
import com.example.tierpark.services.impl.UserService;
import com.example.tierpark.util.CurrentUser;
import com.example.tierpark.util.DateUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class SignUpController {
    @FXML
    private AnchorPane container_id;

    @FXML
    private TextField login_id;

    @FXML
    private TextField name_id;

    @FXML
    private TextField last_name_id;

    @FXML
    private TextField birthday_id;

    @FXML
    private TextField password_id;

    @FXML
    private ChoiceBox<String> gender_id;

    private UserService userService;

    @FXML
    private void initialize() {
        userService = new UserService();
        gender_id.getItems().add("Männlich");
        gender_id.getItems().add("Weiblich");
    }

    @FXML
    protected void singInClicked() throws IOException {
        ((Stage)container_id.getScene().getWindow()).close();
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("sign-in-view.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setTitle("Tierpark");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/zoo_logo.png")));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void signUpClicked() {
        if (isInputValid()) {
            Gender gender;
            if (gender_id.getValue().equals("Männlich")) {
                gender = Gender.MAN;
            } else {
                gender = Gender.WOMAN;
            }
            User user = User.builder()
                    .name(name_id.getText())
                    .lastname(last_name_id.getText())
                    .login(login_id.getText())
                    .birthDate(DateUtil.parse(birthday_id.getText()))
                    .password(password_id.getText())
                    .genderId(gender.getId())
                    .roleId(1)
                    .build();
            userService.insert(user);
            CurrentUser.setUser(user);


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

        if (password_id.getText() == null || password_id.getText().length() == 0) {
            errorMessage += "Kein gültiger Passwort!\n";
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
