/**
 * Originator class for getting and setting mementos for the undo/redo function.
 */
public class Originator{
    private Board board;

    /**
     * Sets the value for the board by creating a copy of the board.
     * @param board1 the Board that needs to be set to
     */
    public void set(Board board1) {
        Board tempBoard = new Board("Standard");
        tempBoard.copy(board1);
        this.board = tempBoard;
    }

    /**
     * Saves the board state as a memento
     * @return A memento representing the board state
     */
    public Memento storeInMemento() {
        return new Memento(board);
    }

    /**
     *
     * @param memento
     * @return
     */
    public Board restoreFromMemento(Memento memento) {
        board = memento.getSavedBoard();
        return board;
    }
}