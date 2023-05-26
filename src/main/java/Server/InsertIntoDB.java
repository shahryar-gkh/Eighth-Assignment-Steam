package Server;
import java.util.Objects;
import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class InsertIntoDB {
    public static void listFilesForFolder(File folder, String databaseName) {
        String url = "jdbc:postgresql://localhost:5432/" + databaseName;
        String user = "postgres";
        String pass = "123456";
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, user, pass);
            System.out.println("Connected to the database successfully.");
            Statement statement = connection.createStatement();
//            statement.executeUpdate("CREATE TABLE GAMES " +
//                    "(ID INT PRIMARY KEY NOT NULL, " +
//                    "TITLE TEXT NOT NULL, " +
//                    "DEVELOPER TEXT NOT NULL, " +
//                    "GENRE TEXT NOT NULL, " +
//                    "PRICE DOUBLE PRECISION NOT NULL, " +
//                    "RELEASE_YEAR INTEGER NOT NULL, " +
//                    "CONTROLLER_SUPPORT BOOLEAN NOT NULL, " +
//                    "REVIEWS INTEGER, " +
//                    "SIZE INTEGER NOT NULL, " +
//                    "FILE_PATH TEXT NOT NULL)");
//            statement.executeUpdate("INSERT INTO GAMES " +
//                    "(ID, TITLE, DEVELOPER, GENRE, PRICE, RELEASE_YEAR, CONTROLLER_SUPPORT, REVIEWS, SIZE, FILE_PATH) " +
//                    "VALUES (2838298, 'Minecraft', 'idk', 'pixels', 8.99, 1998, true, 82, 133, 'C')");
//            ResultSet result = statement.executeQuery("SELECT * FROM GAMES");
//            result.next();
            for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
                if (fileEntry.isDirectory()) {
                    listFilesForFolder(fileEntry, databaseName);
                }
                else if (fileEntry.getName().endsWith(".txt")) {
                    Scanner reader = new Scanner(fileEntry);
                    Object[] fileResult = new Object[10];
                    fileResult[9] = "'" + fileEntry.getCanonicalPath().replace(".txt", ".png") + "'";
                    System.out.println(fileResult[9]);
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
                    System.out.println(sql);
                    statement.executeUpdate(sql);
                    System.out.println("Inserted " + fileResult[1] + " successfully!");
                    reader.close();
                }
            }
            statement.close();
            connection.close();
        }
        catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        final File folder = new File("C:\\Users\\USER\\IdeaProjects\\Eighth-Assignment-Steam\\src\\main\\java\\Server\\Resources");
        listFilesForFolder(folder, "steam");
    }
}