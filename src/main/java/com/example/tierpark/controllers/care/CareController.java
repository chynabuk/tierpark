package com.example.tierpark.controllers.care;

import com.example.tierpark.controllers.NavbarController;
import com.example.tierpark.entities.Animal;
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

    @FXML
    private void initialize(){
        service = new CareService();
        userService = new UserService();
        careTypeService = new CareTypeService();
        animalService = new AnimalService();
        col_id.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        col_care_type.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(careTypeService.readById(cellData.getValue().getCareTypeId()).getName()));
        col_animal.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(animalService.readById(cellData.getValue().getAnimalId()).getName()));
        col_keeper.setCellValueFactory(cellData -> {
            User keeper = userService.readById(cellData.getValue().getKeeperId());
            return new ReadOnlyStringWrapper(keeper.getName() + " " + keeper.getLastname());
        });
        col_done.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(DateUtil.format(cellData.getValue().getDone())));
        updateTable();
    }

    private void updateTable() {
        ObservableList<Care> entities = FXCollections.observableArrayList(service.readAll());
        table_id.setItems(entities);
        table_id.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showDetails(newValue));
    }

    private void showDetails(Care care) {
        if (care != null) {
            label_id.setText(care.getId() + "");
            label_care_type.setText(careTypeService.readById(care.getCareTypeId()).getName());
            label_done.setText(DateUtil.format(care.getDone()));
            label_animal.setText(animalService.readById(care.getAnimalId()).getName());
            User keeper = userService.readById(care.getKeeperId());
            label_keeper.setText(keeper.getName() + " " + keeper.getLastname());
        } else {
            label_id.setText("");
            label_care_type.setText("");
            label_done.setText("");
            label_animal.setText("");
            label_keeper.setText("");
        }
    }

    private boolean noSelectedHandle() {
        int selectedIndex = table_id.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            return true;
        }
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Keine Auswahl");
        alert.setHeaderText("Keine Pflege ausgewählt");
        alert.setContentText("Bitte wählen Sie eine Pflege in der Tabelle aus.");

        alert.showAndWait();
        return false;
    }

    @FXML
    private void createClicked() {
        if (WindowUtil.openWindowWithoutClosing("care-create.fxml")) {
            updateTable();
        }
    }

    @FXML
    private void editClicked() {
    }

    @FXML
    private void deleteClicked() {
        if (noSelectedHandle()) {
            service.delete(table_id.getSelectionModel().getSelectedItem().getId());
            updateTable();
        }
    }
}
