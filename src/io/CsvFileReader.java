package io;

import domain.Device;
import domain.VitalSigns;
import monitoring.DeviceRepository;

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
    private final DeviceRepository deviceRepository;
    private final CsvTestDataGenerator generator;

    public CsvFileReader(File csvFile, DeviceRepository deviceRepository)
    {
        this.csvFile = csvFile;
        this.deviceRepository = deviceRepository;
        this.generator = new CsvTestDataGenerator();
    }

    public List<VitalSigns> getVitalSigns()
    {
        List<Device> devices = deviceRepository.findAll();

        if (devices == null || devices.isEmpty())
            return new ArrayList<>();

        generator.writeTestVitalsFile(csvFile, devices);

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

    private List<String> readLines()
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

            return new VitalSigns(deviceId, timestamp, heartRate, temperature, spo2, ecgIrregular);
        }
        finally
        {
            scanner.close();
        }
    }
}
