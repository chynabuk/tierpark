package com.example.tierpark.controllers;

import com.example.tierpark.util.CurrentUser;
import com.example.tierpark.util.JdbcSQLServerConnection;
import com.example.tierpark.util.WindowUtil;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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

    @FXML
    private Button createBtn;

    @FXML
    private Button updateBtn;


    @FXML
    private Button deleteBtn;


    @FXML
    private Button exportBtn;

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
        JdbcSQLServerConnection.changeConfiguration(1);
        WindowUtil.openWindowSignIn(stageToClose, "sign-in-view.fxml");
    }

    private MenuBar findMenuBar(Node node) {
        if (node == null) {
            return null;
        }
        if (node instanceof MenuBar) {
            return (MenuBar) node;
        }
        if (node.getParent() != null) {
            return findMenuBar(node.getParent());
        }
        return null;
    }

    protected void checkRole() {
        if (CurrentUser.getUser().getRoleId() == 3) {
            nav_users.setVisible(false);
            nav_care.setVisible(false);
            nav_feeds.setVisible(false);
            createBtn.setManaged(false);
            updateBtn.setManaged(false);
            deleteBtn.setManaged(false);
        } else if (CurrentUser.getUser().getRoleId() == 2) {
            nav_users.setVisible(false);
            nav_building.setVisible(false);
            updateBtn.setVisible(false);
            deleteBtn.setManaged(false);
        }
    }
}
