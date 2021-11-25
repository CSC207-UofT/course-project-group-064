public abstract class Piece {
    private int rank; //The y coordinate of the piece on the board
    private int file; //The x coordinate of the piece on the board
    private int pos;
    private boolean color;
    private boolean not_moved;
    private int[] offsets;


    public Piece(boolean color, int file, int rank){
        this.color = color;
        this.rank = rank;
        this.file = file;
        this.pos = 8 * (7-rank) + file;
        this.not_moved = true;
    }

    //Used to determine whether a king or rook can castle.
    public boolean getNotMoved(){
        return not_moved;
    }
    public int getRank(){
        return rank;
    }

    public int getFile(){
        return file;
    }

    //Translates rank, file notation to a 0-63 single integer representation of each square
    public int getPos() { return pos; }

    public boolean getColor(){
        return color;
    }

    public int[] getValidMoves(){
        return null;
    }

    public void updatePosition(int move){
        file = move % 8;
        rank = 7 - ((move - file) / 8);
        pos = move;
        not_moved = false;
    }
}
