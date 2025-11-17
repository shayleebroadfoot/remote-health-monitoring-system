package view;

import controller.LoginController;
import java.util.Scanner;

public class LoginView
{
    private final LoginController controller;
    private final Scanner scanner;

    public LoginView(LoginController controller)
    {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public boolean display()
    {
        System.out.println("==== Login ====");

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        boolean success = controller.login(username, password);

        if (success)
        {
            showSuccessMessage("Login successful.");
            return true;
        }
        else
        {
            showErrorMessage("Invalid username or password.");
            return false;
        }
    }

    public void showSuccessMessage(String message)
    {
        System.out.println("[SUCCESS] " + message);
    }

    public void showErrorMessage(String message)
    {
        System.out.println("[ERROR] " + message);
    }
}
