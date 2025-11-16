import java.util.Random;
import java.util.Scanner;

public class SimulationController {
    private PatientRepository repository = new PatientRepository();
    private ConditionEvaluator evaluator = new BasicConditionEvaluator();
    private Scanner scanner = new Scanner(System.in);

    public void start() {
        System.out.println("Login to Remote Health Monitoring System");
        System.out.print("Username: ");
        String user = scanner.nextLine();
        System.out.print("Password: ");
        String pass = scanner.nextLine();

        if (!AuthenticationService.getInstance().login(user, pass)) {
            System.out.println("Authentication failed!");
            return;
        }

        System.out.println("Authentication successful! Welcome " + user);

        // Add sample patients
        PatientData p1 = new PatientData("p1", "Alice");
        p1.addObserver(new ConditionsView());
        repository.addPatient(p1);

        PatientData p2 = new PatientData("p2", "Bob");
        p2.addObserver(new ConditionsView());
        repository.addPatient(p2);

        // Start simulation loop
        Random rand = new Random();
        for (int i = 0; i < 10; i++) { // 10 iterations for demo
            for (PatientData patient : repository.getAllPatients()) {
                double hr = 60 + rand.nextInt(80);
                double temp = 36 + rand.nextDouble() * 2;
                double o2 = 92 + rand.nextInt(8);

                patient.updateVitals(hr, temp, o2);

                String status = evaluator.evaluate(patient);
                System.out.println("Alert Status: " + status);
            }
            try { Thread.sleep(1000); } catch (InterruptedException e) { }
            System.out.println("----");
        }
    }
}
