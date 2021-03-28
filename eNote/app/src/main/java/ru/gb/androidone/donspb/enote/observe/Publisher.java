package ru.gb.androidone.donspb.enote.observe;

import java.util.ArrayList;
import java.util.List;

import ru.gb.androidone.donspb.enote.datapart.EnoteData;

public class Publisher {
    private List<Observer> observers;

    public Publisher() {
        observers = new ArrayList<>();
    }

    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    public void notifySingle(EnoteData enoteData) {
        for (Observer observer : observers) {
            observer.updateEnoteData(enoteData);
            unsubscribe(observer);
        }
    }
}
