package it.polimi.ingsw.observer;

/**
 * Interface used to implements the Observer pattern
 * */
public interface Observer<T> {
    void update(T message);
}

