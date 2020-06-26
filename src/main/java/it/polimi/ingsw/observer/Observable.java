package it.polimi.ingsw.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to implements Observer pattern
 * */
public class Observable<T> {

    private final List<Observer<T>> observers = new ArrayList<>();

    /**
     * Method that adds the observer given to the list of observer of this class
     * @param observer is the observer to add
     * */
    public void addObserver(Observer<T> observer) {
        synchronized (observers) {
            observers.add(observer);
        }
    }

    /**
     * Method that removes the observer given to the list of observer of this class
     * @param observer is the observer to remove
     * */
    public void removeObserver(Observer<T> observer) {
        synchronized (observers) {
            observers.remove(observer);
        }
    }

    /**
     * Method notify to all the observers of this class a message given
     * @param message is the message to notify
     * */
    protected void notify(T message) {
        synchronized (observers) {
            for (Observer<T> observer : observers) {
                observer.update(message);
            }
        }
    }

}
