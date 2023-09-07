package com.campusdual.racecontrol.main.model;

public class RacingCar extends Car {
    private double speed;
    private double distance;

    public RacingCar(long id, String brand, String model, Garage garage) {
        super(id, brand, model, garage);
    }

    public double getSpeed() {
        return speed;
    }

    public double getDistance() {
        return distance;
    }

    public void race(){
        if ((int)(Math.random() * 2) == 0){
            speed -= 1.66;
            if (speed <= 0) {
                speed = 0;
            }
        } else {
            speed += 1.66;
        }

        distance += speed;
    }
}
