package alert;

import domain.Alert;

import java.util.List;
import java.util.Optional;

public class AlertService
{
    private final AlertRepository alertRepository;

    public AlertService(AlertRepository alertRepository)
    {
        this.alertRepository = alertRepository;
    }

    public void raiseAlert(Alert alert)
    {
        alertRepository.save(alert);
    }

    public Optional<Alert> getAlertById(String alertId)
    {
        return alertRepository.findById(alertId);
    }

    public List<Alert> getActiveAlerts()
    {
        return alertRepository.findActive();
    }

    public List<Alert> getAlertsForPatient(String patientId)
    {
        return alertRepository.findByPatient(patientId);
    }

    public void acknowledgeAlert(String alertId)
    {
        Optional<Alert> existing = alertRepository.findById(alertId);

        if (existing.isPresent())
        {
            Alert alert = existing.get();
            alert.setAcknowledged(true);
            alertRepository.save(alert);
        }
    }
}
