package com.example.tierpark.controllers;

import com.example.tierpark.StartApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class SignUpController {
    @FXML
    private AnchorPane container_id;

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
}
