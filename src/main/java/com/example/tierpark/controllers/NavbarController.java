package com.example.tierpark.controllers;

import com.example.tierpark.util.WindowUtil;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class NavbarController {
    protected Stage stageToClose;

    public void setStageToClose(Stage stage) {
        stageToClose = stage;
    }

    @FXML
    protected void showAnimals() throws IOException {
        WindowUtil.openWindow(stageToClose, "animal-view.fxml");
    }

    @FXML
    protected void showAnimalTypes() throws IOException {
        WindowUtil.openWindow(stageToClose, "animal-type-view.fxml");
    }

    @FXML
    protected void showAnimalFamilies() throws IOException {
        WindowUtil.openWindow(stageToClose, "family-view.fxml");
    }

    @FXML
    protected void showAnimalClasses() throws IOException {
        WindowUtil.openWindow(stageToClose, "class-view.fxml");
    }

    @FXML
    protected void showFeeds() throws IOException {
        WindowUtil.openWindow(stageToClose, "feed-view.fxml");
    }

    @FXML
    protected void showBuildings() throws IOException {
        WindowUtil.openWindow(stageToClose, "building-view.fxml");
    }

    @FXML
    protected void showCareTypes() throws IOException{
        WindowUtil.openWindow(stageToClose, "care-type-view.fxml");
    }

    @FXML
    protected void showCare() throws IOException{
        WindowUtil.openWindow(stageToClose, "care-view.fxml");
    }

    @FXML
    protected void showFeedAnimals() throws IOException{
        WindowUtil.openWindow(stageToClose, "feed-animal-view.fxml");
    }

    @FXML
    protected void showUsers() throws IOException {
        WindowUtil.openWindow(stageToClose, "user-view.fxml");
    }


    @FXML
    protected void exitClicked() {

    }
}
