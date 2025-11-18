package app;

import alert.AlertRepository;
import alert.AlertService;
import alert.InMemoryAlertRepository;
import domain.*;
import io.BasestationReader;
import io.CsvBasestationAdapter;
import io.CsvFileReader;
import monitoring.*;
import patient.InMemoryPatientRepository;
import patient.PatientRepository;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        // Create patients and add to patient repository
        PatientRepository patientRepository = new InMemoryPatientRepository();
        List<Patient> patients = createSamplePatients();
        for (Patient patient : patients)
        {
            patientRepository.save(patient);
        }
        patients = patientRepository.findAll(); // Update with new patient IDs created from repository

        // Create devices and add to device repository
        DeviceRepository deviceRepository = new InMemoryDeviceRepository();
        List<Device> devices = createDevicesForPatients(patients);
        for (Device device : devices)
        {
            deviceRepository.save(device);
        }
        devices = deviceRepository.findAll(); // Update with new device IDs created from repository

        for (Device device : devices)
        {
            System.out.println(device.toString());
        }

        // Get csv file with device readings
        File file = new File("test_vitals.csv");
        writeTestVitalsFile(file, devices); // Generate csv with device ids and random vitals
        CsvBasestationAdapter adapter = new CsvBasestationAdapter(new CsvFileReader(file));
        List<VitalSigns> readings = adapter.readAll();

        for (VitalSigns vitalSigns : readings)
        {
            System.out.println(vitalSigns.toString());
        }

        AlertRepository alertRepository = new InMemoryAlertRepository();
        AlertService alertService = new AlertService(alertRepository);
        MonitoringModel monitoringModel = new MonitoringModel();
        MonitoredPatientDataRepository monitoredPatientDataRepository = new InMemoryMonitoredPatientDataRepository();
        MonitoringService monitoringService = new MonitoringService(adapter, alertService, monitoringModel, deviceRepository, monitoredPatientDataRepository);
        monitoringService.pollOnce();

        for (MonitoredPatientData monitoredPatientData : monitoredPatientDataRepository.findAll())
        {
            System.out.println(monitoredPatientData.toString());
        }
    }

    public static List<Patient> createSamplePatients()
    {
        List<Patient> patients = new ArrayList<>();

        patients.add(new Patient(
                "Alice",
                "Morgan",
                LocalDate.of(1985, 3, 12),
                "123 Willow St, Denver, CO",
                "303-555-1122"
        ));

        patients.add(new Patient(
                "Brian",
                "Santos",
                LocalDate.of(1990, 7, 22),
                "89 Maple Ave, Austin, TX",
                "512-555-8831"
        ));

        patients.add(new Patient(
                "Chloe",
                "Reynolds",
                LocalDate.of(1978, 11, 4),
                "450 Ocean Dr, Miami, FL",
                "305-555-7799"
        ));

        patients.add(new Patient(
                "Daniel",
                "Kim",
                LocalDate.of(2001, 1, 18),
                "741 Cedar Rd, Seattle, WA",
                "206-555-6644"
        ));

        patients.add(new Patient(
                "Eva",
                "Lopez",
                LocalDate.of(1995, 9, 30),
                "230 Pine St, San Diego, CA",
                "619-555-3312"
        ));

        return patients;
    }

    public static List<Device> createDevicesForPatients(List<Patient> patients)
    {
        List<Device> devices = new ArrayList<>();

        devices.add(new Device(
                "Heart Monitor",
                patients.get(0),
                LocalDateTime.now(),
                true
        ));

        devices.add(new Device(
                "Blood Pressure Cuff",
                patients.get(1),
                LocalDateTime.now(),
                true
        ));

        devices.add(new Device(
                "Glucose Sensor",
                patients.get(2),
                LocalDateTime.now(),
                true
        ));

        devices.add(new Device(
                "Pulse Oximeter",
                patients.get(3),
                LocalDateTime.now(),
                true
        ));

        devices.add(new Device(
                "ECG Patch",
                patients.get(4),
                LocalDateTime.now(),
                true
        ));

        return devices;
    }

    private static void writeTestVitalsFile(File file, List<Device> devices)
    {
        try (PrintWriter out = new PrintWriter(file))
        {
            for (Device d : devices)
            {
                // Generate synthetic vitals readings for testing
                LocalDateTime now = LocalDateTime.now();

                int heartRate = 70 + (int) (Math.random() * 40);      // 70–110
                double temp = 36.5 + (Math.random() * 1.5);           // 36.5–38.0
                int spo2 = 94 + (int) (Math.random() * 6);            // 94–99
                boolean ecgIrregular = Math.random() < 0.2;           // 20% chance

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

}