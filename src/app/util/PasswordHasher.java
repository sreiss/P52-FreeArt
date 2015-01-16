package app.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Simon on 16/01/2015.
 */
public class PasswordHasher {
    public static String hashPassword(String password) {
        String hashedPassword = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(password.getBytes());
            byte[] hashBytes = messageDigest.digest();
            messageDigest.update(hashBytes);
            hashBytes = messageDigest.digest();

            StringBuilder stringBuilder = new StringBuilder();
            for(int i=0; i< hashBytes.length ;i++)
            {
                stringBuilder.append(Integer.toString((hashBytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            hashedPassword = stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            hashedPassword = "";
        }
        return hashedPassword;
    }
}
