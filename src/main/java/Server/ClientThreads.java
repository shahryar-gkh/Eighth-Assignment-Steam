package Server;

import Shared.Response;
import org.json.JSONObject;

import java.net.*;
import java.io.*;
import java.sql.*;

public class ClientThreads extends Thread{
    protected Socket socket;
    protected Connection connection;
    protected BufferedReader input;
    protected PrintWriter output;

    public ClientThreads(Connection connection, Socket socket) {
        this.connection = connection;
        this.socket = socket;
        try {
            this.output = new PrintWriter(socket.getOutputStream());
            this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
        try {
            Statement statement = connection.createStatement();
            String request = null;
            request = input.readLine();
            while (!request.isEmpty() && !request.equals("null")) {
                JSONObject message = new JSONObject(request);
                if (message.get("action").equals("end")) {
                    socket.close();
                }
                String response = Response.responseCreator(statement, message);
                this.output.println();
            }
        }
        catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }
}
