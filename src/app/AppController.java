package app;

import controller.LoginController;
import controller.MonitoringController;
import controller.PatientDetailController;
import domain.MonitoredPatientData;
import domain.Patient;
import employee.AuthenticationService;
import monitoring.MonitoringModel;
import view.MainMenuView;
import view.PatientDetailView;

import java.util.List;
import java.util.Scanner;

public class AppController
{
    private final AuthenticationService authenticationService;
    private final LoginController loginController;
    private final MonitoringController monitoringController;
    private final MonitoringModel monitoringModel;
    private final MainMenuView mainMenuView;
    private final Scanner scanner;

    private Thread monitoringThread;

    public AppController(AuthenticationService authenticationService, LoginController loginController, MonitoringController monitoringController, MonitoringModel monitoringModel, MainMenuView mainMenuView, Scanner scanner)
    {
        this.authenticationService = authenticationService;
        this.loginController = loginController;
        this.monitoringController = monitoringController;
        this.monitoringModel = monitoringModel;
        this.mainMenuView = mainMenuView;
        this.scanner = scanner;
    }

    public void run()
    {
        boolean running = true;

        while (running)
        {
            boolean loggedIn = loginController.run();

            if (!loggedIn)
                break;

            showMainMenu();

            if (!authenticationService.isLoggedIn())
                continue;
        }

        stopContinuousMonitoring();
    }

    private void showMainMenu()
    {
        boolean inMenu = true;

        while (inMenu && authenticationService.isLoggedIn())
        {
            mainMenuView.showHeader();
            mainMenuView.showOptions();
            int choice = mainMenuView.readChoice();

            switch (choice)
            {
                case 1:
                    handleMonitoringMenu();
                    break;

                case 2:
                    System.out.println("Patient management not implemented yet.");
                    break;

                case 3:
                    System.out.println("Device management not implemented yet.");
                    break;

                case 4:
                    authenticationService.logout();
                    System.out.println("You have been logged out.");
                    inMenu = false;
                    break;

                case 5:
                    mainMenuView.showGoodbye();
                    stopContinuousMonitoring();
                    System.exit(0);
                    break;

                default:
                    mainMenuView.showInvalidChoice();
            }
        }
    }

    private void handleMonitoringMenu()
    {
        boolean inMonitoringMenu = true;

        while (inMonitoringMenu)
        {
            System.out.println();
            System.out.println("Monitoring Menu");
            System.out.println("---------------");
            System.out.println("1) Poll once and show dashboard");
            System.out.println("2) Start continuous monitoring (5s interval)");
            System.out.println("3) Stop continuous monitoring");
            System.out.println("4) Open patient detail view");
            System.out.println("5) Back to main menu");
            System.out.print("Select an option: ");

            String input = scanner.nextLine().trim();

            switch (input)
            {
                case "1":
                    monitoringController.pollAndUpdateOnce();
                    break;

                case "2":
                    startContinuousMonitoring(5000);
                    break;

                case "3":
                    stopContinuousMonitoring();
                    break;

                case "4":
                    openPatientDetailView();
                    break;

                case "5":
                    inMonitoringMenu = false;
                    break;

                default:
                    System.out.println("Please enter a number between 1 and 5.");
            }
        }
    }

    private void openPatientDetailView()
    {
        List<MonitoredPatientData> dataList = monitoringModel.getMonitoredPatientData();

        if (dataList == null || dataList.isEmpty())
        {
            System.out.println("No monitored patients available. Run monitoring first.");
            return;
        }

        System.out.println();
        System.out.println("Select a patient to view details:");
        for (int i = 0; i < dataList.size(); i++)
        {
            MonitoredPatientData data = dataList.get(i);
            Patient p = data.getPatient();
            String name = p != null ? p.getFullName() : "Unknown";
            String id = p != null ? p.getId() : "null";
            String status = data.getStatus() != null ? data.getStatus().toString() : "UNKNOWN";
            System.out.println((i + 1) + ") " + name + " (ID: " + id + ", Status: " + status + ")");
        }
        System.out.print("Enter a number (or press Enter to cancel): ");

        String input = scanner.nextLine().trim();
        if (input.isEmpty())
        {
            System.out.println("Cancelled.");
            return;
        }

        int index;
        try
        {
            index = Integer.parseInt(input) - 1;
        }
        catch (NumberFormatException e)
        {
            System.out.println("Invalid number.");
            return;
        }

        if (index < 0 || index >= dataList.size())
        {
            System.out.println("Selection out of range.");
            return;
        }

        MonitoredPatientData selected = dataList.get(index);
        Patient selectedPatient = selected.getPatient();

        if (selectedPatient == null || selectedPatient.getId() == null)
        {
            System.out.println("Selected entry has no valid patient ID.");
            return;
        }

        String patientId = selectedPatient.getId();

        PatientDetailView detailView = new PatientDetailView(patientId);
        PatientDetailController detailController = new PatientDetailController(monitoringModel, detailView, scanner);

        startContinuousMonitoring(5000);

        detailController.run();
    }


    private void startContinuousMonitoring(long intervalMillis)
    {
        if (monitoringThread != null && monitoringThread.isAlive())
        {
            System.out.println("Continuous monitoring is already running.");
            return;
        }

        monitoringThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                monitoringController.runContinuousMonitoring(intervalMillis);
            }
        });

        monitoringThread.setDaemon(true);
        monitoringThread.start();

        System.out.println("Started continuous monitoring every " + intervalMillis + " ms.");
    }

    private void stopContinuousMonitoring()
    {
        if (monitoringThread != null && monitoringThread.isAlive())
        {
            monitoringThread.interrupt();
            System.out.println("Stopping continuous monitoring...");
        }

        monitoringThread = null;
    }
}
