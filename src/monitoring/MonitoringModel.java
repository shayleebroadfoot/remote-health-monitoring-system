package monitoring;

import domain.Alert;
import domain.MonitoredPatientData;
import view.Observer;

import java.util.ArrayList;
import java.util.List;

public class MonitoringModel implements Subject
{
    private final List<Observer> observers;
    private List<MonitoredPatientData> monitoredPatientData;
    private List<Alert> activeAlerts;

    public MonitoringModel()
    {
        this.observers = new ArrayList<>();
        this.monitoredPatientData = new ArrayList<>();
        this.activeAlerts = new ArrayList<>();
    }

    @Override
    public void addObserver(Observer observer)
    {
        if (observer != null && !observers.contains(observer))
            observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer)
    {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers()
    {
        for (Observer observer : observers)
        {
            observer.update(monitoredPatientData, activeAlerts);
        }
    }

    public List<MonitoredPatientData> getMonitoredPatientData()
    {
        return new ArrayList<>(monitoredPatientData);
    }

    public void setMonitoredPatientData(List<MonitoredPatientData> monitoredPatientData)
    {
        if (monitoredPatientData == null)
            this.monitoredPatientData = new ArrayList<>();

        else
            this.monitoredPatientData = new ArrayList<>(monitoredPatientData);
    }

    public List<Alert> getActiveAlerts()
    {
        return activeAlerts;
    }

    public void setActiveAlerts(List<Alert> alerts)
    {
        if (alerts == null)
            this.activeAlerts = new ArrayList<>();

        else
            this.activeAlerts = new ArrayList<>(alerts);
    }
}

