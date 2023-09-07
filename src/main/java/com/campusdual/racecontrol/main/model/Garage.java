package model;

import java.util.List;

public class Garage {
    private long id;
    private String name;
    private List<Car> cards;

    public Garage(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Garage(long id, String name, List<Car> cards) {
        this.id = id;
        this.name = name;
        this.cards = cards;
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

    public List<Car> getCards() {
        return cards;
    }

    public void setCards(List<Car> cards) {
        this.cards = cards;
    }
}
