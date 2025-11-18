package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MonitoredPatientData
{
    private Patient patient;
    private VitalSigns latestVitals;
    private List<VitalSigns> history;
    private PatientStatus status;

    public MonitoredPatientData(Patient patient)
    {
        this.patient = patient;
        this.history = new ArrayList<>();
        this.status = PatientStatus.NORMAL;
    }

    public MonitoredPatientData(Patient patient, VitalSigns latestVitals, List<VitalSigns> history, PatientStatus status)
    {
        this.patient = patient;
        this.latestVitals = latestVitals;
        this.history = history != null ? history : new ArrayList<>();
        this.status = status;
    }

    public Patient getPatient()
    {
        return patient;
    }

    public void setPatient(Patient patient)
    {
        this.patient = patient;
    }

    public VitalSigns getLatestVitals()
    {
        return latestVitals;
    }

    public void setLatestVitals(VitalSigns latestVitals)
    {
        this.latestVitals = latestVitals;
    }

    public List<VitalSigns> getHistory()
    {
        return history;
    }

    public void setHistory(List<VitalSigns> history)
    {
        this.history = history;
    }

    public PatientStatus getStatus()
    {
        return status;
    }

    public void setStatus(PatientStatus status)
    {
        this.status = status;
    }

    public void addToHistory(VitalSigns vitals)
    {
        history.add(vitals);
        latestVitals = vitals;
    }

    @Override
    public String toString() {
        return "MonitoredPatientData{" +
                "patient=" + (patient != null ? patient : "null") +
                ", latestVitals=" + (latestVitals != null ? latestVitals : "null") +
                ", historySize=" + (history != null ? history.size() : 0) +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;

        if (!(o instanceof MonitoredPatientData))
            return false;

        MonitoredPatientData monitoredPatientData = (MonitoredPatientData) o;
        return Objects.equals(patient, monitoredPatientData.patient);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(patient.getId());
    }
}
