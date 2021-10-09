public class Rook extends Piece{
    private int rank;
    private int file;
    private boolean color;

    public Rook(boolean color, int file, int rank){
        super(color, file, rank);
    }

    public int getRank(){
        return super.getRank();
    }

    public int getFile(){
        return super.getFile();
    }

    public boolean getColor(){
        return super.getColor();
    }

    @Override
    public int[][] getValidMoves(){
        //TODO valid rook moves involve just adding/subtracting from rank and file
        return null;
    }
}
