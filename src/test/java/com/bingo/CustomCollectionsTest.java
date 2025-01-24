package com.bingo;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CustomCollectionsTest {

    @Test
    void shuffle_when_ArrayReceived_then_ArrayDoesNotLoseNumbers() {
        final short[] original = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        final short[] shuffled = Arrays.copyOf(original, original.length);

        CustomCollections.shuffle(shuffled);

        final Set<Short> originalSet = new HashSet<>();
        final Set<Short> shuffledSet = new HashSet<>();

        for (short num : original) originalSet.add(num);
        for (short num : shuffled) shuffledSet.add(num);

        assertEquals(originalSet, shuffledSet, "Shuffled array should contain the same elements");
    }

    @Test
    void shuffle_when_ArrayNumbersReceived_then_ArrayNumbersShouldNotHaveSameOrder() {
        final short[] original = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        final short[] shuffled = Arrays.copyOf(original, original.length);

        CustomCollections.shuffle(shuffled);

        assertNotEquals(Arrays.toString(original), Arrays.toString(shuffled));
    }

    @Test
    void reverse_when_ArrayNumbersReceived_thenArrayNumbersIsReversed() {
        final short[] original = {1, 2, 3, 4, 5};
        final short[] expected = {5, 4, 3, 2, 1};

        CustomCollections.reverse(original);

        assertArrayEquals(expected, original);
    }

    @Test
    void reverse_when_ArrayOfOneElement_then_DoesNothing() {
        final short[] array = {42};
        final short[] expected = {42};

        CustomCollections.reverse(array);

        assertArrayEquals(expected, array);
    }

    @Test
    void reverse_when_EmptyArray_thenDoesNothing() {
        final short[] array = {};
        final short[] expected = {};

        CustomCollections.reverse(array);

        assertArrayEquals(expected, array);
    }

    @Test
    void reverse_when_NullReceived_then_DoesNotThrowAnyException() {
        assertDoesNotThrow(() -> CustomCollections.reverse(null));
    }
}
