package util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.github.cozyloon.EzConfig;
import model.DBDetails;
import model.DBJsonDetails;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBHelper {

    private static final String SQL_ERROR = "SQL Error";
    private String dbUrl;
    private String userName;
    private String password;

    public DBHelper(String dbUrl, String dbUserName, String dbPassword) {
        this.dbUrl = dbUrl;
        this.userName = dbUserName;
        this.password = dbPassword;
    }

    private Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(dbUrl, userName, password);
        } catch (Exception e) {
            EzConfig.logERROR("DB Connection error", e);
        }
        return DriverManager.getConnection(dbUrl, userName, password);
    }

    public void executeQuery(String query) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            EzConfig.logERROR(SQL_ERROR, e);
        } finally {
            if (preparedStatement != null)
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    EzConfig.logERROR(SQL_ERROR, e);
                }
            if (connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    EzConfig.logERROR(SQL_ERROR, e);
                }
        }
    }

    public void executeDBPostStepsWithLoggers(ResultSet resultSet, Connection connection, PreparedStatement preparedStatement) {
        if (resultSet != null)
            try {
                resultSet.close();
            } catch (SQLException e) {
                EzConfig.logERROR(SQL_ERROR, e);
            }
        if (preparedStatement != null)
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                EzConfig.logERROR(SQL_ERROR, e);
            }
        if (connection != null)
            try {
                connection.close();
            } catch (SQLException e) {
                EzConfig.logERROR(SQL_ERROR, e);
            }
    }

    public DBDetails getUserDetails(String name) {
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        DBDetails dbDetails = new DBDetails();

        String query = "select * from employee where name =?";
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                dbDetails.setName(resultSet.getString("name"));
                dbDetails.setAddress(resultSet.getString("address"));
                dbDetails.setAge(resultSet.getInt("age"));
            }
        } catch (SQLException e) {
            EzConfig.logERROR(SQL_ERROR, e);
        } finally {
            executeDBPostStepsWithLoggers(resultSet, connection, preparedStatement);
        }
        return dbDetails;
    }

    public List<DBJsonDetails> getJsonDetails(String customerName) {
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<DBJsonDetails> jsonDetails = new ArrayList<>();

        String query = "SELECT info FROM orders WHERE info->>'customer' = ?;";
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, customerName);
            resultSet = preparedStatement.executeQuery();

            ObjectMapper objectMapper = new ObjectMapper();
            while (resultSet.next()) {
                DBJsonDetails json = objectMapper.readValue(resultSet.getString("info"), DBJsonDetails.class);
                jsonDetails.add(json);
            }
        } catch (SQLException e) {
            EzConfig.logERROR(SQL_ERROR, e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } finally {
            executeDBPostStepsWithLoggers(resultSet, connection, preparedStatement);
        }
        return jsonDetails;
    }
    
 public String getJsonResponsePostgre(String id) {
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "SELECT info FROM orders WHERE info->>'customer' = ?;";
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();

            ObjectMapper objectMapper = new ObjectMapper();
            ArrayNode jsonArray = objectMapper.createArrayNode();
            while (resultSet.next()) {
                JsonNode jsonNode = objectMapper.readTree(resultSet.getString("info"));
                jsonArray.add(jsonNode);
            }
            return jsonArray.toString();
        } catch (SQLException e) {
            EzConfig.logERROR(SQL_ERROR, e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } finally {
            executeDBPostStepsWithLoggers(resultSet, connection, preparedStatement);
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    EzConfig.logERROR(SQL_ERROR, e);
                }
            }
        }
        return null;
    }
    
 public String getJsonResponseObject(String id) {
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "SELECT info FROM orders WHERE info->>'customer' = ?;";
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String objectJson = resultSet.getString("info");
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectJson);
                return jsonNode.toString();
            } else {
                return null;
            }
        } catch (SQLException e) {
            EzConfig.logERROR(SQL_ERROR, e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } finally {
            executeDBPostStepsWithLoggers(resultSet, connection, preparedStatement);
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    EzConfig.logERROR(SQL_ERROR, e);
                }
            }
        }
        return null;
    }

}
