public class King extends Piece{
    private int rank;
    private int file;
    private boolean color;
    private boolean not_moved;

    public King(boolean color, int file, int rank){
        super(color, file, rank);
        this.not_moved = true;
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
    public int[][] getValidMoves(){
        //TODO calculate valid king moves
        return null;
    }
}
