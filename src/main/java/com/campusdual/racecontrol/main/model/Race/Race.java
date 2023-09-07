package com.campusdual.racecontrol.main.model.Race;

import com.campusdual.racecontrol.main.model.Car;
import com.campusdual.racecontrol.main.model.Garage;
import com.campusdual.racecontrol.main.model.RacingCar;

import java.util.ArrayList;
import java.util.List;

public abstract class Race {
    protected long id;
    protected String name;
    protected boolean finished = false;
    protected List<Garage> garages = new ArrayList<>();
    protected List<RacingCar> cars = new ArrayList<>();

    protected Car[] podium = new Car[3];

    public Race(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public List<Garage> getGarages() {
        return garages;
    }

    public void setGarages(List<Garage> garages) {
        this.garages = garages;
    }

    public List<RacingCar> getCars() {
        return cars;
    }

    public void setCars(List<RacingCar> cars) {
        this.cars = cars;
    }

    public Car[] getPodium() {
        return podium;
    }

    public void setPodium(Car[] podium) {
        this.podium = podium;
    }

    @Override
    public String toString() {
        return "Race{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", garages=" + garages +
                ", cars=" + cars +
                '}';
    }
}
