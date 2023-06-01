package Server;

import java.sql.*;

public class AccountsDatabase {
    public static void createAccountsTable (Statement statement) throws SQLException {
        statement.executeUpdate("CREATE TABLE ACCOUNTS " +
                "(ID TEXT PRIMARY KEY NOT NULL, " +
                "USERNAME TEXT NOT NULL, " +
                "HASHED_PASSWORD TEXT NOT NULL, " +
                "DATE_OF_BIRTH DATE NOT NULL)");
    }
}