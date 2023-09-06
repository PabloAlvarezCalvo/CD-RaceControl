package model;

public class Car {
    private long id;
    private String brand;
    private String model;
    private Garage owner;

    public Car(int id, String brand, String model, Garage owner) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Garage getOwner() {
        return owner;
    }

    public void setOwner(Garage owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                '}';
    }
}
