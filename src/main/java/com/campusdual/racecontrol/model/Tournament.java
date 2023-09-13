package com.campusdual.racecontrol.model;

import com.campusdual.racecontrol.model.Race.Race;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tournament {
    //A tournaments is a collection of faces

    private long id;
    private String name;
    private List<Race> races = new ArrayList<>();

    private List<Garage> garages = new ArrayList<>();

    private Map<ScoreCar, Integer> scoreboard = new HashMap<>();

    public Tournament(long id) {
        this.id = id;
    }

    public Tournament(long id, String name) {
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

    public List<Race> getRaces() {
        return races;
    }

    public void setRaces(List<Race> races) {
        this.races = races;
    }

    public List<Garage> getGarages() {
        return garages;
    }

    public void setGarages(List<Garage> garages) {
        this.garages = garages;
    }

    public Map<ScoreCar, Integer> getScoreboard() {
        return scoreboard;
    }

    public void setScoreboard(Map<ScoreCar, Integer> scoreboard) {
        this.scoreboard = scoreboard;
    }

    @Override
    public String toString() {
        return "Tournament{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", races=" + races +
                '}';
    }
}
