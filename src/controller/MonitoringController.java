package controller;

import monitoring.MonitoringModel;
import monitoring.MonitoringService;
import view.MonitoringDashboardView;

public class MonitoringController
{
    private final MonitoringService monitoringService;
    private final MonitoringModel monitoringModel;
    private final MonitoringDashboardView dashboardView;

    public MonitoringController(MonitoringService monitoringService, MonitoringModel monitoringModel, MonitoringDashboardView dashboardView)
    {
        this.monitoringService = monitoringService;
        this.monitoringModel = monitoringModel;
        this.dashboardView = dashboardView;

        this.monitoringModel.addObserver(dashboardView);
    }

    public void pollAndUpdateOnce()
    {
        monitoringService.pollOnce();
    }

    public void refreshDashboard()
    {
        monitoringModel.notifyObservers();
    }

    public void runContinuousMonitoring(long intervalMillis)
    {
        boolean running = true;

        while (running)
        {
            pollAndUpdateOnce();

            try
            {
                Thread.sleep(intervalMillis);
            }
            catch (InterruptedException e)
            {
                Thread.currentThread().interrupt();
                running = false;
            }
        }
    }
}
