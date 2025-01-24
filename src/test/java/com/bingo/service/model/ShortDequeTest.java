package com.bingo.service.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShortDequeTest {

    @Test
    void shortDeque_when_ShortDequeCreatedWithValues_then_BehaviourIsFIFO() {
        final ShortDeque deque = new ShortDeque(3);
        deque.addLast((short) 1);
        deque.addLast((short) 2);
        deque.addLast((short) 3);

        assertEquals(1, deque.removeFirst());
        assertEquals(2, deque.removeFirst());
        assertEquals(3, deque.removeFirst());
    }

    @Test
    void isEmpty_whenDequeIsCreated_then_IsEmptyReturnsTrueWhenEmptyAndFalseInAnyOtherCase() {
        final ShortDeque deque = new ShortDeque(3);
        assertTrue(deque.isEmpty());

        deque.addLast((short) 1);
        assertFalse(deque.isEmpty());

        deque.removeFirst();
        assertTrue(deque.isEmpty());
    }

    @Test
    void testEnsureCapacity() {
        final ShortDeque deque = new ShortDeque(2);
        deque.addLast((short) 1);
        deque.addLast((short) 2);
        deque.addLast((short) 3); // Should trigger capacity expansion

        assertEquals(1, deque.removeFirst());
        assertEquals(2, deque.removeFirst());
        assertEquals(3, deque.removeFirst());
    }

    @Test
    void testCircularBehavior() {
        final ShortDeque deque = new ShortDeque(3);
        deque.addLast((short) 1);
        deque.addLast((short) 2);
        deque.addLast((short) 3);
        deque.removeFirst(); // Remove 1
        deque.addLast((short) 4); // Insert 4 in circular manner

        assertEquals(2, deque.removeFirst());
        assertEquals(3, deque.removeFirst());
        assertEquals(4, deque.removeFirst());
    }

    @Test
    void fromArray_whenArrayIsReceived_thenDequeIsCreatedAsExpected() {
        final short[] input = {5, 10, 15};
        final ShortDeque deque = ShortDeque.fromArray(input);

        assertEquals(5, deque.removeFirst());
        assertEquals(10, deque.removeFirst());
        assertEquals(15, deque.removeFirst());
    }

    @Test
    void removeFirst_whenDequeIsEmptyAndRemoveFirstIsInvoked_thenIllegalStateExceptionIsRaised() {
        final ShortDeque deque = new ShortDeque(2);
        assertThrows(IllegalStateException.class, deque::removeFirst);
    }
}
