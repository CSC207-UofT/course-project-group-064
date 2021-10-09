public abstract class Piece {
    private int rank;
    private int file;
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

    public boolean getColor(){
        return color;
    }

    public int[][] getValidMoves(){
        //Always ensure that rank and file remain less than 8
        return null;
    }
}
