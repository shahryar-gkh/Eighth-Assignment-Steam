package Server;

import java.sql.*;
import org.json.JSONObject;

public class AccountsDatabase {
    public static void createAccountsTable(Statement statement) throws SQLException {
        statement.executeUpdate("CREATE TABLE ACCOUNTS " +
                "(ID TEXT PRIMARY KEY NOT NULL, " +
                "USERNAME TEXT NOT NULL, " +
                "HASHED_PASSWORD TEXT NOT NULL, " +
                "DATE_OF_BIRTH DATE NOT NULL)");
        System.out.println("The accounts table has been created successfully!");
    }

    public static void addAccount(Statement statement, JSONObject userRequest) throws SQLException {
        statement.executeUpdate("INSERT INTO ACCOUNTS VALUES ('" +
                userRequest.get("id") + "', '" + userRequest.get("username") +
                "', '" + userRequest.get("password") + "', '" +
                userRequest.get("date") + "')");
    }
}
