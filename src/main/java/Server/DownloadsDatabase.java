package Server;

import java.sql.*;
import org.json.JSONObject;

public class DownloadsDatabase {
    public static void createDownloadsTable(Statement statement) throws SQLException {
        statement.executeUpdate("CREATE TABLE DOWNLOADS " +
                "(ACCOUNT_ID TEXT NOT NULL, " +
                "GAME_ID TEXT NOT NULL, " +
                "DOWNLOAD_COUNT INTEGER NOT NULL)");
        System.out.println("The downloads table has been created successfully!");
    }
    public static void addAccount(Statement statement, JSONObject userRequest) throws SQLException {
        statement.executeUpdate("INSERT INTO ACCOUNTS VALUES ('" +
                userRequest.get("id") + "', '" + userRequest.get("username") +
                "', '" + userRequest.get("password") + "', '" +
                userRequest.get("date") + "')");
    }
}
