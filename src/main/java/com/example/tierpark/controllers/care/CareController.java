package com.example.tierpark.controllers.care;

import com.example.tierpark.controllers.NavbarController;
import com.example.tierpark.entities.Care;
import com.example.tierpark.entities.User;
import com.example.tierpark.services.impl.AnimalService;
import com.example.tierpark.services.impl.CareService;
import com.example.tierpark.services.impl.CareTypeService;
import com.example.tierpark.services.impl.UserService;
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

public class CareController extends NavbarController {
    @FXML
    private Label label_id;
    @FXML
    private Label label_care_type;
    @FXML
    private Label label_keeper;
    @FXML
    private Label label_animal;
    @FXML
    private Label label_done;

    @FXML
    private TableColumn<Care, Integer> col_id;
    @FXML
    private TableColumn<Care, String> col_care_type;
    @FXML
    private TableColumn<Care, String> col_animal;
    @FXML
    private TableColumn<Care, String> col_keeper;
    @FXML
    private TableColumn<Care, String> col_done;
    @FXML
    private TableView<Care> table_id;

    private CareService service;
    private UserService userService;
    private CareTypeService careTypeService;
    private AnimalService animalService;
    private ObservableList<Care> careList;

    private Map<Integer, String> careTypeCache = new HashMap<>();
    private Map<Integer, String> animalCache = new HashMap<>();
    private Map<Integer, User> userCache = new HashMap<>();

    @FXML
    private void initialize() {
        service = new CareService();
        fileName = service.getTableName();

        userService = new UserService();
        careTypeService = new CareTypeService();
        animalService = new AnimalService();

        // Load all data once
        try {
            careList = FXCollections.observableArrayList(service.readAll());
            loadCareTypes();
            loadAnimals();
            loadUsers();

            col_id.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
            col_care_type.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(getCareTypeName(cellData.getValue().getCareTypeId())));
            col_animal.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(getAnimalName(cellData.getValue().getAnimalId())));
            col_keeper.setCellValueFactory(cellData -> {
                User keeper = getUser(cellData.getValue().getKeeperId());
                return new ReadOnlyStringWrapper(keeper.getName() + " " + keeper.getLastname());
            });
            col_done.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(DateUtil.format(cellData.getValue().getDone())));

            updateTable();
        }
        catch (NullPointerException e){

        }
    }

    private void loadCareTypes() {
        careTypeService.readAll().forEach(careType -> careTypeCache.put(careType.getId(), careType.getName()));
    }

    private void loadAnimals() {
        animalService.readAll().forEach(animal -> animalCache.put(animal.getId(), animal.getName()));
    }

    private void loadUsers() {
        userService.readAll().forEach(user -> userCache.put(user.getId(), user));
    }

    private String getCareTypeName(int careTypeId) {
        return careTypeCache.getOrDefault(careTypeId, "Unknown");
    }

    private String getAnimalName(int animalId) {
        return animalCache.getOrDefault(animalId, "Unknown");
    }

    private User getUser(int userId) {
        return userCache.getOrDefault(userId, new User());
    }

    private void updateTable() {
        table_id.setItems(careList);
        table_id.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showDetails(newValue));
    }

    private void showDetails(Care care) {
        if (care != null) {
            label_id.setText(String.valueOf(care.getId()));
            label_care_type.setText(getCareTypeName(care.getCareTypeId()));
            label_done.setText(DateUtil.format(care.getDone()));
            label_animal.setText(getAnimalName(care.getAnimalId()));
            User keeper = getUser(care.getKeeperId());
            label_keeper.setText(keeper.getName() + " " + keeper.getLastname());
        } else {
            clearLabels();
        }
    }

    private void clearLabels() {
        label_id.setText("");
        label_care_type.setText("");
        label_done.setText("");
        label_animal.setText("");
        label_keeper.setText("");
    }

    private boolean noSelectedHandle() {
        int selectedIndex = table_id.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            return true;
        }
        showAlert("Keine Auswahl", "Keine Pflege ausgewählt", "Bitte wählen Sie eine Pflege in der Tabelle aus.");
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
        if (WindowUtil.openWindowWithoutClosing("care-create.fxml")) {
            refreshCareList();
        }
    }

    @FXML
    private void editClicked() {
        if (noSelectedHandle() && WindowUtil.openWindowWithoutClosing("care-edit.fxml", table_id.getSelectionModel().getSelectedItem())) {
            refreshCareList();
        }
    }

    @FXML
    private void deleteClicked() {
        if (noSelectedHandle()) {
            service.delete(table_id.getSelectionModel().getSelectedItem().getId());
            refreshCareList();
        }
    }

    private void refreshCareList() {
        careList.setAll(service.readAll());
        table_id.refresh();
    }

    @Override
    protected void setupXML() {
        for (Care care : careList) {
            Element careElement = document.createElement("Care");
            rootElement.appendChild(careElement);

            Element id = document.createElement("ID");
            id.appendChild(document.createTextNode(String.valueOf(care.getId())));
            careElement.appendChild(id);

            Element careType = document.createElement("CareType");
            careType.appendChild(document.createTextNode(getCareTypeName(care.getCareTypeId())));
            careElement.appendChild(careType);

            Element done = document.createElement("Done");
            done.appendChild(document.createTextNode(DateUtil.format(care.getDone())));
            careElement.appendChild(done);

            Element animal = document.createElement("Animal");
            animal.appendChild(document.createTextNode(getAnimalName(care.getAnimalId())));
            careElement.appendChild(animal);

            Element keeper = document.createElement("Keeper");
            User keeperUser = getUser(care.getKeeperId());
            keeper.appendChild(document.createTextNode(keeperUser.getName() + " " + keeperUser.getLastname()));
            careElement.appendChild(keeper);
        }
    }
}
