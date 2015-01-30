package app.util;

/**
 * Created by sreiss on 23/01/15.
 */
public class SlugBuilder {
    public static String removeForbiddenChars(String string) {
        String sanitzedString = string;
        char[] forbiddenChars = {',','_','.','?',';','!','/','\\','\n','\'','’','”','[',']','{','}','(',')','|','&',' ',':'};

        for (char forbiddenChar: forbiddenChars) {
            sanitzedString = sanitzedString.replace(forbiddenChar, '-');
        }

        return sanitzedString;
    }

    public static String sanitizeFileName(String rawFileName) {
        int extensionIndex = rawFileName.lastIndexOf('.');
        String extension = rawFileName.substring(extensionIndex + 1);
        String fileName = rawFileName.substring(0, extensionIndex);

        fileName = removeForbiddenChars(fileName).toLowerCase();

        return fileName + "." + extension;
    }

}
