package com.example.tierpark.util;

import com.example.tierpark.StartApplication;
import com.example.tierpark.controllers.NavbarController;
import com.example.tierpark.controllers.animal.AnimalUpdateController;
import com.example.tierpark.controllers.animalClass.ClassUpdateController;
import com.example.tierpark.controllers.animalFamily.FamilyUpdateController;
import com.example.tierpark.controllers.animalTypes.AnimalTypeUpdateController;
import com.example.tierpark.controllers.building.BuildingUpdateController;
import com.example.tierpark.controllers.care.CareUpdateController;
import com.example.tierpark.controllers.careType.CareTypeUpdateController;
import com.example.tierpark.controllers.feed.FeedUpdateController;
import com.example.tierpark.controllers.feedAnimal.FeedAnimalUpdateController;
import com.example.tierpark.entities.*;
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
        NavbarController controller = fxmlLoader.getController();
        controller.setStageToClose(stage);
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

    public static boolean openWindowWithoutClosing(String pathToFxml, Animal animal) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource(pathToFxml));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            stage.setTitle("Tierpark");
            stage.getIcons().add(new Image(WindowUtil.class.getResourceAsStream("/images/zoo_logo.png")));
            stage.initModality(Modality.APPLICATION_MODAL);
            AnimalUpdateController controller = fxmlLoader.getController();
            controller.setFields(animal);
            stage.setScene(scene);
            stage.showAndWait();
            return true;
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            return false;
        }
    }

    public static boolean openWindowWithoutClosing(String pathToFxml, AnimalClass animalClass) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource(pathToFxml));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            stage.setTitle("Tierpark");
            stage.getIcons().add(new Image(WindowUtil.class.getResourceAsStream("/images/zoo_logo.png")));
            stage.initModality(Modality.APPLICATION_MODAL);
            ClassUpdateController controller = fxmlLoader.getController();
            controller.setFields(animalClass);
            stage.setScene(scene);
            stage.showAndWait();
            return true;
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            return false;
        }
    }

    public static boolean openWindowWithoutClosing(String pathToFxml, AnimalFamily animalFamily) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource(pathToFxml));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            stage.setTitle("Tierpark");
            stage.getIcons().add(new Image(WindowUtil.class.getResourceAsStream("/images/zoo_logo.png")));
            stage.initModality(Modality.APPLICATION_MODAL);
            FamilyUpdateController controller = fxmlLoader.getController();
            controller.setFields(animalFamily);
            stage.setScene(scene);
            stage.showAndWait();
            return true;
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            return false;
        }
    }

    public static boolean openWindowWithoutClosing(String pathToFxml, AnimalType animalType) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource(pathToFxml));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            stage.setTitle("Tierpark");
            stage.getIcons().add(new Image(WindowUtil.class.getResourceAsStream("/images/zoo_logo.png")));
            stage.initModality(Modality.APPLICATION_MODAL);
            AnimalTypeUpdateController controller = fxmlLoader.getController();
            controller.setFields(animalType);
            stage.setScene(scene);
            stage.showAndWait();
            return true;
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            return false;
        }
    }

    public static boolean openWindowWithoutClosing(String pathToFxml, FeedAnimal feedAnimal) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource(pathToFxml));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            stage.setTitle("Tierpark");
            stage.getIcons().add(new Image(WindowUtil.class.getResourceAsStream("/images/zoo_logo.png")));
            stage.initModality(Modality.APPLICATION_MODAL);
            FeedAnimalUpdateController controller = fxmlLoader.getController();
            controller.setFields(feedAnimal);
            stage.setScene(scene);
            stage.showAndWait();
            return true;
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            return false;
        }
    }

    public static boolean openWindowWithoutClosing(String pathToFxml, CareType careType) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource(pathToFxml));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            stage.setTitle("Tierpark");
            stage.getIcons().add(new Image(WindowUtil.class.getResourceAsStream("/images/zoo_logo.png")));
            stage.initModality(Modality.APPLICATION_MODAL);
            CareTypeUpdateController controller = fxmlLoader.getController();
            controller.setFields(careType);
            stage.setScene(scene);
            stage.showAndWait();
            return true;
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            return false;
        }
    }

    public static boolean openWindowWithoutClosing(String pathToFxml, Care care) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource(pathToFxml));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            stage.setTitle("Tierpark");
            stage.getIcons().add(new Image(WindowUtil.class.getResourceAsStream("/images/zoo_logo.png")));
            stage.initModality(Modality.APPLICATION_MODAL);
            CareUpdateController controller = fxmlLoader.getController();
            controller.setFields(care);
            stage.setScene(scene);
            stage.showAndWait();
            return true;
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            return false;
        }
    }

    public static boolean openWindowWithoutClosing(String pathToFxml, Building building) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource(pathToFxml));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            stage.setTitle("Tierpark");
            stage.getIcons().add(new Image(WindowUtil.class.getResourceAsStream("/images/zoo_logo.png")));
            stage.initModality(Modality.APPLICATION_MODAL);
            BuildingUpdateController controller = fxmlLoader.getController();
            controller.setFields(building);
            stage.setScene(scene);
            stage.showAndWait();
            return true;
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            return false;
        }
    }
}
