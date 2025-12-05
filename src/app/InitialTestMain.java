package app;

import alert.AlertRepository;
import alert.AlertService;
import alert.InMemoryAlertRepository;
import controller.LoginController;
import io.CsvTestDataGenerator;
import view.LoginView;
import domain.*;
import employee.AuthenticationService;
import employee.EmployeeRepository;
import employee.EmployeeService;
import employee.InMemoryEmployeeRepository;
import io.BasestationReader;
import io.CsvBasestationAdapter;
import io.CsvFileReader;
import monitoring.*;
import patient.InMemoryPatientRepository;
import patient.PatientRepository;
import view.MonitoringDashboardView;

import java.io.File;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InitialTestMain
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
        CsvTestDataGenerator generator = new CsvTestDataGenerator();
        generator.writeTestVitalsFile(file, devices);

        BasestationReader csvBasestationAdapter = new CsvBasestationAdapter(new CsvFileReader(file, deviceRepository));
        List<VitalSigns> readings = csvBasestationAdapter.readAll();

        for (VitalSigns vitalSigns : readings)
        {
            System.out.println(vitalSigns.toString());
        }

        AlertRepository alertRepository = new InMemoryAlertRepository();
        AlertService alertService = new AlertService(alertRepository);
        MonitoringModel monitoringModel = new MonitoringModel();
        MonitoredPatientDataRepository monitoredPatientDataRepository = new InMemoryMonitoredPatientDataRepository();
        MonitoringService monitoringService = new MonitoringService(csvBasestationAdapter, alertService, monitoringModel, deviceRepository, monitoredPatientDataRepository);
        monitoringService.pollOnce();

        for (MonitoredPatientData monitoredPatientData : monitoredPatientDataRepository.findAll())
        {
            System.out.println(monitoredPatientData.toString());
        }

        // String firstName, String lastName, String email, String username, String password, String role
        Employee employee1 = new Employee("Shaylee", "Broadfoot", "sbroadfoot@gmail.com", "sbroadfoot", "password", "ADMIN");
        EmployeeRepository employeeRepository = new InMemoryEmployeeRepository();
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        employeeService.createEmployee(employee1);
        List<Employee> employees = employeeRepository.findAll();

        for (Employee employee : employees)
        {
            System.out.println(employee.toString());
        }

        AuthenticationService authService = AuthenticationService.getInstance(employeeRepository);

        System.out.println(authService.login(employee1.getUsername(), employee1.getPassword()) ? "login" : "no login");
        authService.logout();

        MonitoringDashboardView monitoringDashboardView = new MonitoringDashboardView();
        List<MonitoredPatientData> list = monitoredPatientDataRepository.findAll();
        List<Alert> alerts = new ArrayList<>();
        monitoringDashboardView.update(list, alerts);

        /* Code to run app using controllers and views */
        Scanner scanner = new Scanner(System.in);

        LoginView loginView = new LoginView(scanner);
        LoginController loginController = new LoginController(authService, loginView);

        boolean loggedIn = loginController.run();

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
                LocalDate.of(1953, 9, 30),
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
}