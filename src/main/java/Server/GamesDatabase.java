package Server;
import java.util.Objects;
import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class GamesDatabase {
    public static void createGamesTable(Statement statement) throws SQLException {
        statement.executeUpdate("CREATE TABLE GAMES " +
        "(ID TEXT PRIMARY KEY NOT NULL, " +
        "TITLE TEXT NOT NULL, " +
        "DEVELOPER TEXT NOT NULL, " +
        "GENRE TEXT NOT NULL, " +
        "PRICE DOUBLE PRECISION NOT NULL, " +
        "RELEASE_YEAR INTEGER NOT NULL, " +
        "CONTROLLER_SUPPORT BOOLEAN NOT NULL, " +
        "REVIEWS INTEGER, " +
        "SIZE INTEGER NOT NULL, " +
        "FILE_PATH TEXT NOT NULL)");
        System.out.println("The games table has been created successfully!");
    }

    public static void putFilesInDatabase(File folder, Connection connection, Statement statement) throws IOException, SQLException {
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (fileEntry.isDirectory()) {
                putFilesInDatabase(fileEntry, connection, statement);
            }
            else if (fileEntry.getName().endsWith(".txt")) {
                Scanner reader = new Scanner(fileEntry);
                Object[] fileResult = new Object[10];
                fileResult[9] = "'" + fileEntry.getCanonicalPath().replace(".txt", ".png") + "'";
                int counter = 0;
                while (reader.hasNextLine()) {
                    String data = "'" + reader.nextLine() + "'";
                    fileResult[counter] = data;
                    counter++;
                }
                String sql = "INSERT INTO GAMES " +
                        "(ID, TITLE, DEVELOPER, GENRE, PRICE, RELEASE_YEAR, CONTROLLER_SUPPORT, REVIEWS, SIZE, FILE_PATH) " +
                        String.format("VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s)", fileResult[0], fileResult[1], fileResult[2],
                                fileResult[3], fileResult[4], fileResult[5], fileResult[6], fileResult[7], fileResult[8],fileResult[9]);
                statement.executeUpdate(sql);
                reader.close();
            }
        }
        System.out.println("All the games have been imported successfully!");
    }
}
