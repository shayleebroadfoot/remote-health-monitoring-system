package monitoring;

import domain.PatientStatus;
import domain.VitalSigns;

public class BasicThresholdEvaluator implements ConditionEvaluator
{
    private int[] warningHrThresholds;
    private int[] criticalHrThresholds;
    private double[] warningTemperatureThresholds;
    private double[] criticalTemperatureThresholds;
    private int spo2WarningThreshold;
    private int spo2CriticalThreshold;
    private String message;

    public BasicThresholdEvaluator()
    {
        this.warningHrThresholds = new int[]{50, 190};
        this.criticalHrThresholds = new int[]{40, 210};
        this.warningTemperatureThresholds = new double[]{35.5, 39.0};
        this.criticalTemperatureThresholds = new double[]{34.5, 40.5};
        this.spo2WarningThreshold = 92;
        this.spo2CriticalThreshold = 88;
        this.message = "";
    }

    public int[] getWarningHrThresholds()
    {
        return warningHrThresholds;
    }

    public void setWarningHrThresholds(int[] warningHrThresholds)
    {
        this.warningHrThresholds = warningHrThresholds;
    }

    public int[] getCriticalHrThresholds()
    {
        return criticalHrThresholds;
    }

    public void setCriticalHrThresholds(int[] criticalHrThresholds)
    {
        this.criticalHrThresholds = criticalHrThresholds;
    }

    public double[] getWarningTemperatureThresholds()
    {
        return warningTemperatureThresholds;
    }

    public void setWarningTemperatureThresholds(double[] warningTemperatureThresholds)
    {
        this.warningTemperatureThresholds = warningTemperatureThresholds;
    }

    public double[] getCriticalTemperatureThresholds()
    {
        return criticalTemperatureThresholds;
    }

    public void setCriticalTemperatureThresholds(double[] criticalTemperatureThresholds)
    {
        this.criticalTemperatureThresholds = criticalTemperatureThresholds;
    }

    public int getSpo2WarningThreshold()
    {
        return spo2WarningThreshold;
    }

    public void setSpo2WarningThreshold(int spo2WarningThreshold)
    {
        this.spo2WarningThreshold = spo2WarningThreshold;
    }

    public int getSpo2CriticalThreshold()
    {
        return spo2CriticalThreshold;
    }

    public void setSpo2CriticalThreshold(int spo2CriticalThreshold)
    {
        this.spo2CriticalThreshold = spo2CriticalThreshold;
    }

    public PatientStatus evaluateHeartRate(int heartRate)
    {
        if (heartRate < criticalHrThresholds[0] || heartRate > criticalHrThresholds[1])
        {
            message = message.isEmpty() ? "heart rate critical" : message + ", heart rate critical";
            return PatientStatus.CRITICAL;
        }

        else if (heartRate < warningHrThresholds[0] || heartRate > warningHrThresholds[1])
        {
            message = message.isEmpty() ? "heart rate warning" : message + ", heart rate warning";
            return PatientStatus.WARNING;
        }

        else
            return PatientStatus.NORMAL;
    }

    public PatientStatus evaluateTemperature(double temperature)
    {
        if (temperature < criticalTemperatureThresholds[0] || temperature > criticalTemperatureThresholds[1])
        {
            message = message.isEmpty() ? "temperature critical" : message + ", temperature critical";
            return PatientStatus.CRITICAL;
        }

        else if (temperature < warningTemperatureThresholds[0] || temperature > warningTemperatureThresholds[1])
        {
            message = message.isEmpty() ? "temperature warning" : message + ", temperature warning";
            return PatientStatus.WARNING;
        }

        else
            return PatientStatus.NORMAL;
    }

    public PatientStatus evaluateSpO2(int spo2)
    {
        if  (spo2 < spo2CriticalThreshold)
        {
            message = message.isEmpty() ? "SpO2 critical" : message + ", SpO2 critical";
            return PatientStatus.CRITICAL;
        }
        else if (spo2 < spo2WarningThreshold)
        {
            message = message.isEmpty() ? "SpO2 critical" : message + ", SpO2 critical";
            return PatientStatus.WARNING;
        }

        else
            return PatientStatus.NORMAL;
    }

    @Override
    public PatientStatus evaluate(VitalSigns vitals)
    {
        int hr = vitals.getHeartRate();
        double temp = vitals.getTemperature();
        int spo2 = vitals.getSpo2();
        boolean ecg = vitals.isEcgIrregular();

        if (ecg || evaluateHeartRate(hr) ==  PatientStatus.CRITICAL || evaluateTemperature(temp) ==  PatientStatus.CRITICAL || evaluateSpO2(spo2) == PatientStatus.CRITICAL)
            return PatientStatus.CRITICAL;
        else if (evaluateHeartRate(hr) ==  PatientStatus.WARNING || evaluateTemperature(temp) ==  PatientStatus.WARNING || evaluateSpO2(spo2) == PatientStatus.WARNING)
            return PatientStatus.WARNING;
        else
            return PatientStatus.NORMAL;
    }

    public String getMessage()
    {
        return message;
    }
}
