package com.example.tierpark.services.impl;

import com.example.tierpark.entities.Animal;
import com.example.tierpark.entities.Gender;
import com.example.tierpark.services.CrudOperations;
import com.example.tierpark.util.JdbcSQLServerConnection;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AnimalService extends CrudOperations<Animal> {
    public AnimalService() {
        super(
                "Animals",
                "INSERT INTO Animals (name, birthdate, animal_type_id, building_id, gender_id) VALUES (?, ?, ?, ?, ?)",
                "UPDATE Animals SET name=?, birthdate=?, animal_type_id=?, building_id=?, gender_id=? where id=?");
    }

    @Override
    public Animal build(ResultSet resultSet) throws SQLException {
        return Animal.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .birthdate(resultSet.getDate("birthdate"))
                .animalTypeId(resultSet.getInt("animal_type_id"))
                .buildingId(resultSet.getInt("building_id"))
                .gender(resultSet.getInt("gender_id") == 1 ? Gender.MAN : Gender.WOMAN)
                .build();
    }

    @Override
    public void prepareStatementCreatingSetup(Animal object, PreparedStatement statement) throws SQLException {
        statement.setString(1, object.getName());
        statement.setDate(2, object.getBirthdate());
        statement.setInt(3, object.getAnimalTypeId());
        statement.setInt(4, object.getBuildingId());
        statement.setInt(5, object.getGender().getId());
    }

    @Override
    public void prepareStatementUpdatingSetup(Animal object, PreparedStatement statement) throws SQLException {
        prepareStatementCreatingSetup(object, statement);
        statement.setInt(6, object.getId());

    }

    @Override
    public void delete(int id) {
        deleteAnimalFromDependedTables(id, "Care");
        deleteAnimalFromDependedTables(id, "Feed_animals");
        super.delete(id);
    }

    private void deleteAnimalFromDependedTables(int id, String tableName){
        Connection connection = JdbcSQLServerConnection.connect();

        String sql = "DELETE FROM " + tableName + " WHERE animal_id=?";

        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            if (statement.executeUpdate() > 0){
                System.out.println("Record deleted");
            }
        }
        catch (SQLException e) {
            if ("42000".equals(e.getSQLState()) || e.getErrorCode() == 229) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erlaubnis verweigert");
                alert.setContentText("Die DELETE-Erlaubnis wurde für die Tabelle '" + tableName + "' verweigert");
                alert.showAndWait();
            } else {
                // Handle other SQL exceptions
                e.printStackTrace();
            }
        }
        finally {
            JdbcSQLServerConnection.close();
        }
    }
}
