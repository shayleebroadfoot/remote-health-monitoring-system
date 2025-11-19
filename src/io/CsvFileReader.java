package io;

import domain.VitalSigns;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CsvFileReader
{
    private final File csvFile;

    public CsvFileReader(File csvFile)
    {
        this.csvFile = csvFile;
    }

    public List<String> readLines()
    {
        try
        {
            return Files.readAllLines(csvFile.toPath());
        }
        catch (IOException e)
        {
            throw new RuntimeException("Failed to read CSV file: " + csvFile, e);
        }
    }

    public List<VitalSigns> getVitalSigns()
    {
        List<String> lines = readLines();
        List<VitalSigns> readings = new ArrayList<>();

        for (String line : lines)
        {
            if (line == null || line.trim().isEmpty())
                continue;

            VitalSigns v = parseLine(line);

            if (v != null)
                readings.add(v);
        }

        try
        {
            Files.write(csvFile.toPath(), new byte[0]); // clear file after reading
        }
        catch (IOException e)
        {
            throw new RuntimeException("Failed to clear CSV file: " + csvFile, e);
        }

        return readings;
    }

    private VitalSigns parseLine(String line)
    {
        Scanner scanner = new Scanner(line);
        scanner.useDelimiter(",");

        try
        {
            String deviceId = scanner.next();
            String timestampStr = scanner.next();
            int heartRate = scanner.nextInt();
            double temperature = scanner.nextDouble();
            int spo2 = scanner.nextInt();
            boolean ecgIrregular = scanner.nextBoolean();

            LocalDateTime timestamp = LocalDateTime.parse(timestampStr);

            // String deviceId, LocalDateTime timeStamp, int heartRate, double temperature, int spo2, boolean ecgIrregular
            return new VitalSigns(deviceId, timestamp, heartRate, temperature, spo2, ecgIrregular);
        }

        finally
        {
            scanner.close();
        }
    }
}
