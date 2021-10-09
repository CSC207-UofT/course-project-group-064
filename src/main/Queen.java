public class Queen extends Piece{
    private int rank;
    private int file;
    private boolean color;

    public Queen(boolean color, int file, int rank){
        super(color, file, rank);
    }

    public int getRank(){
        return super.getRank();
    }

    public int getFile(){
        return super.getFile();
    }

    public boolean getColor() {
        return super.getColor();
    }

    @Override
    public int[][] getValidMoves(){
        //TODO calculate valid queen moves. Should be same basic premise as king but extended.
        return null;
    }
}
