package com.example.tierpark.controllers.animalTypes;

import com.example.tierpark.entities.AnimalFamily;
import com.example.tierpark.entities.AnimalType;
import com.example.tierpark.services.impl.AnimalClassService;
import com.example.tierpark.services.impl.AnimalFamilyService;
import com.example.tierpark.services.impl.AnimalTypeService;
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

public class AnimalTypeController {


    @FXML
    private Label label_id;

    @FXML
    private Label label_name;

    @FXML
    private Label label_family;


    @FXML
    private TableColumn<AnimalType, Integer> col_id;

    @FXML
    private TableColumn<AnimalType, String> col_name;

    @FXML
    private TableColumn<AnimalType, String> col_family;

    @FXML
    private TableView<AnimalType> table_id;

    private AnimalTypeService service;
    private AnimalFamilyService animalFamilyService;

    @FXML
    private void initialize() {
        service = new AnimalTypeService();
        animalFamilyService = new AnimalFamilyService();
        col_id.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getId()));
        col_name.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        col_family.setCellValueFactory(cellDate -> new ReadOnlyStringWrapper(animalFamilyService.readById(cellDate.getValue().getFamilyId()).getName()));
        updateTable();
    }

    private void updateTable() {
        ObservableList<AnimalType> entities = FXCollections.observableArrayList(service.readAll());
        table_id.setItems(entities);
        table_id.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showDetails(newValue));
    }

    private void showDetails(AnimalType animalType) {
        if (animalType != null) {
            label_id.setText(animalType.getId() + "");
            label_name.setText(animalType.getName());
            label_family.setText(animalFamilyService.readById(animalType.getFamilyId()).getName());
        } else {
            label_id.setText("");
            label_name.setText("");
            label_family.setText("");
        }
    }

    private boolean noSelectedHandle() {
        int selectedIndex = table_id.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            return true;
        }
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Keine Auswahl");
        alert.setHeaderText("Keiner Tiertyp ausgewählt");
        alert.setContentText("Bitte wählen Sie einer Tiertyp in der Tabelle aus.");

        alert.showAndWait();
        return false;
    }

    @FXML
    private void createClicked() {
        if (WindowUtil.openWindowWithoutClosing("animal-type-create.fxml")) {
            updateTable();
        }
    }

    @FXML
    private void editClicked() {
        if (noSelectedHandle() && WindowUtil.openWindowWithoutClosing("animal-type-edit.fxml", table_id.getSelectionModel().getSelectedItem())) {
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
