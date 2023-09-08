package com.campusdual.racecontrol.model.Race;

import com.campusdual.racecontrol.model.Car;
import com.campusdual.racecontrol.model.Garage;
import com.campusdual.racecontrol.model.RacingCar;

public class StandardRace extends Race implements Runnable {
    /*
     * Standard races last a number of hours, normally 3.
     */
    private int duration = 0;
    private int timer = 0;

    public StandardRace(long id, String name, int duration) {
        super(id, name);
        this.duration = duration; //in minutes
    }

    /*
     * If only one garage participates, all cars for that garage race.
     * If more than one garage participantes, one car (chosen at random) for each garage races.
     */
    public void startRace() {
        if (!garages.isEmpty()) {
            if (garages.size() == 1) {
                for (Car c : garages.get(0).getCars()){
                    cars.add(new RacingCar(c.getId(), c.getBrand(), c.getModel(), c.getGarage()));
                }
            } else {
                for (Garage g : garages) {
                    int index = (int) (Math.random() * g.getCars().size());
                    Car currentCar = g.getCars().get(index);
                    cars.add(new RacingCar(currentCar.getId(), currentCar.getBrand(), currentCar.getModel(), currentCar.getGarage()));
                }
            }

            if (!cars.isEmpty()) {
                run();
            }
        }
    }

    @Override
    public void run() {

        System.out.println("========================================");
        System.out.println("Starting race: " + getName() + ", type: " + super.getClass().getName() + ".");
        System.out.printf("Duration: %02d:%02d.\n",duration /60, duration % 60);
        System.out.println("Contestants:");
        for(RacingCar c : cars) {
            System.out.printf("[%d] %s, %s, %s.\n", c.getId(), c.getGarage().getName(), c.getBrand(), c.getModel());
        }
        System.out.println("========================================");
        do {

            for (RacingCar c : cars){
                c.race();
            }

            timer += 1;
            if (duration == timer) {
                finished = true;
            }

            System.out.printf("Elapsed time: %02d:%02d.\n", timer / 60, timer % 60);
            System.out.println("Status:");
            for (RacingCar c : cars){
                System.out.printf("[%d].Speed: %.2f. Distance km: %.2f km/minute.\n", c.getId(), c.getSpeed(), c.getDistance());
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        } while (!finished);
    }

    public static void main(String[] args) {
        Garage garagePaco = new Garage(1, "Garaje Paco");
        Car car1 = new Car(1, "Seat", "Ibiza", garagePaco);
        Car car2 = new Car(2, "Citroen", "Xsara", garagePaco);
        Car car3 = new Car(3, "Opel", "Corsa", garagePaco);

        garagePaco.getCars().add(car1);
        garagePaco.getCars().add(car2);
        garagePaco.getCars().add(car3);

        StandardRace sr = new StandardRace(1, "Test", 180);
        sr.getGarages().add(garagePaco);

        sr.startRace();
    }
}
