package monitoring;

import domain.Patient;
import domain.PatientStatus;
import domain.VitalSigns;

public class ElderlyPatientEvaluator implements ConditionEvaluator
{
    private int[] warningHrThresholds;
    private int[] criticalHrThresholds;
    private double[] warningTemperatureThresholds;
    private double[] criticalTemperatureThresholds;
    private int spo2WarningThreshold;
    private int spo2CriticalThreshold;

    public ElderlyPatientEvaluator()
    {
        this.warningHrThresholds = new int[]{55, 170};
        this.criticalHrThresholds = new int[]{50, 185};
        this.warningTemperatureThresholds = new double[]{36.0, 38.5};
        this.criticalTemperatureThresholds = new double[]{35.0, 40.0};
        this.spo2WarningThreshold = 92;
        this.spo2CriticalThreshold = 88;
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
            return PatientStatus.CRITICAL;
        else if (heartRate < warningHrThresholds[0] || heartRate > warningHrThresholds[1])
            return PatientStatus.WARNING;
        else
            return PatientStatus.NORMAL;
    }

    public PatientStatus evaluateTemperature(double temperature)
    {
        if (temperature < criticalTemperatureThresholds[0] || temperature > criticalTemperatureThresholds[1])
            return PatientStatus.CRITICAL;
        else if (temperature < warningTemperatureThresholds[0] || temperature > warningTemperatureThresholds[1])
            return PatientStatus.WARNING;
        else
            return PatientStatus.NORMAL;
    }

    public PatientStatus evaluateSpO2(int spo2)
    {
        if  (spo2 < spo2CriticalThreshold)
            return PatientStatus.CRITICAL;
        else if (spo2 < spo2WarningThreshold)
            return PatientStatus.WARNING;
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
}