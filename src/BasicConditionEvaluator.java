public class BasicConditionEvaluator implements ConditionEvaluator {
    @Override
    public String evaluate(PatientData patient) {
        if (patient.getHeartRate() > 120 || patient.getOxygen() < 90) {
            return "CRITICAL";
        } else if (patient.getHeartRate() > 100) {
            return "WARNING";
        } else {
            return "NORMAL";
        }
    }
}
