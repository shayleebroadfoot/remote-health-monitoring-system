package app;

import alert.AlertRepository;
import alert.AlertService;
import alert.InMemoryAlertRepository;
import controller.LoginController;
import controller.MonitoringController;
import domain.Device;
import domain.Employee;
import domain.Patient;
import employee.AuthenticationService;
import employee.EmployeeRepository;
import employee.EmployeeService;
import employee.InMemoryEmployeeRepository;
import io.BasestationReader;
import io.CsvBasestationAdapter;
import io.CsvFileReader;
import io.SimulatedBasestation;
import monitoring.DeviceRepository;
import monitoring.InMemoryDeviceRepository;
import monitoring.MonitoredPatientDataRepository;
import monitoring.InMemoryMonitoredPatientDataRepository;
import monitoring.MonitoringModel;
import monitoring.MonitoringService;
import patient.InMemoryPatientRepository;
import patient.PatientRepository;
import view.LoginView;
import view.MainMenuView;
import view.MonitoringDashboardView;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppMain
{
    public static void main(String[] args)
    {
        // Create patients and add them to repository
        PatientRepository patientRepository = new InMemoryPatientRepository();
        List<Patient> patients = createSamplePatients();
        for (Patient patient : patients)
        {
            patientRepository.save(patient);
        }
        patients = patientRepository.findAll(); // Set patients list again after repository creates the patient IDs

        // Create devices and add them to repository
        DeviceRepository deviceRepository = new InMemoryDeviceRepository();
        List<Device> devices = createDevicesForPatients(patients);
        for (Device device : devices)
        {
            deviceRepository.save(device);
        }
        devices = deviceRepository.findAll(); // Set patients list again after repository creates the patient IDs

        File file = new File("test_vitals.csv");
        CsvTestDataGenerator generator = new CsvTestDataGenerator();
        generator.writeTestVitalsFile(file, devices);

        BasestationReader basestationReader = new SimulatedBasestation(deviceRepository, file);
//        BasestationReader basestationReader = new CsvBasestationAdapter(new CsvFileReader(file));

        AlertRepository alertRepository = new InMemoryAlertRepository();
        AlertService alertService = new AlertService(alertRepository);

        MonitoringModel monitoringModel = new MonitoringModel();
        MonitoredPatientDataRepository monitoredPatientDataRepository = new InMemoryMonitoredPatientDataRepository();
        MonitoringService monitoringService = new MonitoringService(basestationReader, alertService, monitoringModel, deviceRepository, monitoredPatientDataRepository);

        MonitoringDashboardView monitoringDashboardView = new MonitoringDashboardView();
        MonitoringController monitoringController = new MonitoringController(monitoringService, monitoringModel, monitoringDashboardView);

        EmployeeRepository employeeRepository = new InMemoryEmployeeRepository();
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        Employee employee1 = new Employee("Shaylee", "Broadfoot", "sbroadfoot@gmail.com", "sbroadfoot", "password", "ADMIN");
        employeeService.createEmployee(employee1);

        AuthenticationService authService = AuthenticationService.getInstance(employeeRepository);

        Scanner scanner = new Scanner(System.in);

        LoginView loginView = new LoginView(scanner);
        LoginController loginController = new LoginController(authService, loginView);
        MainMenuView mainMenuView = new MainMenuView(scanner);

        AppController appController = new AppController(authService, loginController, monitoringController, monitoringModel, mainMenuView, scanner);
        appController.run();
    }

    public static List<Patient> createSamplePatients()
    {
        List<Patient> patients = new ArrayList<>();

        patients.add(new Patient("Alice", "Morgan", LocalDate.of(1985, 3, 12), "123 Willow St, Denver, CO", "303-555-1122"));
        patients.add(new Patient("Brian", "Santos", LocalDate.of(1990, 7, 22), "89 Maple Ave, Austin, TX", "512-555-8831"));
        patients.add(new Patient("Chloe", "Reynolds", LocalDate.of(1978, 11, 4), "450 Ocean Dr, Miami, FL", "305-555-7799"));
        patients.add(new Patient("Daniel", "Kim", LocalDate.of(2001, 1, 18), "741 Cedar Rd, Seattle, WA", "206-555-6644"));
        patients.add(new Patient("Eva", "Lopez", LocalDate.of(1953, 9, 30), "230 Pine St, San Diego, CA", "619-555-3312"));

        return patients;
    }

    public static List<Device> createDevicesForPatients(List<Patient> patients)
    {
        List<Device> devices = new ArrayList<>();

        devices.add(new Device("Heart Monitor", patients.get(0), LocalDateTime.now(), true));
        devices.add(new Device("Blood Pressure Cuff", patients.get(1), LocalDateTime.now(), true));
        devices.add(new Device("Glucose Sensor", patients.get(2), LocalDateTime.now(), true));
        devices.add(new Device("Pulse Oximeter", patients.get(3), LocalDateTime.now(), true));
        devices.add(new Device("ECG Patch", patients.get(4), LocalDateTime.now(), true));

        return devices;
    }
}
