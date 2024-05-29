package com.example.tierpark.controllers.animalTypes;

import com.example.tierpark.controllers.NavbarController;
import com.example.tierpark.entities.AnimalFamily;
import com.example.tierpark.entities.AnimalType;
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
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;

public class AnimalTypeController extends NavbarController {

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
    private ObservableList<AnimalType> typeList;
    private Map<Integer, AnimalFamily> animalFamilyCache = new HashMap<>();

    @FXML
    private void initialize() {
        service = new AnimalTypeService();
        fileName = service.getTableName();

        animalFamilyService = new AnimalFamilyService();

        // Load all data once
        typeList = FXCollections.observableArrayList(service.readAll());
        loadAnimalFamilies();

        col_id.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        col_name.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        col_family.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(getAnimalFamilyName(cellData.getValue().getFamilyId())));

        updateTable();
    }

    private void loadAnimalFamilies() {
        for (AnimalFamily animalFamily : animalFamilyService.readAll()) {
            animalFamilyCache.put(animalFamily.getId(), animalFamily);
        }
    }

    private String getAnimalFamilyName(int familyId) {
        AnimalFamily animalFamily = animalFamilyCache.get(familyId);
        return (animalFamily != null) ? animalFamily.getName() : "Unknown";
    }

    private void updateTable() {
        table_id.setItems(typeList);
        table_id.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showDetails(newValue));
    }

    private void showDetails(AnimalType animalType) {
        if (animalType != null) {
            label_id.setText(String.valueOf(animalType.getId()));
            label_name.setText(animalType.getName());
            label_family.setText(getAnimalFamilyName(animalType.getFamilyId()));
        } else {
            clearLabels();
        }
    }

    private void clearLabels() {
        label_id.setText("");
        label_name.setText("");
        label_family.setText("");
    }

    private boolean noSelectedHandle() {
        int selectedIndex = table_id.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            return true;
        }
        showAlert("Keine Auswahl", "Keiner Tiertyp ausgewählt", "Bitte wählen Sie einer Tiertyp in der Tabelle aus.");
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
        if (WindowUtil.openWindowWithoutClosing("animal-type-create.fxml")) {
            refreshTypeList();
        }
    }

    @FXML
    private void editClicked() {
        if (noSelectedHandle() && WindowUtil.openWindowWithoutClosing("animal-type-edit.fxml", table_id.getSelectionModel().getSelectedItem())) {
            refreshTypeList();
        }
    }

    @FXML
    private void deleteClicked() {
        if (noSelectedHandle()) {
            service.delete(table_id.getSelectionModel().getSelectedItem().getId());
            refreshTypeList();
        }
    }

    private void refreshTypeList() {
        typeList.setAll(service.readAll());
        table_id.refresh();
    }

    @Override
    protected void setupXML() {
        for (AnimalType animalType : typeList) {
            Element typeElement = document.createElement("AnimalType");
            rootElement.appendChild(typeElement);

            Element id = document.createElement("ID");
            id.appendChild(document.createTextNode(String.valueOf(animalType.getId())));
            typeElement.appendChild(id);

            Element name = document.createElement("Name");
            name.appendChild(document.createTextNode(animalType.getName()));
            typeElement.appendChild(name);

            Element family = document.createElement("AnimalFamily");
            family.appendChild(document.createTextNode(getAnimalFamilyName(animalType.getFamilyId())));
            typeElement.appendChild(family);
        }
    }
}
