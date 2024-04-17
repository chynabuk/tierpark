package com.example.tierpark.services.impl;

import com.example.tierpark.entities.FeedAnimal;
import com.example.tierpark.services.CrudOperations;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FeedAnimalService extends CrudOperations<FeedAnimal> {
    public FeedAnimalService() {
        super(
                "Feed_animals",
                "INSERT INTO Feed_animals (feed_datetime, amount_of_feed, feed_id, keeper_id, animal_id) VALUES (?, ?, ?, ?, ?)",
                "UPDATE Feed_animals SET feed_datetime=?, amount_of_feed=? WHERE id=?");
    }

    @Override
    public FeedAnimal build(ResultSet resultSet) throws SQLException {
        return FeedAnimal.builder()
                .id(resultSet.getInt("id"))
                .feedDateTime(resultSet.getTimestamp("feed_datetime"))
                .feedId(resultSet.getInt("feed_id"))
                .keeperId(resultSet.getInt("keeper_id"))
                .animalId(resultSet.getInt("animal_id"))
                .build();
    }

    @Override
    public void prepareStatementCreatingSetup(FeedAnimal object, PreparedStatement statement) throws SQLException {
        statement.setTimestamp(1, object.getFeedDateTime());
        statement.setInt(2, object.getFeedAmount());
        statement.setInt(3, object.getFeedId());
        statement.setInt(4, object.getKeeperId());
        statement.setInt(5, object.getAnimalId());
    }

    @Override
    public void prepareStatementUpdatingSetup(FeedAnimal object, PreparedStatement statement) throws SQLException {
        statement.setTimestamp(1, object.getFeedDateTime());
        statement.setInt(2, object.getFeedAmount());
        statement.setInt(3, object.getId());
    }
}
