package it.polimi.ingsw;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Observable {

    private List<PropertyChangeListener> listeners = new ArrayList<>();

    public void addObserver(PropertyChangeListener listener) {
        listeners.add(listener);
    }

    public void removeObserver(PropertyChangeListener listener) {
        listeners.remove(listener);
    }

    public void notify(String property, String oldValue, String newValue) {
        for (PropertyChangeListener listener : listeners)
            listener.propertyChange(new PropertyChangeEvent(this, property, oldValue, newValue));
    }

}

