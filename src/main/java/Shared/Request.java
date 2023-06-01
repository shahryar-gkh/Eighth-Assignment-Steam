package Shared;

import org.json.JSONObject;
import java.util.Scanner;
import org.mindrot.jbcrypt.BCrypt;
import java.util.UUID;

public class Request {
    static final String salt = BCrypt.gensalt();
    public static String requestCreator(JSONObject serverResponse) {
        if (serverResponse.get("action").equals("helloMenu")) {
            return sendHelloMenuRequest(serverResponse);
        }
        else if (serverResponse.get("action").equals("signup")) {
//            return signup(serverResponse);
        }
        return null;
    }

    public static String sendHelloMenuRequest(JSONObject severMessage) {
        severMessage.put("connected", true);
        System.out.println("WELCOME TO STEAM\nWHERE YOU CAN DOWNLOAD\n" +
                "ANY GAME ON YOUR DEVICE\n\n1. LOG IN\n2. SIGN UP");
        Scanner input = new Scanner(System.in);
        int choice = Integer.parseInt(input.nextLine());
        if (choice == 1) {
            return sendLoginRequest();
        }
        else if (choice == 2) {
            return sendSignupRequest();
        }
        else if (choice == 3) {
            return endRequest();
        }
        return null;
    }

    public static String sendLoginRequest() {
        JSONObject message = new JSONObject();
        message.put("action", "checkLogin");
        Scanner input = new Scanner(System.in);
        System.out.println("\nUsername:");
        String username = input.nextLine();
        System.out.println("Password:");
        String password = input.nextLine();
        String hashedPassword = BCrypt.hashpw(password, salt);
        message.put("username", username);
        message.put("password", hashedPassword);
        return message.toString();
    }

    public static String sendSignupRequest() {
        JSONObject message = new JSONObject();
        message.put("action", "checkSignup");
        UUID id = UUID.randomUUID();
        Scanner input = new Scanner(System.in);
        System.out.println("\nUsername:");
        String username = input.nextLine();
        System.out.println("Password:");
        String password = input.nextLine();
        String hashedPassword = BCrypt.hashpw(password, salt);
        System.out.println("Date of birth (YYYY/MM/DD format):");
        String dateOfBirth = input.nextLine();
        message.put("id", id);
        message.put("username", username);
        message.put("password", hashedPassword);
        message.put("date", dateOfBirth);
        return message.toString();
    }

    public static String endRequest() {
        JSONObject message = new JSONObject();
        message.put("action", "end");
        return message.toString();
    }

//    public static String signup(JSONObject serverMessage) {
//        if (serverMessage.get("isAllowed").equals("true")) {
//            return mainMenu();
//        }
//    }
}
