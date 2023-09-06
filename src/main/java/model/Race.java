package model;

public class Race {
    private long id;
    private String name;
    private RaceType type;

    public Race(long id, String name, RaceType type) {
        this.id = id;
        this.name = name;
        this.type = type;
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

    public RaceType getType() {
        return type;
    }

    public void setType(RaceType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Race{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
