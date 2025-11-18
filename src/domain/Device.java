package domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Device
{
    private String id;
    private String type;
    private Patient patient;
    private LocalDateTime dateAdded;
    private boolean active;

    public Device(String type, Patient patient, LocalDateTime dateAdded, boolean active)
    {
        this.id = null;
        this.type = type;
        this.patient = patient;
        this.dateAdded = dateAdded;
        this.active = active;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public Patient getPatient()
    {
        return patient;
    }

    public void setPatient(Patient patient)
    {
        this.patient = patient;
    }

    public LocalDateTime getDateAdded()
    {
        return dateAdded;
    }

    public void setDateAdded(LocalDateTime dateAdded)
    {
        this.dateAdded = dateAdded;
    }

    public boolean isActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", patient=" + (patient != null ? patient.getFullName() : "null") +
                ", dateAdded=" + dateAdded +
                ", active=" + active +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;

        if (!(o instanceof Device))
            return false;

        Device device = (Device) o;

        return Objects.equals(id, device.getId());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }
}
