package com.campusdual.racecontrol.model.Race;

public class EliminationRace extends Race {
    /*
     * Elimination races have preliminar warm up minutes, after that, the last car will be removed every minute
     * until only one remains
     */

    public EliminationRace(long id, String name) {
        super(id, name);
    }
}
