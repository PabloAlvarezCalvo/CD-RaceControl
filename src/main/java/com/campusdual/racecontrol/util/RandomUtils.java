package com.campusdual.racecontrol.util;

import java.util.Random;

public class RandomUtils {
    public static Random r = new Random();

    public static int getRandomIntInRange(int min, int max){
        max++;
        return r.nextInt(max - min) + min;
    }
}
