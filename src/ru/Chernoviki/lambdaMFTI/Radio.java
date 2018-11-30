package ru.Chernoviki.lambdaMFTI;

public class Radio implements ElectricityConsumer {

    public void playMusic() {
        System.out.println("Радио заиграло");
    }

    @Override
    public void electricityOn(Object sender) {
        playMusic();
    }
}
