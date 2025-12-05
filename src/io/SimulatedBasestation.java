package io;

import app.CsvTestDataGenerator;
import domain.Device;
import domain.VitalSigns;
import monitoring.DeviceRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SimulatedBasestation implements BasestationReader
{
    private final DeviceRepository deviceRepository;
    private final File csvFile;
    private final CsvTestDataGenerator generator;
    private final CsvBasestationAdapter adapter;

    public SimulatedBasestation(DeviceRepository deviceRepository, File csvFile)
    {
        this.deviceRepository = deviceRepository;
        this.csvFile = csvFile;
        this.generator = new CsvTestDataGenerator();
        this.adapter = new CsvBasestationAdapter(new CsvFileReader(csvFile));
    }

    @Override
    public List<VitalSigns> readAll()
    {
        List<Device> devices = deviceRepository.findAll();

        if (devices == null || devices.isEmpty())
            return new ArrayList<>();

        generator.writeTestVitalsFile(csvFile, devices);
        return adapter.readAll();
    }
}
