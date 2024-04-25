package com.example.tierpark.controllers.animalFamily;

import com.example.tierpark.controllers.NavbarController;
import com.example.tierpark.entities.AnimalClass;
import com.example.tierpark.entities.AnimalFamily;
import com.example.tierpark.services.impl.AnimalClassService;
import com.example.tierpark.services.impl.AnimalFamilyService;
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

public class FamilyController extends NavbarController {


    @FXML
    private Label label_id;

    @FXML
    private Label label_name;

    @FXML
    private Label label_class;


    @FXML
    private TableColumn<AnimalFamily, Integer> col_id;

    @FXML
    private TableColumn<AnimalFamily, String> col_name;

    @FXML
    private TableColumn<AnimalFamily, String> col_class;

    @FXML
    private TableView<AnimalFamily> table_id;

    private AnimalFamilyService service;
    private AnimalClassService animalClassService;

    @FXML
    private void initialize() {
        service = new AnimalFamilyService();
        animalClassService = new AnimalClassService();
        col_id.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getId()));
        col_name.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        col_class.setCellValueFactory(cellDate -> new ReadOnlyStringWrapper(animalClassService.readById(cellDate.getValue().getClassId()).getName()));
        updateTable();
    }

    private void updateTable() {
        ObservableList<AnimalFamily> entities = FXCollections.observableArrayList(service.readAll());
        table_id.setItems(entities);
        table_id.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showDetails(newValue));
    }

    private void showDetails(AnimalFamily animalFamily) {
        if (animalFamily != null) {
            label_id.setText(animalFamily.getId() + "");
            label_name.setText(animalFamily.getName());
            label_class.setText(animalClassService.readById(animalFamily.getClassId()).getName());
        } else {
            label_id.setText("");
            label_name.setText("");
            label_class.setText("");
        }
    }

    private boolean noSelectedHandle() {
        int selectedIndex = table_id.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            return true;
        }
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Keine Auswahl");
        alert.setHeaderText("Keine Tierfamilie ausgewählt");
        alert.setContentText("Bitte wählen Sie eine Tierfamilie in der Tabelle aus.");

        alert.showAndWait();
        return false;
    }

    @FXML
    private void createClicked() {
        if (WindowUtil.openWindowWithoutClosing("family-create.fxml")) {
            updateTable();
        }
    }

    @FXML
    private void editClicked() {
        if (noSelectedHandle() && WindowUtil.openWindowWithoutClosing("family-edit.fxml", table_id.getSelectionModel().getSelectedItem())) {
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
