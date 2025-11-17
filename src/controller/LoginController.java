package controller;

import employee.AuthenticationService;
public class LoginController
{
    private final AuthenticationService authService;
    private boolean loggedIn = false;
    private String currentUser = null;

    public LoginController(AuthenticationService authService)
    {
        this.authService = authService;
    }

    /**
     * Try to log in with the given username and password.
     * Returns true on success, false on failure.
     */
    public boolean login(String username, String password)
    {
        boolean ok = authService.login(username, password);
        if (ok) {
            loggedIn = true;
            currentUser = username;
        } else {
            loggedIn = false;
            currentUser = null;
        }
        return ok;
    }

    public void logout()
    {
        loggedIn = false;
        currentUser = null;
    }

    public boolean isLoggedIn()
    {
        return loggedIn;
    }

    public String getCurrentUser()
    {
        return currentUser;
    }
}