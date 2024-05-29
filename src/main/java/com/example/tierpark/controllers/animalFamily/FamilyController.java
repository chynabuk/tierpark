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

import java.util.HashMap;
import java.util.Map;

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
    private ObservableList<AnimalFamily> familyList;
    private Map<Integer, AnimalClass> animalClassCache = new HashMap<>();

    @FXML
    private void initialize() {
        service = new AnimalFamilyService();
        animalClassService = new AnimalClassService();

        // Load all data once
        familyList = FXCollections.observableArrayList(service.readAll());
        loadAnimalClasses();

        col_id.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        col_name.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        col_class.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(getAnimalClassName(cellData.getValue().getClassId())));

        updateTable();
    }

    private void loadAnimalClasses() {
        for (AnimalClass animalClass : animalClassService.readAll()) {
            animalClassCache.put(animalClass.getId(), animalClass);
        }
    }

    private String getAnimalClassName(int classId) {
        AnimalClass animalClass = animalClassCache.get(classId);
        return (animalClass != null) ? animalClass.getName() : "Unknown";
    }

    private void updateTable() {
        table_id.setItems(familyList);
        table_id.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showDetails(newValue));
    }

    private void showDetails(AnimalFamily animalFamily) {
        if (animalFamily != null) {
            label_id.setText(String.valueOf(animalFamily.getId()));
            label_name.setText(animalFamily.getName());
            label_class.setText(getAnimalClassName(animalFamily.getClassId()));
        } else {
            clearLabels();
        }
    }

    private void clearLabels() {
        label_id.setText("");
        label_name.setText("");
        label_class.setText("");
    }

    private boolean noSelectedHandle() {
        int selectedIndex = table_id.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            return true;
        }
        showAlert("Keine Auswahl", "Keine Tierfamilie ausgewählt", "Bitte wählen Sie eine Tierfamilie in der Tabelle aus.");
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
        if (WindowUtil.openWindowWithoutClosing("family-create.fxml")) {
            refreshFamilyList();
        }
    }

    @FXML
    private void editClicked() {
        if (noSelectedHandle() && WindowUtil.openWindowWithoutClosing("family-edit.fxml", table_id.getSelectionModel().getSelectedItem())) {
            refreshFamilyList();
        }
    }

    @FXML
    private void deleteClicked() {
        if (noSelectedHandle()) {
            service.delete(table_id.getSelectionModel().getSelectedItem().getId());
            refreshFamilyList();
        }
    }

    private void refreshFamilyList() {
        familyList.setAll(service.readAll());
        table_id.refresh();
    }
}
