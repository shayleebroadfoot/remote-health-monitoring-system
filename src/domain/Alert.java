package domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Alert
{
    private String id;
    private Patient patient;
    private LocalDateTime timeStamp;
    private PatientStatus patientStatus;
    private String message;
    private boolean acknowledged;

    public Alert(Patient patient, PatientStatus patientStatus, String message)
    {
        this.id = null;
        this.patient = patient;
        this.timeStamp = LocalDateTime.now();
        this.patientStatus = patientStatus;
        this.message = message;
        this.acknowledged = false;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public Patient getPatient()
    {
        return patient;
    }

    public void setPatient(Patient patient)
    {
        this.patient = patient;
    }

    public LocalDateTime getTimeStamp()
    {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp)
    {
        this.timeStamp = timeStamp;
    }

    public PatientStatus getPatientStatus()
    {
        return patientStatus;
    }

    public void setPatientStatus(PatientStatus patientStatus)
    {
        this.patientStatus = patientStatus;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public boolean isAcknowledged()
    {
        return acknowledged;
    }

    public void setAcknowledged(boolean acknowledged)
    {
        this.acknowledged = acknowledged;
    }

    @Override
    public String toString()
    {
        return "Alert{" +
                "id='" + id + '\'' +
                ", patient=" + (patient != null ? patient.getId() : "null") +
                ", timeStamp=" + timeStamp +
                ", severity=" + patientStatus +
                ", message='" + message + '\'' +
                ", acknowledged=" + acknowledged +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;

        if (!(o instanceof Alert))
            return false;

        Alert alert = (Alert) o;
        return Objects.equals(id, alert.id);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }
}
