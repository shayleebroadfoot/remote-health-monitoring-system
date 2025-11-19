package controller;

import monitoring.MonitoringModel;
import view.PatientDetailView;

import java.util.Scanner;

public class PatientDetailController
{
    private final MonitoringModel monitoringModel;
    private final PatientDetailView patientDetailView;
    private final Scanner scanner;

    public PatientDetailController(MonitoringModel monitoringModel, PatientDetailView patientDetailView, Scanner scanner)
    {
        this.monitoringModel = monitoringModel;
        this.patientDetailView = patientDetailView;
        this.scanner = scanner;
    }

    public void run()
    {
        monitoringModel.addObserver(patientDetailView);

        System.out.println();
        System.out.println("Opened patient detail view.");
        System.out.println("This screen will refresh automatically whenever new vitals are processed.");
        System.out.println("Press ENTER to return to the previous menu.");
        System.out.println();

        scanner.nextLine();

        monitoringModel.removeObserver(patientDetailView);

        System.out.println("Closed patient detail view.");
    }
}
