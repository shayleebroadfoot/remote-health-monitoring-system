package alert;

import domain.Alert;
import domain.Patient;

import java.util.*;

public class InMemoryAlertRepository implements AlertRepository
{
    private final Map<String, Alert> alerts = new HashMap<>();

    @Override
    public Optional<Alert> findById(String id)
    {
        return Optional.ofNullable(alerts.get(id));
    }

    @Override
    public List<Alert> findActive()
    {
        List<Alert> results = new ArrayList<>();

        for (Alert alert : alerts.values())
        {
            if (!alert.isAcknowledged())
                results.add(alert);
        }

        return results;
    }

    @Override
    public List<Alert> findByPatient(String patientId)
    {
        List<Alert> results = new ArrayList<>();

        for (Alert alert : alerts.values())
        {
            Patient patient = alert.getPatient();

            if (patient != null && patientId.equals(patient.getId()))
                results.add(alert);
        }

        return results;
    }

    @Override
    public void save(Alert alert)
    {
        if (alert.getId() == null)
            alert.setId(UUID.randomUUID().toString());

        alerts.put(alert.getId(), alert);
    }

    @Override
    public void delete(String id)
    {
        alerts.remove(id);
    }
}