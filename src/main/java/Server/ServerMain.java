package Server;

import Shared.Response;
import java.net.*;
import java.io.*;
import java.sql.*;

public class ServerMain {
    protected static String url = "jdbc:postgresql://localhost:5432/steam";
    protected static String user = "postgres";
    protected static String pass = "123456";

    protected static Connection connection;
    static {
        try {
            connection = DriverManager.getConnection(url, user, pass);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected static Statement statement;
    static {
        try {
            statement = connection.createStatement();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in = null;

    public ServerMain(int port) throws SQLException, IOException {
        this.server = new ServerSocket(port);
    }

    public void start() throws IOException {
        Socket socket = server.accept();
        System.out.println("Client connected: " + socket.getRemoteSocketAddress());
        ClientThreads threads = new ClientThreads(connection, socket);
        threads.start();
    }

    public static void main(String[] args) throws SQLException, IOException {
        /*
        I used these lines of codes to create the necessary tables and also  import the games data from the "resources" folder.
        I commented them because they are all part of a one-time process.
        */

        // final File folder = new File("C:\\Users\\USER\\IdeaProjects\\Eighth-Assignment-Steam\\src\\main\\java\\Server\\Resources");
        // GamesDatabase.createGamesTable(statement);
        // GamesDatabase.putFilesInDatabase(folder, statement);
        // AccountsDatabase.createAccountsTable(statement);
        // DownloadsDatabase.createDownloadsTable(statement);

        ServerMain server = new ServerMain(5000);
        server.start();
    }
}
