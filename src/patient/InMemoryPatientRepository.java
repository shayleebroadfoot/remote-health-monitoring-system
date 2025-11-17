package patient;

import domain.Patient;
import java.util.*;

public class InMemoryPatientRepository implements PatientRepository
{
    private final Map<String, Patient> patients;

    public InMemoryPatientRepository()
    {
        this.patients = new HashMap<>();
    }

    @Override
    public Optional<Patient> findById(String id)
    {
        return Optional.ofNullable(patients.get(id));
    }

    @Override
    public List<Patient> findAll()
    {
        return new ArrayList<>(patients.values());
    }

    @Override
    public void save(Patient patient)
    {
        if (patient.getId() == null)
            patient.setId(UUID.randomUUID().toString());

        patients.put(patient.getId(), patient);
    }

    @Override
    public void delete(String id)
    {
        patients.remove(id);
    }
}
