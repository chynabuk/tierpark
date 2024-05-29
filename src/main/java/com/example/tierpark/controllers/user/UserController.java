package com.example.tierpark.controllers.user;

import com.example.tierpark.controllers.NavbarController;
import com.example.tierpark.entities.Gender;
import com.example.tierpark.entities.User;
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
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;

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
    private ObservableList<User> userList;
    private Map<Integer, String> roleCache = new HashMap<>();

    @FXML
    private void initialize() {
        userService = new UserService();
        roleService = new RoleService();

        // Load all data once
        userList = FXCollections.observableArrayList(userService.readAll());
        loadRoles();

        col_id.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        col_login.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getLogin()));
        col_name.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        col_lastname.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getLastname()));
        col_birthdate.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(DateUtil.format(cellData.getValue().getBirthDate())));
        col_gender.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getGenderId() == Gender.MAN.getId() ? Gender.MAN.toString() : Gender.WOMAN.toString()));
        col_role.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(getRoleName(cellData.getValue().getRoleId())));

        updateTable();
    }

    private void loadRoles() {
        roleService.readAll().forEach(role -> roleCache.put(role.getId(), role.getName()));
    }

    private String getRoleName(int roleId) {
        return roleCache.getOrDefault(roleId, "Unknown");
    }

    private void updateTable() {
        table_id.setItems(userList);
        table_id.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showDetails(newValue));
    }

    private void showDetails(User user) {
        if (user != null) {
            label_id.setText(String.valueOf(user.getId()));
            label_login.setText(user.getLogin());
            label_birthdate.setText(DateUtil.format(user.getBirthDate()));
            label_sex.setText(user.getGenderId() == Gender.MAN.getId() ? Gender.MAN.toString() : Gender.WOMAN.toString());
            label_name.setText(user.getName());
            label_lastname.setText(user.getLastname());
            label_role.setText(getRoleName(user.getRoleId()));
        } else {
            clearLabels();
        }
    }

    private void clearLabels() {
        label_id.setText("");
        label_login.setText("");
        label_birthdate.setText("");
        label_sex.setText("");
        label_name.setText("");
        label_lastname.setText("");
        label_role.setText("");
    }

    private boolean noSelectedHandle() {
        int selectedIndex = table_id.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            return true;
        }
        showAlert("Keine Auswahl", "Kein Benutzer ausgewählt", "Bitte wählen Sie einen Benutzer in der Tabelle aus.");
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
        if (WindowUtil.openWindowWithoutClosing("user-create.fxml")) {
            refreshUserList();
        }
    }

    @FXML
    private void editClicked() {
        if (noSelectedHandle() && WindowUtil.openWindowWithoutClosing("user-edit.fxml", table_id.getSelectionModel().getSelectedItem())) {
            refreshUserList();
        }
    }

    @FXML
    private void deleteClicked() {
        if (noSelectedHandle()) {
            userService.delete(table_id.getSelectionModel().getSelectedItem().getId());
            refreshUserList();
        }
    }

    private void refreshUserList() {
        userList.setAll(userService.readAll());
        table_id.refresh();
    }

    @Override
    protected void setupXML() {
        for (User user : userList) {
            Element userElement = document.createElement("User");
            rootElement.appendChild(userElement);

            Element id = document.createElement("ID");
            id.appendChild(document.createTextNode(String.valueOf(user.getId())));
            userElement.appendChild(id);

            Element login = document.createElement("Login");
            login.appendChild(document.createTextNode(user.getLogin()));
            userElement.appendChild(login);

            Element name = document.createElement("Name");
            name.appendChild(document.createTextNode(user.getName()));
            userElement.appendChild(name);

            Element lastname = document.createElement("Lastname");
            lastname.appendChild(document.createTextNode(user.getLastname()));
            userElement.appendChild(lastname);

            Element birthdate = document.createElement("Birthdate");
            birthdate.appendChild(document.createTextNode(DateUtil.format(user.getBirthDate())));
            userElement.appendChild(birthdate);

            Element gender = document.createElement("Gender");
            gender.appendChild(document.createTextNode(user.getGenderId() == Gender.MAN.getId() ? Gender.MAN.toString() : Gender.WOMAN.toString()));
            userElement.appendChild(gender);

            Element role = document.createElement("Role");
            role.appendChild(document.createTextNode(getRoleName(user.getRoleId())));
            userElement.appendChild(role);
        }
    }
}
