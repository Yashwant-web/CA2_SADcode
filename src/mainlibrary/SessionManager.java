package mainlibrary;

public class SessionManager {

    private static String loggedInUser;  // Static variable to store logged-in user

    // Method to set the logged-in user
    public static void setUser(String user) {
        loggedInUser = user;
    }

    // Method to get the logged-in user
    public static String getUser() {
        return loggedInUser;
    }

    // Method to clear the session
    public static void clearSession() {
        loggedInUser = null;  // Clear the session
    }

    // Method to check if a user is logged in
    public static boolean isLoggedIn() {
        return loggedInUser != null;  // If loggedInUser is not null, user is logged in
    }
}
