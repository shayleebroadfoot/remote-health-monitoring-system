package alert;

import domain.Alert;

import java.util.List;
import java.util.Optional;

public interface AlertRepository
{
    public Optional<Alert> findById(String id);

    public List<Alert> findActive();

    public List<Alert> findByPatient(String patientId);

    public void save(Alert alert);

    public void delete(String alertId);
}