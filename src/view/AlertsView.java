package view;

import domain.Alert;
import domain.MonitoredPatientData;

import java.util.List;

/* This can be a view with just a list of alerts. There should be a selection to "acknowledge" them (set boolean variable to true in
*  the Alerts class). Then maybe have a menu to see all alerts, just unacknowledged (active) alerts, and maybe just unacknowledged
* (inactive) alerts???
*
* No controller for it yet. AppController in app package ties all controllers / views together
*/
public class AlertsView implements Observer
{
    @Override
    public void update(List<MonitoredPatientData> patients, List<Alert> alerts)
    {

    }
}
