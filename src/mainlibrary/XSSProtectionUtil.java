package mainlibrary;

public class XSSProtectionUtil {

    public static String sanitize(String input) {
        if (input == null) {
            return null;
        }
        // Replace special HTML characters with their respective escape sequences
        input = input.replaceAll("&", "&amp;");
        input = input.replaceAll("<", "&lt;");
        input = input.replaceAll(">", "&gt;");
        input = input.replaceAll("\"", "&quot;");
        input = input.replaceAll("'", "&#x27;");
        input = input.replaceAll("/", "&#x2F;");
        return input;
    }
}
