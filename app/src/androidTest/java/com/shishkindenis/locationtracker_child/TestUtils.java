package com.shishkindenis.locationtracker_child;

import java.util.Random;

public class TestUtils {
    public static void sleep() {
        try {
            Thread.sleep(5000);
        } catch (
                InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String randomInt() {
        return String.valueOf(((new Random()).nextInt(100000)));
    }
}

