package com.example.tierpark;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class StartApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("sign-in-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setTitle("Tierpark");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/zoo_logo.png")));
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
//        RoleService roleService = new RoleService();
//        roleService.insert(Role.builder().name("Tierpfleger").build());
        launch();
    }
}