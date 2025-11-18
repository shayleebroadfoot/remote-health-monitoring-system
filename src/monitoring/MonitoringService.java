package monitoring;

import alert.AlertService;
import domain.*;
import io.BasestationReader;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

public class MonitoringService
{
    private final BasestationReader basestationReader;
    private final AlertService alertService;
    private final MonitoringModel monitoringModel;
    private final DeviceRepository deviceRepository;
    private final MonitoredPatientDataRepository monitoredPatientDataRepository;

    public MonitoringService(BasestationReader basestationReader, AlertService alertService, MonitoringModel monitoringModel, DeviceRepository deviceRepository, MonitoredPatientDataRepository monitoredPatientDataRepository)
    {
        this.basestationReader = basestationReader;
        this.alertService = alertService;
        this.monitoringModel = monitoringModel;
        this.deviceRepository = deviceRepository;
        this.monitoredPatientDataRepository = monitoredPatientDataRepository;
    }

    /**
     * Reads all available vitals from the basestation once and processes them.
     */
    public void pollOnce()
    {
        List<VitalSigns> readings = basestationReader.readAll();

        for (VitalSigns vitalSigns : readings)
        {
            processReading(vitalSigns);
        }
    }

    /**
     * Process a single vitals reading for one patient.
     */
    public void processReading(VitalSigns vitalSigns)
    {
        if (vitalSigns == null)
            return;

        String deviceId = vitalSigns.getDeviceId();

        if (deviceId == null)
            return;

        Optional<Device> tempDevice = deviceRepository.findById(deviceId);

        if (tempDevice.isEmpty())
            return;

        Device device = tempDevice.get();

        if (!device.isActive())
            return;

        Patient patient = device.getPatient();

        if (patient == null)
            return;

        Optional<MonitoredPatientData> tempPatientData = monitoredPatientDataRepository.findByPatientId(patient.getId());
        MonitoredPatientData patientData = tempPatientData.isEmpty() ? new MonitoredPatientData(patient) : tempPatientData.get();

        updateLatestVitals(patientData, vitalSigns);

        ConditionEvaluator conditionEvaluator = setConditionEvaluator(getAge(patient.getBirthDate()));
        PatientStatus status = conditionEvaluator.evaluate(vitalSigns);
        String message = conditionEvaluator.getMessage();

        updatePatientStatus(patientData);
        updateAlerts(patient, status, message);
    }

    private int getAge(LocalDate birthDate)
    {
        return (int)ChronoUnit.DAYS.between(birthDate, LocalDate.now());
    }

    private ConditionEvaluator setConditionEvaluator(int age)
    {
        if (age < 65)
            return new BasicThresholdEvaluator();
        else
            return new ElderlyPatientEvaluator();
    }

    private void updateLatestVitals(MonitoredPatientData monitoredPatientData, VitalSigns vitalSigns)
    {
        if (monitoredPatientData.getLatestVitals() != null)
            monitoredPatientData.addToHistory(monitoredPatientData.getLatestVitals());

        monitoredPatientData.setLatestVitals(vitalSigns);
        monitoredPatientDataRepository.save(monitoredPatientData);
    }

    private void updatePatientStatus(MonitoredPatientData monitoredPatientData)
    {
        monitoredPatientDataRepository.save(monitoredPatientData);
        syncMonitoredPatientData();
    }

    private void updateAlerts(Patient patient, PatientStatus status, String message)
    {
        if (status == PatientStatus.NORMAL)
            return;

        if (message == null || message.isEmpty())
            message = "no information available";

        Alert alert = new Alert(patient, status, message);
        alertService.raiseAlert(alert);

        syncAlerts();
    }

    private void syncMonitoredPatientData()
    {
        monitoringModel.setMonitoredPatientData(monitoredPatientDataRepository.findAll());
    }
    private void syncAlerts()
    {
        monitoringModel.setActiveAlerts(alertService.getActiveAlerts());
    }
}

