package view;

import java.util.Scanner;

public class MainMenuView
{
    private final Scanner scanner;

    public MainMenuView(Scanner scanner)
    {
        this.scanner = scanner;
    }

    public void showHeader()
    {
        System.out.println();
        System.out.println("=================================================");
        System.out.println("                   Main Menu                     ");
        System.out.println("=================================================");
    }

    public void showOptions()
    {
        System.out.println("1) Monitoring dashboard");
        System.out.println("2) Patient management");
        System.out.println("3) Device management");
        System.out.println("4) Logout");
        System.out.println("5) Exit application");
        System.out.println();
    }

    public int readChoice()
    {
        System.out.print("Select an option: ");

        while (!scanner.hasNextInt())
        {
            System.out.print("Please enter a number (1-5): ");
            scanner.next(); // discard invalid input
        }

        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return choice;
    }

    public void showInvalidChoice()
    {
        System.out.println("Invalid choice. Please select a valid option.");
    }

    public void showGoodbye()
    {
        System.out.println("Goodbye.");
    }
}
