public abstract class Piece {
    private int rank; //The y coordinate of the piece on the board
    private int file; //The x coordinate of the piece on the board
    private int pos; //The single integer position corresponding to a specific square on the board
    private boolean color; //The piece's color. white=true, black=false
    private boolean not_moved; //If true can't castle (king/rook) or double move (pawn)
    private int[] offsets; //The cardinal board directions a piece can move. Looped over to get valid moves


    public Piece(boolean color, int file, int rank){
        this.color = color;
        this.rank = rank;
        this.file = file;
        this.pos = 8 * (7-rank) + file;
        this.not_moved = true;
    }

    //Getters
    public boolean getNotMoved(){
        return not_moved;
    }
    public int getRank(){
        return rank;
    }
    public int getFile(){
        return file;
    }
    public int getPos() { return pos; }
    public boolean getColor(){
        return color;
    }

    /**
     * Depending on piece type returns position agnostic possible moves to be later filtered depending on board position.
     * @return integer array of possible destination squares if the piece were to move
     */
    public int[] getValidMoves(){
        return null;
    }

    /**
     * Updates piece's internally tracked position when it is moved on the board.
     * @param move integer index of square the piece has been moved to.
     */
    public void updatePosition(int move){
        file = move % 8;
        rank = 7 - ((move - file) / 8);
        pos = move;
        not_moved = false;
    }
}
