import java.util.*;

public class PatientRepository {
    private Map<String, PatientData> patients = new HashMap<>();

    public void addPatient(PatientData patient) {
        patients.put(patient.getId(), patient);
    }

    public PatientData getPatient(String id) {
        return patients.get(id);
    }

    public Collection<PatientData> getAllPatients() {
        return patients.values();
    }
}
