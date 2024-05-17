package com.example.tierpark.controllers.animal;

import com.example.tierpark.entities.Animal;
import com.example.tierpark.entities.AnimalType;
import com.example.tierpark.entities.Building;
import com.example.tierpark.entities.Gender;
import com.example.tierpark.services.impl.AnimalService;
import com.example.tierpark.services.impl.AnimalTypeService;
import com.example.tierpark.services.impl.BuildingService;
import com.example.tierpark.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class AnimalCreateController {
    @FXML
    private TextField name_id;

    @FXML
    private ChoiceBox<String> animal_type_id;

    @FXML
    private ChoiceBox<String> building_id;

    @FXML
    private TextField birth_date;

    @FXML
    private ChoiceBox<String> gender_id;

    private AnimalService service;
    private AnimalTypeService animalTypeService;
    private BuildingService buildingService;

    private Map<String, Integer> animalTypes;
    private Map<String, Integer> buildings;
    private Map<String, Gender> genders;
    @FXML
    private void initialize(){
        service = new AnimalService();
        animalTypeService = new AnimalTypeService();
        buildingService = new BuildingService();
        animalTypes = new HashMap<>();
        for (AnimalType animalType : animalTypeService.readAll()){
            animal_type_id.getItems().add(animalType.getName());
            animalTypes.put(animalType.getName(), animalType.getId());
        }
        buildings = new HashMap<>();
        for (Building building : buildingService.readAll()){
            building_id.getItems().add(building.getName());
            buildings.put(building.getName(), building.getId());
        }

        genders = new HashMap<>();
        gender_id.getItems().add(Gender.MAN.toString());
        genders.put(Gender.MAN.toString(), Gender.MAN);
        gender_id.getItems().add(Gender.WOMAN.toString());
        genders.put(Gender.WOMAN.toString(), Gender.WOMAN);
    }
    @FXML
    private void createClicked() {
        if (isInputValid()) {
            service.insert(
                    Animal.builder()
                            .name(name_id.getText())
                            .birthdate(DateUtil.parse(birth_date.getText()))
                            .animalTypeId(animalTypes.get(animal_type_id.getValue()))
                            .buildingId(buildings.get(building_id.getValue()))
                            .gender(genders.get(gender_id.getValue()))
                            .build()
            );
            ((Stage) name_id.getScene().getWindow()).close();
        }

    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (name_id.getText() == null || name_id.getText().length() == 0) {
            errorMessage += "Kein gültiger Name!\n";
        }
        if (animal_type_id.getValue() == null || animal_type_id.getValue().length() == 0) {
            errorMessage += "Kein gültiger Tiertype!\n";
        }
        if (building_id.getValue() == null || building_id.getValue().length() == 0){
            errorMessage += "Kein gültiges Gebäude!\n";
        }
        if (birth_date.getText() == null || birth_date.getText().length() == 0) {
            errorMessage += "Kein gültiges Geburtsdatum!\n";
        } else {
            if (!DateUtil.validDate(birth_date.getText())) {
                errorMessage += "Kein gültiges Geburtsdatum. Verwenden Sie das Format tt.mm.JJJJ!\n";
            }
        }
        if (gender_id == null || gender_id.getValue().length() == 0){
            errorMessage += "Kein gültiges Geschlecht!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ungültige Felder");
            alert.setHeaderText("Bitte korrigieren Sie ungültige Felder");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
