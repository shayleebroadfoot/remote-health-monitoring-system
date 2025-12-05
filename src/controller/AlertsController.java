package controller;

import alert.AlertService;
import domain.Alert;
import monitoring.MonitoringModel;
import view.AlertsView;

import java.util.Scanner;

public class AlertsController
{
    private final MonitoringController monitoringController;
    private final MonitoringModel monitoringModel;
    private final AlertService alertService;
    private final AlertsView alertsView;
    private final Scanner scanner;

    public AlertsController(MonitoringController monitoringController, MonitoringModel monitoringModel, AlertService alertService, AlertsView alertsView, Scanner scanner)
    {
        this.monitoringController = monitoringController;
        this.monitoringModel = monitoringModel;
        this.alertService = alertService;
        this.alertsView = alertsView;
        this.scanner = scanner;

        this.monitoringModel.addObserver(alertsView);
    }

    public void run()
    {
        boolean inMenu = true;

        while (inMenu)
        {
            System.out.println();
            System.out.println("Alerts Menu");
            System.out.println("-----------");
            System.out.println("1) Poll once and refresh alerts");
            System.out.println("2) Show current active alerts");
            System.out.println("3) Acknowledge an alert by ID");
            System.out.println("4) Back to main menu");
            System.out.print("Select an option: ");

            String input = scanner.nextLine().trim();

            switch (input)
            {
                case "1":
                    pollOnceAndShow();
                    break;

                case "2":
                    alertsView.displayActiveAlerts();
                    break;

                case "3":
                    acknowledgeAlertById();
                    break;

                case "4":
                    inMenu = false;
                    break;

                default:
                    System.out.println("Please enter a number between 1 and 4.");
            }
        }
    }

    private void pollOnceAndShow()
    {
        // One full monitoring cycle: vitals + alert generation + model.notifyObservers()
        monitoringController.pollAndUpdateOnce();
        alertsView.displayActiveAlerts();
    }

    private void acknowledgeAlertById()
    {
        System.out.println();
        System.out.print("Enter the Alert ID to acknowledge (or press ENTER to cancel): ");
        String alertId = scanner.nextLine().trim();

        if (alertId.isEmpty())
        {
            System.out.println("Cancelled.");
            return;
        }

        alertService.acknowledgeAlert(alertId);

        System.out.println("Alert " + alertId + " acknowledged (if it existed).");
    }
}
