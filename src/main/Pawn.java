public class Pawn extends Piece{
    private int rank;
    private int file;
    private boolean color;

    public Pawn(boolean color, int file, int rank){
        super(color, file, rank);
    }

    @Override
    public int getRank() {
        return rank;
    }

    @Override
    public int getFile() {
        return file;
    }

    @Override
    public boolean getColor() {
        return super.getColor();
    }

    @Override
    public int[][] getValidMoves() {
        //TODO pawns can double move on first move and single move after with diagonal capture and en passant
        return super.getValidMoves();
    }
}
