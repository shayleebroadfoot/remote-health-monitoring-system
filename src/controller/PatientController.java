package controller;

import employee.AuthenticationService;
import domain.Patient;
import patient.PatientService;

import java.util.List;
import java.util.Optional;

public class PatientController
{
    private final PatientService patientService;
    private final AuthenticationService authService;

    public PatientController(PatientService patientService, AuthenticationService authService)
    {
        this.patientService = patientService;
        this.authService = authService;
    }

    private void ensureLoggedIn()
    {
        if (!authService.isLoggedIn())
            throw new IllegalStateException("User must be logged in to manage patients.");

    }

    // Queries -----------------------------------------------------------------

    public List<Patient> loadPatients()
    {
        ensureLoggedIn();
        return patientService.getAllPatients();
    }

    public Optional<Patient> getPatientById(String id)
    {
        ensureLoggedIn();
        return patientService.getPatientById(id);
    }

    // Commands ----------------------------------------------------------------

    public void handleCreatePatient(Patient patient)
    {
        ensureLoggedIn();
        patientService.createPatient(patient);
    }

    public void handleEditPatient(String patientId, Patient updatedPatient)
    {
        ensureLoggedIn();
        // you could sanity-check IDs here if you want
        patientService.updatePatient(updatedPatient);
    }

    public void handleDeletePatient(String patientId)
    {
        ensureLoggedIn();
        patientService.deletePatient(patientId);
    }

//    public void handleEnrollPatient(String patientId, Enrollment enrollment)
//    {
//        ensureLoggedIn();
//        patientService.enrollPatient(patientId, enrollment);
//    }
//
//    public void handleUnenrollPatient(String patientId)
//    {
//        ensureLoggedIn();
//        patientService.unenrollPatient(patientId);
//    }
}
