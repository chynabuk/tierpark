package com.example.tierpark.controllers.animalClass;

import com.example.tierpark.controllers.NavbarController;
import com.example.tierpark.entities.AnimalClass;
import com.example.tierpark.services.impl.AnimalClassService;
import com.example.tierpark.util.WindowUtil;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.w3c.dom.Element;

public class ClassController extends NavbarController {

    @FXML
    private Label label_id;

    @FXML
    private Label label_name;

    @FXML
    private TableColumn<AnimalClass, Integer> col_id;

    @FXML
    private TableColumn<AnimalClass, String> col_name;

    @FXML
    private TableView<AnimalClass> table_id;

    private AnimalClassService service;
    private ObservableList<AnimalClass> classList;

    @FXML
    private void initialize() {
        service = new AnimalClassService();
        fileName = service.getTableName();
        // Load all data once
        classList = FXCollections.observableArrayList(service.readAll());

        col_id.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        col_name.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));

        updateTable();
    }

    private void updateTable() {
        table_id.setItems(classList);
        table_id.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showDetails(newValue));
    }

    private void showDetails(AnimalClass animalClass) {
        if (animalClass != null) {
            label_id.setText(String.valueOf(animalClass.getId()));
            label_name.setText(animalClass.getName());
        } else {
            clearLabels();
        }
    }

    private void clearLabels() {
        label_id.setText("");
        label_name.setText("");
    }

    private boolean noSelectedHandle() {
        int selectedIndex = table_id.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            return true;
        }
        showAlert("Keine Auswahl", "Keine Tierklasse ausgewählt", "Bitte wählen Sie eine Tierklasse in der Tabelle aus.");
        return false;
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void createClicked() {
        if (WindowUtil.openWindowWithoutClosing("class-create.fxml")) {
            refreshClassList();
        }
    }

    @FXML
    private void editClicked() {
        if (noSelectedHandle() && WindowUtil.openWindowWithoutClosing("class-edit.fxml", table_id.getSelectionModel().getSelectedItem())) {
            refreshClassList();
        }
    }

    @FXML
    private void deleteClicked() {
        if (noSelectedHandle()) {
            service.delete(table_id.getSelectionModel().getSelectedItem().getId());
            refreshClassList();
        }
    }

    private void refreshClassList() {
        classList.setAll(service.readAll());
        table_id.refresh();
    }

    @Override
    protected void setupXML() {
        for (AnimalClass animalClass : classList) {
            Element classElement = document.createElement("AnimalClass");
            rootElement.appendChild(classElement);

            Element id = document.createElement("ID");
            id.appendChild(document.createTextNode(String.valueOf(animalClass.getId())));
            classElement.appendChild(id);

            Element name = document.createElement("Name");
            name.appendChild(document.createTextNode(animalClass.getName()));
            classElement.appendChild(name);
        }
    }
}
