import java.io.*;

public class HistoryManager {
    private static HistoryManager instance = new HistoryManager();
    private PrintWriter writer;

    private HistoryManager() {
        try {
            writer = new PrintWriter(new FileWriter("PatientHistory.csv", true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HistoryManager getInstance() {
        return instance;
    }

    public void save(PatientData patient) {
        writer.println(patient.toCSV());
        writer.flush();
    }
}
