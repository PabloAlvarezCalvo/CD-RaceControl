package model.Race;

import model.Car;
import model.Garage;

import java.util.List;

public abstract class Race {
    protected long id;
    protected String name;
    protected List<Garage> garages;
    protected List<Car> cars;

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

    public List<Garage> getGarages() {
        return garages;
    }

    public void setGarages(List<Garage> garages) {
        this.garages = garages;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
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
