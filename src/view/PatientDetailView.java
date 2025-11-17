package view;

import controller.PatientController;
import domain.Patient;

import java.util.Optional;
import java.util.Scanner;

public class PatientDetailView
{
    private final PatientController controller;
    private final Scanner scanner;

    public PatientDetailView(PatientController controller)
    {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

//    public void showForPatientId(String patientId)
//    {
//        Optional<Patient> patientOpt = controller.getPatientById(patientId);
//
//        if (patientOpt.isEmpty())
//        {
//            showErrorMessage("Patient not found: " + patientId);
//            return;
//        }
//
//        Patient patient = patientOpt.get();
//
//        System.out.println("=== Patient Details ===");
//        System.out.println("ID: " + patient.getId());
//        System.out.println("Name: " + patient.getFullName());
//        System.out.println("Birth date: " + patient.getBirthDate());
//
//        if (patient.getContactInfo() != null)
//        {
//            System.out.println("Contact: " + patient.getContactInfo());
//        }
//
//        if (patient.getEnrollment() != null)
//        {
//            System.out.println("Enrollment: " + patient.getEnrollment());
//        }
//        else
//        {
//            System.out.println("Enrollment: (not enrolled)");
//        }
//
//        System.out.println();
//        System.out.println("Press Enter to return to the patient list...");
//        scanner.nextLine();
//    }

    public void showErrorMessage(String msg)
    {
        System.out.println("[ERROR] " + msg);
    }
}
