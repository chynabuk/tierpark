package com.example.tierpark.controllers.animal;

import com.example.tierpark.controllers.NavbarController;
import com.example.tierpark.entities.Animal;
import com.example.tierpark.entities.AnimalType;
import com.example.tierpark.services.impl.AnimalService;
import com.example.tierpark.services.impl.AnimalTypeService;
import com.example.tierpark.services.impl.BuildingService;
import com.example.tierpark.util.DateUtil;
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

public class AnimalController extends NavbarController {
    @FXML
    private Label label_id;
    @FXML
    private Label label_name;
    @FXML
    private Label label_birth_date;
    @FXML
    private Label label_type;
    @FXML
    private Label label_building;
    @FXML
    private Label label_gender;

    @FXML
    private TableColumn<Animal, Integer> col_id;
    @FXML
    private TableColumn<Animal, String> col_name;
    @FXML
    private TableColumn<Animal, String> col_birth_date;
    @FXML
    private TableColumn<Animal, String> col_type;
    @FXML
    private TableColumn<Animal, String> col_building;
    @FXML
    private TableView<Animal> table_id;

    private AnimalService service;
    private AnimalTypeService animalTypeService;
    private BuildingService buildingService;

    @FXML
    private void initialize(){
        service = new AnimalService();
        animalTypeService = new AnimalTypeService();
        buildingService = new BuildingService();
        col_id.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getId()));
        col_name.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        col_birth_date.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(DateUtil.format(cellData.getValue().getBirthdate())));
        col_type.setCellValueFactory(cellDate -> new ReadOnlyStringWrapper(animalTypeService.readById(cellDate.getValue().getAnimalTypeId()).getName()));
        col_building.setCellValueFactory(cellDate -> new ReadOnlyStringWrapper(buildingService.readById(cellDate.getValue().getBuildingId()).getName()));
        updateTable();
    }

    private void updateTable() {
        ObservableList<Animal> entities = FXCollections.observableArrayList(service.readAll());
        table_id.setItems(entities);
        table_id.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showDetails(newValue));
    }

    private void showDetails(Animal animal) {
        if (animal != null) {
            label_id.setText(animal.getId() + "");
            label_name.setText(animal.getName());
            label_birth_date.setText(DateUtil.format(animal.getBirthdate()));
            label_type.setText(animalTypeService.readById(animal.getAnimalTypeId()).getName());
            label_building.setText(buildingService.readById(animal.getBuildingId()).getName());
            label_gender.setText(animal.getGender().toString());
        } else {
            label_id.setText("");
            label_name.setText("");
            label_birth_date.setText("");
            label_type.setText("");
            label_building.setText("");
            label_gender.setText("");
        }
    }

    private boolean noSelectedHandle() {
        int selectedIndex = table_id.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            return true;
        }
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Keine Auswahl");
        alert.setHeaderText("Kein Tier ausgewählt");
        alert.setContentText("Bitte wählen Sie ein Tier in der Tabelle aus.");

        alert.showAndWait();
        return false;
    }

    @FXML
    private void createClicked() {
        if (WindowUtil.openWindowWithoutClosing("animal-create.fxml")) {
            updateTable();
        }
    }

    @FXML
    private void editClicked() {
//        if (noSelectedHandle() && WindowUtil.openWindowWithoutClosing("animal-type-edit.fxml", table_id.getSelectionModel().getSelectedItem())) {
//            updateTable();
//        }
    }

    @FXML
    private void deleteClicked() {
        if (noSelectedHandle()) {
            service.delete(table_id.getSelectionModel().getSelectedItem().getId());
            updateTable();
        }
    }
}
