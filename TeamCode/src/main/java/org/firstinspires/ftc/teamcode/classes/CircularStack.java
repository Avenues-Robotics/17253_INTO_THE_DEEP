package org.firstinspires.ftc.teamcode.classes;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class CircularStack<T> {
    private Deque<T> deque = new ArrayDeque<>();
    private int maxSize;

    public CircularStack(int maxSize){
        this.maxSize = maxSize;
    }

    public void push(T object){
        deque.push(object);
        while (deque.size() > maxSize){
            deque.removeLast();
        }
    }

    public Boolean isEmpty() {
        return deque.isEmpty();
    }

    public List<T> grab() {
        return new ArrayList<>(deque);
    }

    public void clear() {
        deque.clear();
    }
}
