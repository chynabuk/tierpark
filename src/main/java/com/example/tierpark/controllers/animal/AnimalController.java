package com.example.tierpark.controllers.animal;

import com.example.tierpark.controllers.NavbarController;
import com.example.tierpark.entities.Animal;
import com.example.tierpark.entities.AnimalType;
import com.example.tierpark.entities.Building;
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
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;

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

    private AnimalService animalService;
    private AnimalTypeService animalTypeService;
    private BuildingService buildingService;
    private ObservableList<Animal> animalList;

    private Map<Integer, AnimalType> animalTypeCache = new HashMap<>();
    private Map<Integer, Building> buildingCache = new HashMap<>();

    @FXML
    private void initialize() {
        animalService = new AnimalService();
        fileName = animalService.getTableName();
        animalTypeService = new AnimalTypeService();
        buildingService = new BuildingService();

        // Load all data once
        animalList = FXCollections.observableArrayList(animalService.readAll());
        loadAnimalTypes();
        loadBuildings();

        col_id.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        col_name.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        col_birth_date.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(DateUtil.format(cellData.getValue().getBirthdate())));
        col_type.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(getAnimalTypeName(cellData.getValue().getAnimalTypeId())));
        col_building.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(getBuildingName(cellData.getValue().getBuildingId())));

        updateTable();
    }

    private void loadAnimalTypes() {
        for (AnimalType type : animalTypeService.readAll()) {
            animalTypeCache.put(type.getId(), type);
        }
    }

    private void loadBuildings() {
        for (Building building : buildingService.readAll()) {
            buildingCache.put(building.getId(), building);
        }
    }

    private String getAnimalTypeName(int typeId) {
        AnimalType type = animalTypeCache.get(typeId);
        return (type != null) ? type.getName() : "Unknown";
    }

    private String getBuildingName(int buildingId) {
        Building building = buildingCache.get(buildingId);
        return (building != null) ? building.getName() : "Unknown";
    }

    private void updateTable() {
        table_id.setItems(animalList);
        table_id.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showDetails(newValue));
    }

    private void showDetails(Animal animal) {
        if (animal != null) {
            label_id.setText(String.valueOf(animal.getId()));
            label_name.setText(animal.getName());
            label_birth_date.setText(DateUtil.format(animal.getBirthdate()));
            label_type.setText(getAnimalTypeName(animal.getAnimalTypeId()));
            label_building.setText(getBuildingName(animal.getBuildingId()));
            label_gender.setText(animal.getGender().toString());
        } else {
            clearLabels();
        }
    }

    private void clearLabels() {
        label_id.setText("");
        label_name.setText("");
        label_birth_date.setText("");
        label_type.setText("");
        label_building.setText("");
        label_gender.setText("");
    }

    private boolean noSelectedHandle() {
        int selectedIndex = table_id.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            return true;
        }
        showAlert("Keine Auswahl", "Kein Tier ausgewählt", "Bitte wählen Sie ein Tier in der Tabelle aus.");
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
        if (WindowUtil.openWindowWithoutClosing("animal-create.fxml")) {
            refreshAnimalList();
        }
    }

    @FXML
    private void editClicked() {
        if (noSelectedHandle() && WindowUtil.openWindowWithoutClosing("animal-edit.fxml", table_id.getSelectionModel().getSelectedItem())) {
            refreshAnimalList();
        }
    }

    @FXML
    private void deleteClicked() {
        if (noSelectedHandle()) {
            animalService.delete(table_id.getSelectionModel().getSelectedItem().getId());
            refreshAnimalList();
        }
    }

    private void refreshAnimalList() {
        animalList.setAll(animalService.readAll());
        table_id.refresh();
    }

    @Override
    protected void setupXML() {
        for (Animal animal : animalList) {
            Element animalElement = document.createElement("Animal");
            rootElement.appendChild(animalElement);

            Element id = document.createElement("ID");
            id.appendChild(document.createTextNode(String.valueOf(animal.getId())));
            animalElement.appendChild(id);

            Element name = document.createElement("Name");
            name.appendChild(document.createTextNode(animal.getName()));
            animalElement.appendChild(name);

            Element birthDate = document.createElement("BirthDate");
            birthDate.appendChild(document.createTextNode(DateUtil.format(animal.getBirthdate())));
            animalElement.appendChild(birthDate);

            Element type = document.createElement("Type");
            type.appendChild(document.createTextNode(getAnimalTypeName(animal.getAnimalTypeId())));
            animalElement.appendChild(type);

            Element building = document.createElement("Building");
            building.appendChild(document.createTextNode(getBuildingName(animal.getBuildingId())));
            animalElement.appendChild(building);

            Element gender = document.createElement("Gender");
            gender.appendChild(document.createTextNode(animal.getGender().toString()));
            animalElement.appendChild(gender);
        }
    }
}
