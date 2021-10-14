public class Bishop extends Piece{
    private int rank;
    private int file;
    private boolean color;

    public Bishop(boolean color, int file, int rank){
        super(color, file, rank);
    }

    public int getRank(){
        return super.getRank();
    }

    public int getFile(){
        return super.getFile();
    }

    @Override
    public int getPos() {
        return super.getPos();
    }

    public boolean getColor(){
        return super.getColor();
    }

    public int[][] getValidMoves(){
        //TODO biship moves are rank +1 file +1 or rank-1 file -1
        return null;
    }
}
