package monitoring;

import domain.MonitoredPatientData;

import java.util.List;
import java.util.Optional;

public interface MonitoredPatientDataRepository
{
    public Optional<MonitoredPatientData> findByPatientId(String id);

    public List<MonitoredPatientData> findAll();

    public void save(MonitoredPatientData monitoredPatientData);

    public void delete(String patientId);
}
