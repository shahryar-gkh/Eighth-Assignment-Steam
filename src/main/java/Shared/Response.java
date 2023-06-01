package Shared;


import java.util.Scanner;
import java.io.*;
import java.sql.*;
import org.json.*;

public class Response {
    public String responseCreator(Statement statement, String clientRequest) throws SQLException {
        JSONObject jsonRequest = new JSONObject(clientRequest);
        boolean doesUserExist = usernameTaken(statement, jsonRequest);
        if (jsonRequest.get("action").equals("loginMenu")) {
            return loginMenu(jsonRequest);
        }
        else if (jsonRequest.get("action").equals("checkLogin")) {
            return checkLogin(statement, jsonRequest, doesUserExist);
        }
        else if (jsonRequest.get("action").equals("homePage")) {
            return homePage(jsonRequest);
        }
        return null;
    }

    public String loginMenu(JSONObject receivedMessage) {
        receivedMessage.put("action", "loginMenu");
        return receivedMessage.toString();
    }

    public boolean usernameTaken(Statement statement, JSONObject receivedMessage) throws SQLException {
        ResultSet result = statement.executeQuery("SELECT * FROM ACCOUNTS WHERE" +
                "USERNAME = '" + receivedMessage.get("username") + "'");
        result.next();
        int number = statement.executeUpdate("SELECT COUNT (*) AS NUMBER FROM " +
                "information_schema.columns WHERE table_name = 'ACCOUNTS'");
        return number == 0;
    }

    public String checkLogin(Statement statement, JSONObject receivedMessage, boolean usernameTaken) throws SQLException {
        JSONObject message = new JSONObject();
        message.put("action", "login");
        if (usernameTaken) {
            ResultSet result = statement.executeQuery("SELECT * FROM ACCOUNTS WHERE USERNAME = '"
                    + receivedMessage.get("username") + "'");
            result.next();
            if (result.getString("password").equals(receivedMessage.get("password"))) {
                message.put("username", result.getString("username"));
                message.put("password", result.getString("password"));
                message.put("id", result.getString("id"));
                message.put("date", result.getString("birth_date"));
                message.put("loggedIn", true);
            }
            else {
                message.put("loggedIn", false);
                message.put("error", "Incorrect password.");
            }
        }
        else {
            message.put("loggedIn", false);
            message.put("error", "No account exists under this username.");
        }
        return message.toString();
    }

    public String homePage(JSONObject receivedMessage) {
        JSONObject message = new JSONObject();
        message.put("action", receivedMessage.get("user"));
        message.put("action", "homePage");
        return message.toString();
    }
}
