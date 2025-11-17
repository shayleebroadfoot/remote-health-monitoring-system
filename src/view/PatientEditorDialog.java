package view;

import domain.Patient;
import java.util.Scanner;

public class PatientEditorDialog
{
    private Patient currentPatient;

    public Patient showCreateDialog(Scanner scanner)
    {
        System.out.println("=== Create Patient ===");
        currentPatient = new Patient();
        readFields(scanner, currentPatient);
        return currentPatient;
    }

    public Patient showEditDialog(Scanner scanner, Patient existing)
    {
        System.out.println("=== Edit Patient ===");
        currentPatient = existing;
        displayPatient(existing);
        readFields(scanner, currentPatient);
        return currentPatient;
    }

    public void displayPatient(Patient patient)
    {
        System.out.println("ID: " + patient.getId());
        System.out.println("First Name: " + patient.getFirstName());
        System.out.println("Last Name: " + patient.getLastName());
        // Add more fields when your Patient class has them
    }

    public Patient getEnteredPatient()
    {
        return currentPatient;
    }

    public void showError(String message)
    {
        System.out.println("[ERROR] " + message);
    }

    private void readFields(Scanner scanner, Patient patient)
    {
        System.out.print("ID: ");
        String id = scanner.nextLine().trim();
        if (!id.isEmpty())
        {
            patient.setId(id);
        }

        System.out.print("First Name: ");
        String firstName = scanner.nextLine().trim();
        if (!firstName.isEmpty())
        {
            patient.setFirstName(firstName);
        }

        System.out.print("Last Name: ");
        String lastName = scanner.nextLine().trim();
        if (!lastName.isEmpty())
        {
            patient.setLastName(lastName);
        }
    }
}
