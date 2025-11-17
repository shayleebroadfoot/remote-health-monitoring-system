package view;

import controller.MonitoringController;
import domain.Alert;

import java.util.List;
import java.util.Scanner;

public class MonitoringDashboardView
{
    private final MonitoringController controller;
    private final Scanner scanner;

    public MonitoringDashboardView(MonitoringController controller)
    {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public void show()
    {
        boolean running = true;

        while (running)
        {
            System.out.println("=== Monitoring Dashboard ===");
            System.out.println("[1] Refresh monitoring data");
            System.out.println("[2] Show active alerts");
            System.out.println("[3] Acknowledge alert");
            System.out.println("[Q] Back to main menu");
            System.out.print("Choice: ");

            String choice = scanner.nextLine().trim().toUpperCase();

            switch (choice)
            {
                case "1":
                    refreshMonitoring();
                    break;
                case "2":
                    showActiveAlerts();
                    break;
                case "3":
                    acknowledgeAlert();
                    break;
                case "Q":
                    running = false;
                    break;
                default:
                    showErrorMessage("Unknown choice.");
                    break;
            }
        }
    }

    private void refreshMonitoring()
    {
        try
        {
            controller.refreshMonitoring();
            System.out.println("Monitoring data refreshed.");
        }
        catch (Exception e)
        {
            showErrorMessage("Failed to refresh monitoring data: " + e.getMessage());
        }
    }

    private void showActiveAlerts()
    {
        try
        {
            List<Alert> alerts = controller.getActiveAlerts();

            if (alerts == null || alerts.isEmpty())
            {
                System.out.println("No active alerts.");
                return;
            }

            System.out.println("=== Active Alerts ===");
            for (Alert alert : alerts)
            {
                System.out.println(formatAlert(alert));
            }
        }
        catch (Exception e)
        {
            showErrorMessage("Failed to load alerts: " + e.getMessage());
        }
    }

    private void acknowledgeAlert()
    {
        System.out.print("Enter alert ID to acknowledge: ");
        String id = scanner.nextLine().trim();

        if (id.isEmpty())
        {
            showErrorMessage("Alert ID is required.");
            return;
        }

        try
        {
            controller.acknowledgeAlert(id);
            System.out.println("Alert acknowledged.");
        }
        catch (Exception e)
        {
            showErrorMessage("Could not acknowledge alert: " + e.getMessage());
        }
    }

    private String formatAlert(Alert alert)
    {
        // Adjust this to match your Alert class fields.
        // For now, rely on toString() so it compiles without extra changes.
        return alert.toString();
    }

    private void showErrorMessage(String message)
    {
        System.out.println("[ERROR] " + message);
    }
}
