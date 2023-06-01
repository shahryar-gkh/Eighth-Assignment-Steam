package Client;
import java.net.*;
import java.io.*;

public class ClientMain
{
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream out = null;

    // constructor to put ip address and port
    public ClientMain(String ipAddress, int port)
    {
        // establish a connection
        try
        {
            socket = new Socket(ipAddress, port);
            System.out.println("Connected");

            // takes input from terminal
            input = new DataInputStream(System.in);

            // sends output to the socket
            out = new DataOutputStream(socket.getOutputStream());
        }
        catch(Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        // string to read message from input
        String line = "";

        // keep reading until "Over" is input
        while (!line.isEmpty())
        {
            try
            {
                line = input.readLine();
                out.writeUTF(line);
            }
            catch(IOException i)
            {
                System.out.println(i);
            }
        }

        try
        {
            input.close();
            out.close();
            socket.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }

    public static void main(String[] args)
    {
        ClientMain client = new ClientMain("127.0.0.1", 5000);
    }
}
