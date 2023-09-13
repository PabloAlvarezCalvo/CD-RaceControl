package com.campusdual.racecontrol.model.Race;

import com.campusdual.racecontrol.model.ScoreCar;
import com.campusdual.racecontrol.model.Garage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Comparator;

public class StandardRace extends Race implements Runnable {
    /*
     * Standard races last a number of hours, normally 3.
     */
    private int duration = 0;
    private int timer = 0;

    public StandardRace(String name, int duration) {
        super(name);
        this.duration = duration; //in minutes
        //this.id = generateId();
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    /*
     * If only one garage participates, all cars for that garage race.
     * If more than one garage participantes, one car (chosen at random) for each garage races.
     */
    public void startRace() {
        if (!garages.isEmpty()) {
            if (garages.size() == 1) {

                for (ScoreCar c : garages.get(0).getCars()) {
                    cars.add(new ScoreCar(c.getBrand(), c.getModel()));
                }
            } else {
                for (Garage g : garages) {
                    int index = (int) (Math.random() * g.getCars().size());
                    ScoreCar currentCar = g.getCars().get(index);
                    cars.add(currentCar);
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
        System.out.printf("Duration: %02d:%02d.\n", duration / 60, duration % 60);
        System.out.println("Contestants:");
        for (ScoreCar c : cars) {
            //System.out.printf("[%d] %s, %s, %s.\n", c.getId(), c.getGarage().getName(), c.getBrand(), c.getModel());
            System.out.printf("[%d] %s, %s, %s.\n", c.getId(), c.getGarageName(), c.getBrand(), c.getModel());
        }
        System.out.println("========================================");

        //Reset speed and distance on each race
        cars.forEach(ScoreCar::restart);

        do {

            cars.forEach(ScoreCar::drive);

            timer += 1;
            if (duration == timer) {
                finished = true;
            }

            System.out.printf("Elapsed time: %02d:%02d.\n", timer / 60, timer % 60);
            System.out.println("Status:");
            for (ScoreCar c : cars) {
                System.out.printf("[%d]Speed: %.2f km/h. Distance km: %.2f km.\n", c.getId(), c.getSpeedometer(), c.getDistance() / 1000);
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        } while (!finished);

        cars.sort(Comparator.comparing(ScoreCar::getDistance).reversed());

        for (int i = 0; i < cars.size() && i < podium.length; i++){
            podium[i] = cars.get(i);
        }

    }

    public JSONObject exportRace() {
        JSONObject object = new JSONObject();
        object.put(RACE_ID, id);
        object.put(RACE_NAME, name);
        object.put(RACE_TYPE, ELIMINATION_TYPE);

        JSONArray arrayIdGarages = new JSONArray();
        for (Garage g : garages) {
            arrayIdGarages.add(g.getId());
        }
        object.put(RACE_GARAGES, arrayIdGarages);

        JSONArray arrayIdCars = new JSONArray();
        for (ScoreCar c : cars) {
            arrayIdCars.add(c.getId());
        }
        object.put(RACE_CARS, arrayIdCars);

        return object;
    }

    public static void main(String[] args) {
        Garage garagePaco = new Garage("Garaje Paco");

        ScoreCar car1 = new ScoreCar("Seat", "Ibiza");
        ScoreCar car2 = new ScoreCar("Citroen", "Xsara");
        ScoreCar car3 = new ScoreCar("Opel", "Corsa");

        car1.setGarageName(garagePaco.getName());
        car2.setGarageName(garagePaco.getName());
        car3.setGarageName(garagePaco.getName());

        garagePaco.getCars().add(car1);
        garagePaco.getCars().add(car2);
        garagePaco.getCars().add(car3);

        StandardRace sr = new StandardRace("Test", 180);
        sr.getGarages().add(garagePaco);

        sr.startRace();
    }
}
