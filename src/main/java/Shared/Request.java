package Shared;

import org.json.JSONObject;
import java.util.Scanner;
import org.mindrot.jbcrypt.BCrypt;
import java.util.UUID;

public class Request {
    public String requestCreator(JSONObject serverResponse) {
        if (serverResponse.get("action").equals("loginMenu")) {
            return sendLoginMenuRequest();
        }
        return null;
    }

    public String sendLoginMenuRequest(JSONObject receivedMessage) {

        receivedMessage.put("connected", true);
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

}
