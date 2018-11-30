package ru.Chernoviki.lambdaMFTI;


public class Lamp implements ElectricityConsumer {
    public void lightOn() {
        System.out.println("Лампа зажглась");
    }

    @Override
    public void electricityOn(Object sender) {
        lightOn();
    }
}
