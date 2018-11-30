package ru.Chernoviki.lambdaMFTI;

import java.util.ArrayList;
import java.util.List;

public class Switcher {

    private List<ElectricityConsumer> listeners = new ArrayList<>();

    public void addElListener(ElectricityConsumer listener){
        listeners.add(listener);  // подписка на событие
    }

    public void removeElListener(ElectricityConsumer listener){
        listeners.remove(listener);  // подписка на событие
    }

    public void switchOn() {
        System.out.println("Выключатель ВКЛ");
//        if (listeners != null) {
//            listeners.electricityOn();
        for(ElectricityConsumer c : listeners){
            c.electricityOn(this);
        }
    }
}
