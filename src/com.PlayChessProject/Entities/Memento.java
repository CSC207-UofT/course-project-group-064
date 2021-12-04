package Entities;

public class Memento {
    private Board board;

    public Memento(Board board1) {
        board = board1;
    }

    public Board getSavedBoard() {
        return board;
    }
}
