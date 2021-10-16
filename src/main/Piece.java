public abstract class Piece {
    private int rank; //The y coordinate of the piece on the board
    private int file; //The x coordinate of the piece on the board
    private boolean color;

    public Piece(boolean color, int file, int rank){
        this.color = color;
        this.rank = rank;
        this.file = file;
    }

    public int getRank(){
        return rank;
    }

    public int getFile(){
        return file;
    }

    //Translates rank, file notation to a 0-63 single integer representation of each square
    public int getPos() { return (7 - rank) * 8 + file; }

    public boolean getColor(){
        return color;
    }

    public int[][] getValidMoves(){
        //Always ensure that rank and file remain less than 8
        return null;
    }

    public void updatePosition(int move){
        file = move % 8;
        rank = 7 - ((move - file) / 8);
    }
}
