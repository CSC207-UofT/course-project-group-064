package com.playchessgame.chessgame.Entities;
import java.util.Stack;

/**
 * Caretaker class in which stacks of the undo and redo boards are saved.
 */
public class Caretaker {
    Stack<Memento> savedBoardsRedo = new Stack<>();
    Stack<Memento> savedBoardsUndo = new Stack<>();

    /**
     * Pushes a memento on the undo stack
     * @param m the memento
     */
    public void addMementoUndo(Memento m) {
        savedBoardsUndo.push(m);
    }

    /**
     * Pushes a memento on the redo stack
     * @param m the memento
     */
    public void addMementoRedo(Memento m) {
        savedBoardsRedo.push(m);
    }

    /**
     * Pops off the memento at the top of the undo stack
     * @return the memento at the top of the undo stack
     */
    public Memento getMementoUndo() {
        return savedBoardsUndo.pop();
    }

    /**
     * Pops off the memento at the top of the redo stack
     * @return the memento at the top of the redo stack
     */
    public Memento getMementoRedo() {
        return savedBoardsRedo.pop();
    }

    /**
     * returns undo Stack
     * @return savedBoardsUndo
     */
    public Stack<Memento> undoStack() {
        return savedBoardsUndo;
    }

    /**
     * returns redo Stack
     * @return savedBoardsRedo
     */
    public Stack<Memento> redoStack() {
        return savedBoardsRedo;
    }

    /**
     * Clears the redo stack
     */
    public void clearRedo() {
        savedBoardsRedo.clear();
    }

}
