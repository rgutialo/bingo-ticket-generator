package com.bingo.service.model;

/**
 * Implementation of a FIFO deque for short (primitive) values.
 */
public class ShortDeque {
    private short[] data;
    private int front, rear, size;


    public ShortDeque(int capacity) {
        data = new short[capacity];
        front = rear = size = 0;
    }

    public void addLast(short value) {
        ensureCapacity();
        data[rear] = value;
        rear = (rear + 1) % data.length;
        size++;
    }

    public short removeFirst() {
        if (size == 0) throw new IllegalStateException("Operation not allowed: Deque is empty");
        short value = data[front];
        front = (front + 1) % data.length;
        size--;
        return value;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void ensureCapacity() {
        if (size == data.length) {
            int newCapacity = data.length * 2;
            short[] newData = new short[newCapacity];
            for (int i = 0; i < size; i++) {
                newData[i] = data[(front + i) % data.length];
            }
            data = newData;
            front = 0;
            rear = size;
        }
    }

    public static ShortDeque fromArray(short[] array) {
        ShortDeque deque = new ShortDeque(array.length);
        for (short value : array) deque.addLast(value);
        return deque;
    }
}