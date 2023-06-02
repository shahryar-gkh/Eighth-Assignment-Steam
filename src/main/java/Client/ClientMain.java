package Client;

import Shared.Request;
import org.json.JSONObject;

import java.net.*;
import java.io.*;

public class ClientMain {
    public static void main(String[] args) {
        Socket socket;
        InputStream input;
        OutputStream output;
        String ipAddress = "127.0.0.1";
        int port = 5000;
        try
        {
            socket = new Socket(ipAddress, port);
            System.out.println("Connected");
            input = socket.getInputStream();
            output = socket.getOutputStream();
            BufferedReader read = new BufferedReader(new InputStreamReader(input));
            String response = read.readLine();
            PrintWriter printWriter = new PrintWriter(output, true);
            while (!response.isEmpty() && !response.equals("null")) {
                try {
                    JSONObject request = new JSONObject(Request.requestCreator(new JSONObject(response)));
                    printWriter.println(request);
                    response = read.readLine();
                }
                catch(Exception e) {
                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
                }
            }
        }
        catch(Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
}
