package com.examples.etl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.util.Collector;

public class DimflatMapfunction extends RichFlatMapFunction {
    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        Class.forName("");
        String url = "";
        String userName = "";
        String password = "";
        String sql = "";
        try (Connection connection = DriverManager.getConnection(url, userName, password);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {

            }
        } catch (Exception e) {

        }
    }

    @Override
    public void flatMap(Object value, Collector out) throws Exception {

    }
}
