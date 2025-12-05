package controller;

import alert.AlertService;
import domain.Alert;
import monitoring.MonitoringModel;
import view.AlertsView;

import java.util.List;
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
            System.out.println("3) Acknowledge an alert by number");
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
                    acknowledgeAlertByNumber();
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
        monitoringController.pollAndUpdateOnce();
        alertsView.displayActiveAlerts();
    }

    private void acknowledgeAlertByNumber()
    {
        List<Alert> activeAlerts = alertsView.getActiveAlertsSnapshot();

        if (activeAlerts.isEmpty())
        {
            System.out.println("No active alerts to acknowledge.");
            return;
        }

        System.out.println();
        alertsView.displayActiveAlerts();
        System.out.print("Enter the alert number to acknowledge (or press ENTER to cancel): ");

        String input = scanner.nextLine().trim();

        if (input.isEmpty())
        {
            System.out.println("Cancelled.");
            return;
        }

        int index;

        try
        {
            index = Integer.parseInt(input);
        }
        catch (NumberFormatException e)
        {
            System.out.println("Invalid number.");
            return;
        }

        if (index < 1 || index > activeAlerts.size())
        {
            System.out.println("Selection out of range.");
            return;
        }

        Alert selectedAlert = activeAlerts.get(index - 1);

        if (selectedAlert.getId() == null)
        {
            System.out.println("Selected alert has no ID and cannot be acknowledged.");
            return;
        }

        alertService.acknowledgeAlert(selectedAlert.getId());
        selectedAlert.setAcknowledged(true);

        System.out.println("Alert " + index + " acknowledged.");
    }
}
