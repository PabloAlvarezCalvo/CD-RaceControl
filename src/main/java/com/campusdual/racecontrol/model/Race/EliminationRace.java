package com.campusdual.racecontrol.model.Race;

import org.json.simple.JSONObject;

import java.util.ArrayList;

public class EliminationRace extends Race {
    /*
     * Elimination races have preliminar warm up minutes, after that, the last car will be removed every minute
     * until only one remains
     */
    private int warmUpTime;

    public EliminationRace(String name) {
        super(name);
        id = generateId();
    }

    public int getWarmUpTime() {
        return warmUpTime;
    }

    public void setWarmUpTime(int warmUpTime) {
        this.warmUpTime = warmUpTime;
    }

    @Override
    public void startRace() {
        /*TODO*/
    }


    @Override
    public JSONObject exportRace() {
        return null;
    }
}
