package ru.chernoviki.lambdaMGTU;


// шаблон Наблюдатель (Observer)
// https://www.youtube.com/watch?v=DNC6Lknn2AE

public class Program {

    public static void fire(Object sender){
        System.out.println("FIREEEEE!");
    }

    public static void main(String[] args) {
        Switcher switcher = new Switcher();

        Lamp lamp = new Lamp();
        Radio radio = new Radio();

        switcher.addElListener(lamp);
        switcher.addElListener(radio);

//        switcher.addElListener(
//                new ElectricityConsumer() {
//                    @Override
//                    public void electricityOn() {
//                        System.out.println("Пожар");
//                    }
//                }
//        );

//        switcher.addElListener( sender -> System.out.println("Пожар") );

        switcher.addElListener( Program :: fire );


        switcher.switchOn();
    }
}
