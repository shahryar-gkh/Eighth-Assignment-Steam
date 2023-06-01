package Server;
import java.net.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ServerMain
{
    //initialize socket and input stream
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in = null;
    protected static String url = "jdbc:postgresql://localhost:5432/steam";
    protected static String user = "postgres";
    protected static String pass = "123456";
    protected static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected static Statement statement;

    static {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ServerMain(int port) throws SQLException {
        // starts server and waits for a connection
        try
        {
            server = new ServerSocket(port);
            System.out.println("Server started");

            System.out.println("Waiting for a client ...");

            socket = server.accept();
            System.out.println("Client accepted");

            // takes input from the client socket
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

            String line = "";

            // reads message from client until "Over" is sent
            while (!line.isEmpty())
            {
                try
                {
                    line = in.readUTF();
                    System.out.println(line);
                }
                catch(IOException i)
                {
                    System.out.println(i);
                }
            }
            System.out.println("Closing connection");
            socket.close();
            in.close();
        }
        catch(Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public static void main(String[] args) throws SQLException {
        /*
        I used these lines of codes to create the necessary tables and also  import the games data from the "resources" folder.
        I commented them because they are all part of a one-time process.
        */

        //final File folder = new File("C:\\Users\\USER\\IdeaProjects\\Eighth-Assignment-Steam\\src\\main\\java\\Server\\Resources");
        //GamesDatabase.createGameTable(connection, statement);
        //GamesDatabase.putFilesInDatabase(folder, connection, statement);
        //AccountsDatabase.createAccountsTable(statement);

        ServerMain server = new ServerMain(5000);
    }
}
