package view;

import controller.PatientController;
import domain.Patient;

import java.util.List;
import java.util.Optional;

public class PatientsView
{
    private final PatientController controller;
    private final PatientEditorDialog patientEditorDialog;

    public PatientsView(PatientController controller)
    {
        this.controller = controller;
        this.patientEditorDialog = new PatientEditorDialog();
    }

//    private void reloadAndDisplayPatients()
//    {
//        List<Patient> patients = controller.getAllPatients();
//        // print / render list
//    }
//
//    private void createFlow()
//    {
//        Patient p = patientEditorDialog.showCreateDialog(); // gathers fields
//        controller.createPatient(p);
//    }
//
//    private void editFlow(String id) {
//        Optional<Patient> existing = controller.getPatientById(id);
//        if (existing.isEmpty()) { /* show error */ return; }
//
//        Patient edited = patientEditorDialog.showEditDialog(existing.get());
//        controller.updatePatient(edited);
//    }
//
//    private void enrollFlow(String id) {
//        // build Enrollment object somehow (another small dialog/helper)
//        Enrollment enrollment = enrollmentDialog.showEnrollmentDialog();
//        controller.enrollPatient(id, enrollment);
//    }
}
