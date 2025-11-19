package controller;

import employee.AuthenticationService;
import domain.Employee;
import view.LoginView;

public class LoginController
{
    private final AuthenticationService authenticationService;
    private final LoginView loginView;

    public LoginController(AuthenticationService authenticationService, LoginView loginView)
    {
        this.authenticationService = authenticationService;
        this.loginView = loginView;
    }

    /**
     * Runs the login screen.
     *
     * @return true if the user logged in successfully and we should continue
     *         into the main menu; false if the user chose to exit the app.
     */
    public boolean run()
    {
        boolean keepTrying = true;

        while (keepTrying)
        {
            loginView.showWelcome();

            String username = loginView.promptUsername();
            if (isExitCommand(username))
                return false;

            String password = loginView.promptPassword();
            if (isExitCommand(password))
                return false;

            boolean success = authenticationService.login(username, password);

            if (success)
            {
                Employee currentUser = authenticationService.getCurrentUser().orElse(null);
                loginView.showLoginSuccess(currentUser);
                return true;
            }
            else
            {
                loginView.showLoginFailure();
                keepTrying = loginView.askRetry();
            }
        }

        return false;
    }

    public void logout()
    {
        authenticationService.logout();
        loginView.showLoggedOut();
    }

    private boolean isExitCommand(String input)
    {
        if (input == null)
            return false;

        String trimmed = input.trim();
        return trimmed.equalsIgnoreCase("q") || trimmed.equalsIgnoreCase("quit") || trimmed.equalsIgnoreCase("exit");
    }
}
