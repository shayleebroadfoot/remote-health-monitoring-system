package monitoring;

import domain.MonitoredPatientData;

import java.util.*;

public class InMemoryMonitoredPatientDataRepository implements MonitoredPatientDataRepository
{
    private final Map<String, MonitoredPatientData> monitoredPatientData;

    public InMemoryMonitoredPatientDataRepository()
    {
        this.monitoredPatientData = new HashMap<>();
    }

    @Override
    public Optional<MonitoredPatientData> findByPatientId(String id)
    {
        return Optional.ofNullable(monitoredPatientData.get(id));
    }

    @Override
    public List<MonitoredPatientData> findAll()
    {
        return new ArrayList<>(monitoredPatientData.values());
    }

    @Override
    public void save(MonitoredPatientData monitoredPatientData)
    {
        this.monitoredPatientData.put(monitoredPatientData.getPatient().getId(), monitoredPatientData);
    }

    @Override
    public void delete(String patientId)
    {
        monitoredPatientData.remove(patientId);
    }
}
