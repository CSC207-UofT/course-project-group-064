public class Rook extends Piece{
    private int rank;
    private int file;
    private boolean color;
    private int pos;

    public Rook(boolean color, int file, int rank){
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

    @Override
    public int[] getValidMoves(){
        //TODO valid rook moves involve just adding/subtracting from rank and file
        return null;
    }

    @Override
    public void updatePosition(int move) {
        super.updatePosition(move);
    }
}
