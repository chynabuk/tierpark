package com.example.tierpark.controllers.careType;

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

public class CareTypeController {
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
    private TableView<CareType> care_type_table;

    private CareTypeService careTypeService;


    @FXML
    private void initialize() {
        careTypeService = new CareTypeService();
        col_id.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getId()));
        col_name.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        col_description.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDescription()));
        updateTable();
    }

    private void updateTable() {
        ObservableList<CareType> careTypes = FXCollections.observableArrayList(careTypeService.readAll());
        care_type_table.setItems(careTypes);
        care_type_table.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showDetails(newValue));
    }

    private void showDetails(CareType careType) {
        if (careType != null) {
            label_id.setText(careType.getId() + "");
            label_name.setText(careType.getName());
            label_description.setText(careType.getDescription());
        } else {
            label_id.setText("");
            label_name.setText("");
            label_description.setText("");
        }
    }

    private boolean noSelectedHandle() {
        int selectedIndex = care_type_table.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            return true;
        }
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Keine Auswahl");
        alert.setHeaderText("Kein Pflegetyp ausgewählt");
        alert.setContentText("Bitte wählen Sie einen Pflegetyp in der Tabelle aus.");

        alert.showAndWait();
        return false;
    }

    @FXML
    private void createClicked() {
        if (WindowUtil.openWindowWithoutClosing("care-type-create.fxml")) {
            updateTable();
        }
    }

    @FXML
    private void editClicked() {
//        if (noSelectedHandle() && WindowUtil.openWindowWithoutClosing("feed-edit.fxml", feeds_table.getSelectionModel().getSelectedItem())) {
//            updateTable();
//        }
    }

    @FXML
    private void deleteClicked() {
        if (noSelectedHandle()) {
            careTypeService.delete(care_type_table.getSelectionModel().getSelectedItem().getId());
            updateTable();
        }
    }
}
