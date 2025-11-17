package patient;

import domain.Patient;
import java.util.List;
import java.util.Optional;

public interface PatientRepository
{
    public Optional<Patient> findById(String id);

    public List<Patient> findAll();

    public void save(Patient patient);

    public void delete(String id);
}
