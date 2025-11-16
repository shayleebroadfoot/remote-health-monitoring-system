import java.util.ArrayList;
import java.util.List;

public class PatientData {
    private String id;
    private String name;
    private double heartRate;
    private double temperature;
    private double oxygen;
    private List<Observer> observers = new ArrayList<>();

    public PatientData(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addObserver(Observer o) {
        observers.add(o);
    }

    public void removeObserver(Observer o){
        observers.remove(o);
    }

    public void updateVitals(double heartRate, double temperature, double oxygen) {
        this.heartRate = heartRate;
        this.temperature = temperature;
        this.oxygen = oxygen;
        notifyObservers();
        HistoryManager.getInstance().save(this); // Save to CSV history
    }

    private void notifyObservers() {
        for (Observer o : observers) {
            o.update(this);
        }
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public double getHeartRate() { return heartRate; }
    public double getTemperature() { return temperature; }
    public double getOxygen() { return oxygen; }

    // CSV representation for HistoryManager
    public String toCSV() {
        return System.currentTimeMillis() + "," + id + "," + heartRate + "," + temperature + "," + oxygen;
    }

}
