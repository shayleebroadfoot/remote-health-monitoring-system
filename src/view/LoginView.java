package view;

import domain.Employee;

import java.util.Scanner;

public class LoginView
{
    private final Scanner scanner;

    public LoginView(Scanner scanner)
    {
        this.scanner = scanner;
    }

    public void showWelcome()
    {
        System.out.println();
        System.out.println("=================================================");
        System.out.println("        Remote Patient Monitoring System         ");
        System.out.println("=================================================");
        System.out.println("Type 'q', 'quit', or 'exit' at any prompt to quit.");
        System.out.println();
    }

    public String promptUsername()
    {
        System.out.print("Username: ");
        String input = scanner.nextLine();

        return input != null ? input.trim() : "";
    }

    public String promptPassword()
    {
        System.out.print("Password: ");
        String input = scanner.nextLine();

        return input != null ? input.trim() : "";
    }

    public void showLoginSuccess(Employee employee)
    {
        if (employee != null)
            System.out.println("Logged in as: " + employee.getFullName() + " (" + employee.getRole() + ")");
        else
            System.out.println("Login successful.");
    }

    public void showLoginFailure()
    {
        System.out.println("Login failed.");
    }

    public boolean askRetry()
    {
        while (true)
        {
            System.out.print("Try again? (y/n): ");
            String input = scanner.nextLine();

            if (input == null)
                continue;

            String trimmed = input.trim().toLowerCase();

            if (trimmed.equals("y") || trimmed.equals("yes"))
                return true;

            if (trimmed.equals("n") || trimmed.equals("no"))
                return false;

            System.out.println("Please enter 'y' or 'n'.");
        }
    }

    public void showLoggedOut()
    {
        System.out.println("You have been logged out.");
    }
}
