package com.playchessgame.chessgame.Entities;

import java.util.ArrayList;

public class CareTaker {

    // Where all mementos are saved

    ArrayList<Memento> savedBoards = new ArrayList<Memento>();

    // Adds memento to the ArrayList

    public void addMemento(Memento m) { savedBoards.add(m); }

    // Gets the memento requested from the ArrayList

    public Memento getMemento(int index) { return savedBoards.get(index); }
}
