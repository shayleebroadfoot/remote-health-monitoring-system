package domain;

import java.time.LocalDateTime;

import java.util.Objects;

public class VitalSigns
{
    private String deviceId;
    private LocalDateTime timeStamp;
    private int heartRate;
    private double temperature;
    private int spo2;
    private boolean ecgIrregular;

    public VitalSigns() {}

    public VitalSigns(String deviceId, LocalDateTime timeStamp, int heartRate, double temperature, int spo2, boolean ecgIrregular)
    {
        this.deviceId = deviceId;
        this.timeStamp = timeStamp;
        this.heartRate = heartRate;
        this.temperature = temperature;
        this.spo2 = spo2;
        this.ecgIrregular = ecgIrregular;
    }

    public String getDeviceId()
    {
        return deviceId;
    }

    public void setDeviceId(String patientId)
    {
        this.deviceId = patientId;
    }

    public LocalDateTime getTimeStamp()
    {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp)
    {
        this.timeStamp = timeStamp;
    }

    public int getHeartRate()
    {
        return heartRate;
    }

    public void setHeartRate(int heartRate)
    {
        this.heartRate = heartRate;
    }

    public double getTemperature()
    {
        return temperature;
    }

    public void setTemperature(double temperature)
    {
        this.temperature = temperature;
    }

    public int getSpo2()
    {
        return spo2;
    }

    public void setSpo2(int spo2)
    {
        this.spo2 = spo2;
    }

    public boolean isEcgIrregular()
    {
        return ecgIrregular;
    }

    public void setEcgIrregular(boolean ecgIrregular)
    {
        this.ecgIrregular = ecgIrregular;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;

        if (!(o instanceof VitalSigns))
            return false;

        VitalSigns vitalSigns = (VitalSigns) o;

        return Objects.equals(deviceId, vitalSigns.deviceId) && Objects.equals(timeStamp, vitalSigns.timeStamp);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(deviceId, timeStamp);
    }
}