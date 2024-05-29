package com.example.tierpark.controllers.careType;

import com.example.tierpark.controllers.NavbarController;
import com.example.tierpark.entities.CareType;
import com.example.tierpark.services.impl.CareTypeService;
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

public class CareTypeController extends NavbarController {
    @FXML
    private Label label_id;

    @FXML
    private Label label_name;
    @FXML
    private Label label_description;

    @FXML
    private TableColumn<CareType, Integer> col_id;

    @FXML
    private TableColumn<CareType, String> col_name;

    @FXML
    private TableColumn<CareType, String> col_description;

    @FXML
    private TableView<CareType> table_id;

    private CareTypeService careTypeService;
    private ObservableList<CareType> careTypeList;

    @FXML
    private void initialize() {
        careTypeService = new CareTypeService();

        // Load all data once
        careTypeList = FXCollections.observableArrayList(careTypeService.readAll());

        col_id.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        col_name.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        col_description.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDescription()));

        updateTable();
    }

    private void updateTable() {
        table_id.setItems(careTypeList);
        table_id.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showDetails(newValue));
    }

    private void showDetails(CareType careType) {
        if (careType != null) {
            label_id.setText(String.valueOf(careType.getId()));
            label_name.setText(careType.getName());
            label_description.setText(careType.getDescription());
        } else {
            clearLabels();
        }
    }

    private void clearLabels() {
        label_id.setText("");
        label_name.setText("");
        label_description.setText("");
    }

    private boolean noSelectedHandle() {
        int selectedIndex = table_id.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            return true;
        }
        showAlert("Keine Auswahl", "Kein Pflegetyp ausgewählt", "Bitte wählen Sie einen Pflegetyp in der Tabelle aus.");
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
        if (WindowUtil.openWindowWithoutClosing("care-type-create.fxml")) {
            refreshCareTypeList();
        }
    }

    @FXML
    private void editClicked() {
        if (noSelectedHandle() && WindowUtil.openWindowWithoutClosing("care-type-edit.fxml", table_id.getSelectionModel().getSelectedItem())) {
            refreshCareTypeList();
        }
    }

    @FXML
    private void deleteClicked() {
        if (noSelectedHandle()) {
            careTypeService.delete(table_id.getSelectionModel().getSelectedItem().getId());
            refreshCareTypeList();
        }
    }

    private void refreshCareTypeList() {
        careTypeList.setAll(careTypeService.readAll());
        table_id.refresh();
    }
}
