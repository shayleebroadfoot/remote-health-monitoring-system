package domain;

import java.time.LocalDate;
import java.util.Objects;

public class Patient
{
    private String id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String address;
    private String phone;
    private String emergencyContactName;
    private String emergencyContactPhone;

    public Patient()
    {
        this.id = null;
        this.firstName = "";
        this.lastName = "";
        this.birthDate = null;
        this.address = "";
        this.phone = "";
        this.emergencyContactName = "";
        this.emergencyContactPhone = "";
    }

    public Patient(String firstName, String lastName, LocalDate birthDate, String address, String phone)
    {
        this.id = null;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
        this.phone = phone;
        this.emergencyContactName = "";
        this.emergencyContactPhone = "";
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate()
    {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate)
    {
        this.birthDate = birthDate;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhoneNumber(String phone)
    {
        this.phone = phone;
    }

    public String getEmergencyContactName()
    {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName)
    {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmergencyContactPhone()
    {
        return emergencyContactPhone;
    }

    public void setEmergencyContactPhone(String emergencyContactPhone)
    {
        this.emergencyContactPhone = emergencyContactPhone;
    }

    public String getFullName()
    {
        return lastName + ", " + firstName;
    }

    @Override
    public String toString()
    {
        return "Patient{ " + "id= " + id + ", " + "name= " + getFullName() + ", " + "dob= " + birthDate;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;

        if (!(o instanceof Patient))
            return false;

        Patient patient = (Patient) o;

        return Objects.equals(id, patient.getId());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }
}