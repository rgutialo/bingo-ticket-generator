package com.bingo;

import java.util.Random;

public class CustomCollections {

    private CustomCollections() {
        throw new IllegalStateException("Utility class");
    }

    public static void shuffle(short[] array) {
        Random rand = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            short temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    public static void reverse(short[] array) {
        if (array == null || array.length <= 1) {
            return;
        }

        int left = 0, right = array.length - 1;
        while (left < right) {
            short temp = array[left];
            array[left] = array[right];
            array[right] = temp;

            left++;
            right--;
        }
    }
}
