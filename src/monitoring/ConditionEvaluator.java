package monitoring;

import domain.PatientStatus;
import domain.VitalSigns;

public interface ConditionEvaluator
{
    public PatientStatus evaluate(VitalSigns vitals);
    public String getMessage();

}
