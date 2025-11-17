package view;

import controller.MonitoringController;
import domain.Alert;
import domain.MonitoredPatientData;

import java.util.List;
import java.util.Scanner;

public class AlertsView implements Observer
{
    private final MonitoringController controller;
    private final Scanner scanner;
    private List<Alert> lastAlerts;

    public AlertsView(MonitoringController controller)
    {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void update(List<MonitoredPatientData> patients, List<Alert> alerts)
    {
        // store snapshot for menu actions
        this.lastAlerts = alerts;
    }

    /**
     * Console menu for viewing + acknowledging alerts.
     */
    public void showMenu()
    {
        boolean running = true;

        while (running)
        {
            System.out.println("=== Active Alerts ===");

            if (lastAlerts == null || lastAlerts.isEmpty())
                System.out.println("(no active alerts)");

            else
            {
                for (Alert alert : lastAlerts)
                {
                    System.out.println(alert.getId() + " | "
                            + alert.getPatient().getFullName() + " | "
                            + alert.getMessage() + " | "
                            + alert.getPatientStatus());
                }
            }

            System.out.println();
            System.out.println("Options:");
            System.out.println("1) Acknowledge alert");
            System.out.println("0) Back");
            System.out.print("Choice: ");

            String choice = scanner.nextLine();

            switch (choice)
            {
                case "1":
                    handleAcknowledge();
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Unknown option.");
            }

            System.out.println();
        }
    }

    private void handleAcknowledge()
    {
        if (lastAlerts == null || lastAlerts.isEmpty())
        {
            System.out.println("No alerts to acknowledge.");
            return;
        }

        System.out.print("Enter alert id to acknowledge: ");
        String alertId = scanner.nextLine();

        controller.acknowledgeAlert(alertId);

        System.out.println("Alert acknowledged.");
    }
}

