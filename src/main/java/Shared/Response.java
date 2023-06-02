package Shared;


import java.util.Scanner;
import java.io.*;
import java.sql.*;

import Server.AccountsDatabase;
import org.json.*;

public class Response {
    public static String responseCreator(Statement statement, JSONObject clientRequest) throws SQLException {
        boolean doesUserExist = usernameTaken(statement, clientRequest);
        if (clientRequest.get("action").equals("helloMenu")) {
            return helloMenu(clientRequest);
        }
        else if (clientRequest.get("action").equals("checkLogin")) {
            return checkLogin(statement, clientRequest, doesUserExist);
        }
        else if (clientRequest.get("action").equals("checkSignup")) {
            return checkSignup(statement, clientRequest, doesUserExist);
        }
        else if (clientRequest.get("action").equals("homePage")) {
            return homePage(clientRequest);
        }
        return null;
    }

    public static String helloMenu(JSONObject receivedMessage) {
        receivedMessage.put("action", "helloMenu");
        return receivedMessage.toString();
    }

    public static boolean usernameTaken(Statement statement, JSONObject receivedMessage) throws SQLException {
        ResultSet result = statement.executeQuery("SELECT * FROM ACCOUNTS WHERE" +
                "USERNAME = '" + receivedMessage.get("username") + "'");
        result.next();
        int number = statement.executeUpdate("SELECT COUNT (*) AS NUMBER FROM " +
                "information_schema.columns WHERE table_name = 'ACCOUNTS'");
        return number == 0;
    }

    public static String checkLogin(Statement statement, JSONObject receivedMessage, boolean usernameTaken) throws SQLException {
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
                message.put("date", result.getString("date"));
                message.put("isLoggedIn", "true");
            }
            else {
                message.put("isLoggedIn", "false");
                message.put("error", "Incorrect password.");
            }
        }
        else {
            message.put("isLoggedIn", "false");
            message.put("error", "No account exists under this username.");
        }
        return message.toString();
    }

    public static String checkSignup(Statement statement, JSONObject receivedMessage, boolean usernameTaken) throws SQLException {
        JSONObject message = new JSONObject();
        message.put("id", receivedMessage.get("id"));
        message.put("username", receivedMessage.get("username"));
        message.put("password", receivedMessage.get("password"));
        message.put("date", receivedMessage.get("date"));
        if (usernameTaken) {
            message.put("isAllowed", "false");
        }
        else {
            AccountsDatabase.addAccount(statement, message);
            message.put("isAllowed", "true");
        }
        message.put("action", "signup");
        return message.toString();
    }

    public static String homePage(JSONObject receivedMessage) {
        JSONObject message = new JSONObject();
        message.put("action", receivedMessage.get("user"));
        message.put("action", "homePage");
        return message.toString();
    }
}
