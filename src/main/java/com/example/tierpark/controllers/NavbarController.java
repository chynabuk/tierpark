package com.example.tierpark.controllers;

import com.example.tierpark.util.CurrentUser;
import com.example.tierpark.util.WindowUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public abstract class NavbarController {
    protected Stage stageToClose;
    protected String fileName;
    protected Document document;
    protected Element rootElement;

    @FXML
    private Menu nav_animals;

    @FXML
    private Menu nav_feeds;

    @FXML
    private Menu nav_care;

    @FXML
    private Menu nav_building;

    @FXML
    private Menu nav_users;

    public void setStageToClose(Stage stage) {
        stageToClose = stage;
        checkRole();
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
    protected void exportToXML() {
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            document = documentBuilder.newDocument();
            rootElement = document.createElement(fileName);
            document.appendChild(rootElement);

            setupXML();

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(fileName + ".xml"));

            transformer.transform(domSource, streamResult);

            System.out.println("File saved!");
        } catch (ParserConfigurationException | javax.xml.transform.TransformerException e) {
            e.printStackTrace();
        }
    }

    protected abstract void setupXML();
    @FXML
    protected void showUsers() throws IOException {
        WindowUtil.openWindow(stageToClose, "user-view.fxml");
    }


    @FXML
    protected void exitClicked() throws IOException {
        WindowUtil.openWindowSignIn(stageToClose, "sign-in-view.fxml");
    }

    protected void checkRole() {
        if (CurrentUser.getUser().getRoleId() == 3) {
            
        }
    }
}
