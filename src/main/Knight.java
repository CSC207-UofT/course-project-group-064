public class Knight extends Piece{
    private int rank;
    private int file;
    private boolean color;

    public Knight(boolean color, int file, int rank){
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
    public int[][] getValidMoves(){
        //TODO Knight moves are rank +-1 file +-2 or rank +=2 file +=1
        return null;
    }
}
