package view;

import domain.Alert;
import domain.MonitoredPatientData;
import domain.Patient;
import domain.VitalSigns;
import domain.PatientStatus;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class PatientDetailView implements Observer
{
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final String patientId;

    public PatientDetailView(String patientId)
    {
        this.patientId = patientId;
    }

    @Override
    public void update(List<MonitoredPatientData> monitoredPatientData, List<Alert> alerts)
    {
        if (patientId == null)
        {
            System.out.println("PatientDetailView has no patientId configured.");
            return;
        }

        MonitoredPatientData data = findPatientData(monitoredPatientData);
        List<Alert> patientAlerts = findPatientAlerts(alerts);

        System.out.println();
        System.out.println("================================ Patient Detail ================================");

        if (data == null)
        {
            System.out.println("No monitoring data found for patient ID: " + patientId);
            System.out.println("===============================================================================\n");
            return;
        }

        printPatientInfo(data);
        System.out.println();
        printLatestVitals(data);
        System.out.println();
        printVitalsHistory(data);
        System.out.println();
        printPatientAlerts(patientAlerts);

        System.out.println("===============================================================================\n");
    }

    private MonitoredPatientData findPatientData(List<MonitoredPatientData> monitoredPatientData)
    {
        if (monitoredPatientData == null)
            return null;

        for (MonitoredPatientData data : monitoredPatientData)
        {
            Patient p = data.getPatient();

            if (p != null && patientId.equals(p.getId()))
                return data;
        }

        return null;
    }

    private List<Alert> findPatientAlerts(List<Alert> alerts)
    {
        // We don't need a new list type here; just filter on the fly when printing.
        return alerts;
    }

    private void printPatientInfo(MonitoredPatientData data)
    {
        Patient p = data.getPatient();

        if (p == null)
        {
            System.out.println("Patient: N/A (no patient object)");
            return;
        }

        System.out.println("Patient Information");
        System.out.println("-------------------");
        System.out.println("ID:        " + p.getId());
        System.out.println("Name:      " + p.getFullName());
        System.out.println("Birthdate: " + p.getBirthDate());
        System.out.println("Address:   " + p.getAddress());
        System.out.println("Phone:     " + p.getPhone());
        System.out.println("Emergency Contact: " + p.getEmergencyContactName() + " (" + p.getEmergencyContactPhone() + ")");
        PatientStatus status = data.getStatus();
        System.out.println("Current Status: " + (status != null ? status : PatientStatus.NORMAL));
    }

    private void printLatestVitals(MonitoredPatientData data)
    {
        System.out.println("Latest Vitals");
        System.out.println("-------------");

        VitalSigns v = data.getLatestVitals();

        if (v == null)
        {
            System.out.println("No vitals recorded yet.");
            return;
        }

        String time = v.getTimeStamp() != null ? v.getTimeStamp().format(TIME_FORMAT) : "N/A";

        System.out.println("Time:           " + time);
        System.out.println("Device ID:      " + v.getDeviceId());
        System.out.println("Heart Rate:     " + v.getHeartRate());
        System.out.println("Temperature:    " + String.format("%.1f", v.getTemperature()));
        System.out.println("SpO2:           " + v.getSpo2());
        System.out.println("ECG Irregular:  " + v.isEcgIrregular());
    }

    private void printVitalsHistory(MonitoredPatientData data)
    {
        System.out.println("Vitals History (previous readings)");
        System.out.println("----------------------------------");

        List<VitalSigns> history = data.getHistory();

        if (history == null || history.isEmpty())
        {
            System.out.println("No previous readings.");
            return;
        }

        System.out.printf("%-20s %-10s %-10s %-8s %-12s%n", "Time", "HeartRate", "Temp", "SpO2", "ECG Irregular");

        for (VitalSigns v : history)
        {
            String time = v.getTimeStamp() != null ? v.getTimeStamp().format(TIME_FORMAT) : "N/A";

            System.out.printf("%-20s %-10d %-10.1f %-8d %-12b%n", time, v.getHeartRate(), v.getTemperature(), v.getSpo2(), v.isEcgIrregular());
        }
    }

    private void printPatientAlerts(List<Alert> alerts)
    {
        System.out.println("Alerts for Patient");
        System.out.println("------------------");

        if (alerts == null || alerts.isEmpty())
        {
            System.out.println("No alerts.");
            return;
        }

        boolean any = false;

        for (Alert alert : alerts)
        {
            if (alert.getPatient() == null || alert.getPatient().getId() == null)
                continue;

            if (!patientId.equals(alert.getPatient().getId()))
                continue;

            any = true;

            String time = alert.getTimeStamp() != null ? alert.getTimeStamp().format(TIME_FORMAT) : "N/A";
            String status = alert.getPatientStatus() != null ? alert.getPatientStatus().toString() : "UNKNOWN";
            String message = alert.getMessage() != null ? alert.getMessage() : "";

            System.out.println("Time:   " + time);
            System.out.println("Status: " + status);
            System.out.println("Msg:    " + message);
            System.out.println();
        }

        if (!any)
            System.out.println("No alerts for this patient.");
    }
}
