/**
 * Memento class to create and return board mementos.
 */
public class Memento {
    private Board board;

    /**
     * Memento initializer
     * @param board1 the given board that needs to be a memento.
     */
    public Memento(Board board1) {
        board = board1;
    }

    /**
     * Returns the board saved in this current memento
     * @return the board in this memento
     */
    public Board getSavedBoard() {
        return board;
    }
}
