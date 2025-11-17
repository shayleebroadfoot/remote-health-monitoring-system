package monitoring;

import view.Observer;

public interface Subject
{
    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers();
}
