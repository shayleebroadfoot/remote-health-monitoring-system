public class AuthenticationService {
    private static AuthenticationService instance = new AuthenticationService();
    private boolean authenticated = false;
    private String loggedInUser;

    private AuthenticationService() {}

    public static AuthenticationService getInstance() {
        return instance;
    }

    public boolean login(String username, String password) {
        // Simple demo: any non-empty username
        if (username != null && !username.isEmpty()) {
            authenticated = true;
            loggedInUser = username;
            return true;
        }
        return false;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public String getLoggedInUser() {
        return loggedInUser;
    }
}
