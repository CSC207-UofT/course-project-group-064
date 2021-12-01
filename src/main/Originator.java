public class Originator{

    private Board board;

    // Sets the value for the board

    public void set(Board board1) {
        System.out.println("From Originator: Current Version of board\n"+board1.getPiecePositions()+ "\n");
        this.board = board1;
    }

    // Creates a new Memento with a new board

    public Memento storeInMemento() {
        System.out.println("From Originator: Saving to Memento");
        return new Memento(board);
    }

    // Gets the article currently stored in memento

    public Board restoreFromMemento(Memento memento) {

        board = memento.getSavedBoard();

        System.out.println("From Originator: Previous Article Saved in Memento\n"+board + "\n");

        return board;

    }

}