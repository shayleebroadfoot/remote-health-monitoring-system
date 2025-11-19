package view;

import domain.Alert;
import domain.MonitoredPatientData;
import domain.Patient;
import domain.VitalSigns;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class MonitoringDashboardView implements Observer
{
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void update(List<MonitoredPatientData> monitoredPatientData, List<Alert> alerts)
    {
        System.out.println();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Remote Patient Monitoring Dashboard ~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println();

        printMonitoredPatients(monitoredPatientData);
        System.out.println();
        printActiveAlerts(alerts);

        System.out.println();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ End of Dashboard ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println();
    }

    private void printMonitoredPatients(List<MonitoredPatientData> monitoredPatientData)
    {
        System.out.println("Monitored Patients");
        System.out.println("------------------");

        if (monitoredPatientData == null || monitoredPatientData.isEmpty())
        {
            System.out.println("No patients are currently being monitored.");
            return;
        }

        System.out.printf("%-25s %-20s %-10s %-10s %-8s %-15s %-10s%n", "Patient", "Last Reading", "HR", "Temp", "SpO2", "ECG Irregular", "Status");
        System.out.println("--------------------------------------------------------------------------------------------------------");

        for (MonitoredPatientData data : monitoredPatientData)
        {
            Patient patient = data.getPatient();
            VitalSigns v = data.getLatestVitals();

            String patientName = (patient != null) ? patient.getFullName() : "N/A";
            String timestamp = "N/A";
            String heartRate = "N/A";
            String temp = "N/A";
            String spo2 = "N/A";
            String irregular = "N/A";

            if (v != null)
            {
                if (v.getTimeStamp() != null)
                    timestamp = v.getTimeStamp().format(TIME_FORMAT);

                heartRate = String.valueOf(v.getHeartRate());
                temp = String.format("%.1f", v.getTemperature());
                spo2 = String.valueOf(v.getSpo2());
                irregular = String.valueOf(v.isEcgIrregular());
            }

            String status = (data.getStatus() != null) ? data.getStatus().toString() : "UNKNOWN";

            System.out.printf("%-25s %-20s %-10s %-10s %-8s %-15s %-10s%n", patientName, timestamp, heartRate, temp, spo2, irregular, status);
        }
    }

    private void printActiveAlerts(List<Alert> alerts)
    {
        System.out.println("Active Alerts");
        System.out.println("-------------");

        if (alerts == null || alerts.isEmpty())
        {
            System.out.println("No active alerts.");
            return;
        }

        System.out.printf("%-25s %-20s %-10s %-50s%n", "Patient", "Time", "Status", "Message");
        System.out.println("--------------------------------------------------------------------------------------------------------");

        for (Alert alert : alerts)
        {
            String patientName = (alert.getPatient() != null) ? alert.getPatient().getFullName() : "N/A";

            String time = (alert.getTimeStamp() != null) ? alert.getTimeStamp().format(TIME_FORMAT) : "N/A";

            String status = (alert.getPatientStatus() != null) ? alert.getPatientStatus().toString() : "UNKNOWN";

            String message = alert.getMessage() != null ? alert.getMessage() : "";

            System.out.printf("%-25s %-20s %-10s %-50s%n", patientName, time, status, message);
        }
    }
}
