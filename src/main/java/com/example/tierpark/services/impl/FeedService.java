package com.example.tierpark.services.impl;

import com.example.tierpark.entities.Feed;
import com.example.tierpark.services.CrudOperations;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FeedService extends CrudOperations<Feed> {
    public FeedService() {
        super(
                "Feeds",
                "INSERT INTO Feeds (name, measure, price_per_unit) VALUES (?, ?, ?)",
                "UPDATE Feeds SET name=?, measure=?, price_per_unit=? WHERE id=?"
                );
    }

    @Override
    public Feed build(ResultSet resultSet) throws SQLException {
        return Feed.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .measure(resultSet.getString("measure"))
                .pricePerUnit(resultSet.getInt("price_per_unit"))
                .build();
    }

    @Override
    public void prepareStatementCreatingSetup(Feed object, PreparedStatement statement) throws SQLException {
        statement.setString(1, object.getName());
        statement.setString(2, object.getMeasure());
        statement.setInt(3, object.getPricePerUnit());
    }

    @Override
    public void prepareStatementUpdatingSetup(Feed object, PreparedStatement statement) throws SQLException {
        prepareStatementCreatingSetup(object, statement);
        statement.setInt(4, object.getId());
    }
}
