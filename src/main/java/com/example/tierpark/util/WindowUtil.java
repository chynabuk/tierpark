package com.example.tierpark.util;

import com.example.tierpark.StartApplication;
import com.example.tierpark.controllers.feed.FeedUpdateController;
import com.example.tierpark.entities.Feed;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class WindowUtil {

    public static void openWindow(Stage stageToClose, String pathToFxml) throws IOException {
        stageToClose.close();
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource(pathToFxml));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setTitle("Tierpark");
        stage.getIcons().add(new Image(WindowUtil.class.getResourceAsStream("/images/zoo_logo.png")));
        stage.setScene(scene);
        stage.show();
    }

    public static boolean openWindowWithoutClosing(String pathToFxml) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource(pathToFxml));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            stage.setTitle("Tierpark");
            stage.getIcons().add(new Image(WindowUtil.class.getResourceAsStream("/images/zoo_logo.png")));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            return true;
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            return false;
        }
    }

    public static boolean openWindowWithoutClosing(String pathToFxml, Feed feed) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource(pathToFxml));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            stage.setTitle("Tierpark");
            stage.getIcons().add(new Image(WindowUtil.class.getResourceAsStream("/images/zoo_logo.png")));
            stage.initModality(Modality.APPLICATION_MODAL);
            FeedUpdateController controller = fxmlLoader.getController();
            controller.setFields(feed);
            stage.setScene(scene);
            stage.showAndWait();
            return true;
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            return false;
        }
    }
}
