package com.example.tierpark.controllers.building;

import com.example.tierpark.controllers.NavbarController;
import com.example.tierpark.entities.Building;
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

public class BuildingController extends NavbarController {
    @FXML
    private Label label_id;

    @FXML
    private Label label_name;

    @FXML
    private Label label_built_date;

    @FXML
    private TableColumn<Building, Integer> col_id;

    @FXML
    private TableColumn<Building, String> col_name;

    @FXML
    private TableColumn<Building, String> col_built_date;

    @FXML
    private TableView<Building> building_table;

    private BuildingService buildingService;
    private ObservableList<Building> buildingList;

    @FXML
    private void initialize() {
        buildingService = new BuildingService();

        // Load all data once
        buildingList = FXCollections.observableArrayList(buildingService.readAll());

        col_id.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        col_name.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        col_built_date.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(DateUtil.format(cellData.getValue().getBuiltDate())));

        updateTable();
    }

    private void updateTable() {
        building_table.setItems(buildingList);
        building_table.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showDetails(newValue));
    }

    private void showDetails(Building building) {
        if (building != null) {
            label_id.setText(String.valueOf(building.getId()));
            label_name.setText(building.getName());
            label_built_date.setText(DateUtil.format(building.getBuiltDate()));
        } else {
            clearLabels();
        }
    }

    private void clearLabels() {
        label_id.setText("");
        label_name.setText("");
        label_built_date.setText("");
    }

    private boolean noSelectedHandle() {
        int selectedIndex = building_table.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            return true;
        }
        showAlert("Keine Auswahl", "Kein Gebäude ausgewählt", "Bitte wählen Sie ein Gebäude in der Tabelle aus.");
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
        if (WindowUtil.openWindowWithoutClosing("building-create.fxml")) {
            refreshBuildingList();
        }
    }

    @FXML
    private void editClicked() {
        if (noSelectedHandle() && WindowUtil.openWindowWithoutClosing("building-edit.fxml", building_table.getSelectionModel().getSelectedItem())) {
            refreshBuildingList();
        }
    }

    @FXML
    private void deleteClicked() {
        if (noSelectedHandle()) {
            buildingService.delete(building_table.getSelectionModel().getSelectedItem().getId());
            refreshBuildingList();
        }
    }

    private void refreshBuildingList() {
        buildingList.setAll(buildingService.readAll());
        building_table.refresh();
    }
}
