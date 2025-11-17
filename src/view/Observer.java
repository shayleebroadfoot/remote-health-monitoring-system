package view;

import domain.Alert;
import domain.MonitoredPatientData;

import java.util.List;

public interface Observer
{
    public void update(List<MonitoredPatientData> patients, List<Alert> alerts);
}
