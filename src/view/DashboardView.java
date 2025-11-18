package view;

import domain.Alert;
import domain.MonitoredPatientData;

import javax.swing.text.View;
import java.util.List;

public class DashboardView implements Observer
{
    @Override
    public void update(List<MonitoredPatientData> patients, List<Alert> alerts)
    {

    }
}
