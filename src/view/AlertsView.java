//package view;
//
//import domain.Alert;
//import domain.MonitoredPatientData;
//
//import java.util.List;
//
///* This can be a view with just a list of alerts. There should be a selection to "acknowledge" them (set boolean variable to true in
//*  the Alerts class). Then maybe have a menu to see all alerts, just unacknowledged (active) alerts, and maybe just unacknowledged
//* (inactive) alerts???
//*
//* No controller for it yet. AppController in app package ties all controllers / views together
//*/
//public class AlertsView implements Observer
//{
//    @Override
//    public void update(List<MonitoredPatientData> patients, List<Alert> alerts)
//    {
//
//    }
//}
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
        System.out.println();
        System.out.println("Active Alerts");
        System.out.println("-------------");

        if (latestAlerts.isEmpty())
        {
            System.out.println("No alerts.");
            return;
        }

        boolean any = false;

        System.out.printf("%-15s %-25s %-20s %-10s %-50s%n", "Alert ID", "Patient", "Time", "Status", "Message");
        System.out.println("--------------------------------------------------------------------------------------------------------");

        for (Alert alert : latestAlerts)
        {
            if (alert.isAcknowledged())
                continue;

            any = true;

            String id = alert.getId();
            String patientName = alert.getPatient() != null ? alert.getPatient().getFullName() : "N/A";
            String time = alert.getTimeStamp() != null ? alert.getTimeStamp().format(TIME_FORMAT) : "N/A";
            String status = alert.getPatientStatus() != null ? alert.getPatientStatus().toString() : "UNKNOWN";
            String message = alert.getMessage() != null ? alert.getMessage() : "";

            System.out.printf("%-15s %-25s %-20s %-10s %-50s%n", id, patientName, time, status, message);
        }

        if (!any)
            System.out.println("No active (unacknowledged) alerts.");
    }
}
