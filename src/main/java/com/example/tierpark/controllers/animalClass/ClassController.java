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
import javafx.stage.Stage;

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

    @FXML
    private void initialize() {
        service = new AnimalClassService();
        col_id.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getId()));
        col_name.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        updateTable();
    }

    private void updateTable() {
        ObservableList<AnimalClass> entities = FXCollections.observableArrayList(service.readAll());
        table_id.setItems(entities);
        table_id.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showDetails(newValue));
    }

    private void showDetails(AnimalClass animalClass) {
        if (animalClass != null) {
            label_id.setText(animalClass.getId() + "");
            label_name.setText(animalClass.getName());
        } else {
            label_id.setText("");
            label_name.setText("");
        }
    }

    private boolean noSelectedHandle() {
        int selectedIndex = table_id.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            return true;
        }
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Keine Auswahl");
        alert.setHeaderText("Keine Tierklasse ausgewählt");
        alert.setContentText("Bitte wählen Sie eine Tierklasse in der Tabelle aus.");

        alert.showAndWait();
        return false;
    }

    @FXML
    private void createClicked() {
        if (WindowUtil.openWindowWithoutClosing("class-create.fxml")) {
            updateTable();
        }
    }

    @FXML
    private void editClicked() {
        if (noSelectedHandle() && WindowUtil.openWindowWithoutClosing("class-edit.fxml", table_id.getSelectionModel().getSelectedItem())) {
            updateTable();
        }
    }

    @FXML
    private void deleteClicked() {
        if (noSelectedHandle()) {
            service.delete(table_id.getSelectionModel().getSelectedItem().getId());
            updateTable();
        }
    }

}