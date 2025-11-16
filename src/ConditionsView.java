public class ConditionsView implements Observer {
    @Override
    public void update(PatientData patient) {
        System.out.println("Patient: " + patient.getName() +
                " | HR: " + patient.getHeartRate() +
                " | Temp: " + patient.getTemperature() +
                " | O2: " + patient.getOxygen());
    }
}
