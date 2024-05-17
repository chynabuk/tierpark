package com.example.tierpark.controllers;

import com.example.tierpark.StartApplication;
import com.example.tierpark.entities.User;
import com.example.tierpark.services.impl.UserService;
import com.example.tierpark.util.CurrentUser;
import com.example.tierpark.util.WindowUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class SignInController {
    @FXML
    private AnchorPane container_id;

    @FXML
    private TextField login_id;

    @FXML
    private TextField password_id;

    private UserService userService;


    @FXML
    private void initialize() {
        userService = new UserService();

    }

    @FXML
    protected void signUpClicked() throws IOException {
        ((Stage)container_id.getScene().getWindow()).close();
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("sign-up-view.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setTitle("Tierpark");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/zoo_logo.png")));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void clickedSignIn() throws IOException {
        User user = isInputValid();
        if (user != null) {
            WindowUtil.openWindow((Stage) container_id.getScene().getWindow(), "animal-view.fxml");
            CurrentUser.setUser(user);
        }
    }


    private User isInputValid() {
        String errorMessage = "";
        User user;

        if (login_id.getText() == null || login_id.getText().length() == 0) {
            errorMessage += "Kein gültiger Benutzername!\n";
        }
        if (password_id.getText() == null || password_id.getText().length() == 0) {
            errorMessage += "Kein gültiger Passwort!\n";
        }

        if (errorMessage.length() == 0) {
            user = userService.login(login_id.getText(), password_id.getText());
            if (user == null) {
                errorMessage += "Der Benutzername oder das Passwort ist falsch eingegeben!\n";
            } else {
                return user;
            }
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ungültige Felder");
        alert.setHeaderText("Bitte korrigieren Sie ungültige Felder");
        alert.setContentText(errorMessage);
        alert.showAndWait();
        return null;
    }

}