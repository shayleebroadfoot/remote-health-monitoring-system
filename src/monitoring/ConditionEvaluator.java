package monitoring;

import domain.*;

public interface ConditionEvaluator
{
    public PatientStatus evaluate(VitalSigns vitals);
    public String getMessage();

}
