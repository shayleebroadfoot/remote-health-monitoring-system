package io;

import domain.Device;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;

public class CsvTestDataGenerator
{
    public void writeTestVitalsFile(File file, List<Device> devices)
    {
        try (PrintWriter out = new PrintWriter(file))
        {
            for (Device d : devices)
            {
                LocalDateTime now = LocalDateTime.now();

                int heartRate = generateHeartRate();
                double temp = generateTemperature();
                int spo2 = generateSpo2();
                boolean ecgIrregular = Math.random() < 0.03; // ~3% chance, much less spammy

                out.printf("%s,%s,%d,%.1f,%d,%b%n",
                        d.getId(),
                        now,
                        heartRate,
                        temp,
                        spo2,
                        ecgIrregular
                );
            }

            System.out.println("Wrote test_vitals.csv with " + devices.size() + " readings.");
        }
        catch (IOException e)
        {
            throw new RuntimeException("Failed to write test_vitals.csv", e);
        }
    }

    // ---- Helpers ----

    private int randomIntInRange(int minInclusive, int maxInclusive)
    {
        return minInclusive + (int) (Math.random() * (maxInclusive - minInclusive + 1));
    }

    private double randomDoubleInRange(double minInclusive, double maxInclusive)
    {
        return minInclusive + (Math.random() * (maxInclusive - minInclusive));
    }

    /**
     * Heart rate generator designed to hit:
     * - ~3% CRITICAL (low and high),
     * - ~7% WARNING,
     * - ~90% NORMAL.
     *
     * Thresholds:
     *   critical: <50 or >185
     *   warning:  <55 or >170
     *   normal:   55–170
     */
    private int generateHeartRate()
    {
        double r = Math.random();

        if (r < 0.01)
        {
            // Far below critical
            return randomIntInRange(30, 45); // <50
        }
        else if (r < 0.02)
        {
            // High CRITICAL (just above critical upper)
            return randomIntInRange(185, 210); // >185
        }
        else if (r < 0.03)
        {
            // Beyond critical, very rare outliers
            return randomIntInRange(211, 230);
        }
        else if (r < 0.07)
        {
            // Low WARNING
            return randomIntInRange(50, 54); // 50–54
        }
        else if (r < 0.10)
        {
            // High WARNING (just above warning upper)
            return randomIntInRange(171, 184); // 171–184
        }
        else
        {
            // NORMAL (most of the time)
            return randomIntInRange(60, 160); // comfortably inside 55–170
        }
    }

    /**
     * Temperature generator based on:
     *   critical: <35.0 or >40.0
     *   warning:  <36.0 or >38.5
     *   normal:   36.0–38.5
     *
     * Target: ~90% NORMAL, ~7% WARNING, ~3% CRITICAL.
     */
    private double generateTemperature()
    {
        double r = Math.random();

        if (r < 0.015)
        {
            // Far below critical
            return randomDoubleInRange(33.0, 34.5); // <35.0
        }
        else if (r < 0.03)
        {
            // Above critical
            return randomDoubleInRange(40.1, 41.5); // >40.0
        }
        else if (r < 0.06)
        {
            // Low WARNING (35–36)
            return randomDoubleInRange(35.0, 35.9);
        }
        else if (r < 0.10)
        {
            // High WARNING (just above 38.5 but below 40)
            return randomDoubleInRange(38.6, 39.9);
        }
        else
        {
            // NORMAL
            return randomDoubleInRange(36.0, 38.5);
        }
    }

    /**
     * SpO2 generator based on:
     *   critical: <88
     *   warning:  88–91
     *   normal:   >=92
     *
     * Target: ~90% NORMAL, ~5% WARNING, ~3% CRITICAL.
     */
    private int generateSpo2()
    {
        double r = Math.random();

        if (r < 0.015)
        {
            // Far critical low
            return randomIntInRange(75, 84); // way below 88
        }
        else if (r < 0.03)
        {
            // Near critical low
            return randomIntInRange(85, 87); // still <88
        }
        else if (r < 0.08)
        {
            // WARNING band
            return randomIntInRange(88, 91);
        }
        else
        {
            // NORMAL
            return randomIntInRange(92, 100);
        }
    }
}
