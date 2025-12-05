package view;

import domain.Alert;
import domain.MonitoredPatientData;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AlertsView implements Observer
{
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private List<Alert> latestAlerts = new ArrayList<>();

    @Override
    public synchronized void update(List<MonitoredPatientData> patients, List<Alert> alerts)
    {
        latestAlerts = alerts != null ? new ArrayList<>(alerts) : new ArrayList<>();
    }

    public synchronized void displayActiveAlerts()
    {
        List<Alert> activeAlerts = getActiveAlertsSnapshot();

        System.out.println();
        System.out.println("Active Alerts");
        System.out.println("-------------");

        if (activeAlerts.isEmpty())
        {
            System.out.println("No active alerts.");
            return;
        }

        System.out.printf("%-4s %-25s %-20s %-10s %-50s%n", "#", "Patient", "Time", "Status", "Message");
        System.out.println("--------------------------------------------------------------------------------------------------------");

        int index = 1;

        for (Alert alert : activeAlerts)
        {
            String patientName = alert.getPatient() != null ? alert.getPatient().getFullName() : "N/A";
            String time = alert.getTimeStamp() != null ? alert.getTimeStamp().format(TIME_FORMAT) : "N/A";
            String status = alert.getPatientStatus() != null ? alert.getPatientStatus().toString() : "UNKNOWN";
            String message = alert.getMessage() != null ? alert.getMessage() : "";

            System.out.printf("%-4d %-25s %-20s %-10s %-50s%n", index, patientName, time, status, message);
            index++;
        }
    }

    public synchronized List<Alert> getActiveAlertsSnapshot()
    {
        List<Alert> active = new ArrayList<>();

        for (Alert alert : latestAlerts)
        {
            if (alert.isAcknowledged())
                continue;

            active.add(alert);
        }

        return active;
    }
}
