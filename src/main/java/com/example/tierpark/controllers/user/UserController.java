package com.example.tierpark.controllers.user;

import com.example.tierpark.controllers.NavbarController;
import com.example.tierpark.entities.Building;
import com.example.tierpark.entities.Gender;
import com.example.tierpark.entities.User;
import com.example.tierpark.services.impl.BuildingService;
import com.example.tierpark.services.impl.RoleService;
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

public class UserController extends NavbarController {
    @FXML
    private Label label_id;

    @FXML
    private Label label_login;

    @FXML
    private Label label_birthdate;

    @FXML
    private Label label_sex;

    @FXML
    private Label label_name;

    @FXML
    private Label label_lastname;

    @FXML
    private Label label_role;


    @FXML
    private TableColumn<User, Integer> col_id;

    @FXML
    private TableColumn<User, String> col_login;

    @FXML
    private TableColumn<User, String> col_name;

    @FXML
    private TableColumn<User, String> col_lastname;

    @FXML
    private TableColumn<User, String> col_birthdate;

    @FXML
    private TableColumn<User, String> col_gender;

    @FXML
    private TableColumn<User, String> col_role;

    @FXML
    private TableView<User> table_id;

    private UserService userService;
    private RoleService roleService;

    @FXML
    private void initialize() {
        userService = new UserService();
        roleService = new RoleService();
        col_id.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getId()));
        col_login.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getLogin()));
        col_name.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        col_lastname.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getLastname()));
        col_birthdate.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(DateUtil.format(cellData.getValue().getBirthDate())));
        col_gender.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getGenderId() == Gender.MAN.getId() ? Gender.MAN.toString() : Gender.WOMAN.toString()));
        col_role.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(roleService.readById(cellData.getValue().getRoleId()).getName()));
        updateTable();
    }

    private void updateTable() {
        ObservableList<User> users = FXCollections.observableArrayList(userService.readAll());
        table_id.setItems(users);
        table_id.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showDetails(newValue));
    }

    private void showDetails(User user) {
        if (user != null) {
            label_id.setText(user.getId() + "");
            label_login.setText(user.getLogin());
            label_birthdate.setText(DateUtil.format(user.getBirthDate()));
            label_sex.setText(user.getGenderId() == Gender.MAN.getId() ? Gender.MAN.toString() : Gender.WOMAN.toString());
            label_name.setText(user.getName());
            label_lastname.setText(user.getLastname());
            label_role.setText(roleService.readById(user.getRoleId()).getName());
        } else {
            label_id.setText("");
            label_login.setText("");
            label_birthdate.setText("");
            label_sex.setText("");
            label_name.setText("");
            label_lastname.setText("");
            label_role.setText("");
        }
    }

    private boolean noSelectedHandle() {
        int selectedIndex = table_id.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            return true;
        }
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Keine Auswahl");
        alert.setHeaderText("Kein Gebäude ausgewählt");
        alert.setContentText("Bitte wählen Sie ein Gebäude in der Tabelle aus.");

        alert.showAndWait();
        return false;
    }

    @FXML
    private void createClicked() {
        if (WindowUtil.openWindowWithoutClosing("building-create.fxml")) {
            updateTable();
        }
    }

    @FXML
    private void editClicked() {
        if (noSelectedHandle() && WindowUtil.openWindowWithoutClosing("user-edit.fxml", table_id.getSelectionModel().getSelectedItem())) {
            updateTable();
        }
    }

    @FXML
    private void deleteClicked() {
        if (noSelectedHandle()) {
            userService.delete(table_id.getSelectionModel().getSelectedItem().getId());
            updateTable();
        }
    }
}
