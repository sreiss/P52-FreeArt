package app.error;

import java.util.HashMap;

/**
 * Created by Simon on 13/01/2015.
 */
public class ErrorManager {
    private static ErrorManager instance;
    private HashMap<String, HashMap<String, String>> errorsMap;

    private ErrorManager() {
        init();
    }

    public static ErrorManager getInstance() {
        if (instance == null) {
            instance = new ErrorManager();
        }
        return instance;
    }

    public void init() {
        errorsMap = new HashMap<String, HashMap<String, String>>();
        errorsMap.put("signup", new HashMap<String, String>(){{
            put("errorwhilesaving", "An unknown error occured while we tried to create your account. Please retry.");
            put("invalidpassword", "The two password you gave didn't match!");
            put("existingusername", "The username you gave already exists. Please choose another one!");
            put("invalidemail", "Veuillez renseigner un email valide.");
        }});
        errorsMap.put("login", new HashMap<String, String>(){{
            put("invalidusername", "No users with this login where found.");
            put("invalidpassword", "Wrong password, please retry!");
            put("authneeded", "Authentication needed to access the \"account\" section!");
        }});
        errorsMap.put("account", new HashMap<String, String>(){{
            put("updatefailure", "We were not able to update your data, sorry.");
            put("passwordupdatefailure", "We were not able to update your password, sorry.");
            put("nofile", "You didn't select any file to upload.");
            put("nocategory", "You didn't select any category to add the upload to.");
            put("notitle", "You didn't give a title to your work.");
        }});
    }

    public String findError(String context, String type) {
        String foundError;
        HashMap<String, String> errorArea = errorsMap.get(context);
        if (errorArea != null) {
            String error = errorArea.get(type);
            if (error != null) {
                foundError = error;
            } else {
                foundError = "An unknow error occured";
            }
        } else {
            foundError = "An unknown error occured.";
        }

        return foundError;
    }

}
