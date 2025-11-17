package controller;

import alert.AlertService;
import domain.Alert;
import monitoring.MonitoringService;

import java.util.List;

public class MonitoringController
{
    private final MonitoringService monitoringService;
    private final AlertService alertService;
    // maybe also a reference to AlertsView if you want controller-driven calls

    public MonitoringController(MonitoringService monitoringService, AlertService alertService)
    {
        this.monitoringService = monitoringService;
        this.alertService = alertService;
    }

    public void refreshMonitoring()
    {
        monitoringService.pollOnce();
    }

    public void acknowledgeAlert(String alertId)
    {
        alertService.acknowledgeAlert(alertId);
    }

    public List<Alert> getActiveAlerts()
    {
        return alertService.getActiveAlerts();
    }
}

