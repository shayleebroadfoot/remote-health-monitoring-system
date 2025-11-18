package patient;

import domain.Patient;

import java.util.List;
import java.util.Optional;

public class PatientService
{
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository)
    {
        this.patientRepository = patientRepository;
    }

    public void createPatient(Patient patient)
    {
        patientRepository.save(patient);
    }

    public void updatePatient(Patient patient)
    {
        patientRepository.save(patient);
    }

    public void deletePatient(String id)
    {
        patientRepository.delete(id);
    }

    public Optional<Patient> getPatientById(String id)
    {
        return patientRepository.findById(id);
    }

    public List<Patient> getAllPatients()
    {
        return patientRepository.findAll();
    }
}
