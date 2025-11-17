package app;

import alert.AlertService;
import domain.Patient;
import domain.VitalSigns;
import io.BasestationReader;
import io.CsvBasestationAdapter;
import io.CsvFileReader;
import monitoring.BasicThresholdEvaluator;
import monitoring.ConditionEvaluator;
import monitoring.MonitoringModel;
import monitoring.MonitoringService;
import patient.InMemoryPatientRepository;
import patient.PatientService;

import java.io.File;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main
{
    public static void main(String[] args)
    {
        File file = new File("test_vitals.csv");
        CsvFileReader reader = new CsvFileReader(file);
        CsvBasestationAdapter adapter = new CsvBasestationAdapter(reader);

        List<VitalSigns> readings = adapter.readAll();

    }
}