public class Pawn extends Piece{
    private int rank;
    private int file;
    private boolean color;
    private int pos;
    private boolean not_moved;
    private final int[] offsets = {7, 8, 9, 16};

    public Pawn(boolean color, int file, int rank){
        super(color, file, rank);
        if (!color){
            offsets[0] = -7;
            offsets[1] = -8;
            offsets[2] = -9;
            offsets[3] = -16;
        }
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
    public int getPos() {
        return super.getPos();
    }

    @Override
    public boolean getColor() {
        return super.getColor();
    }

    @Override
    public int[] getValidMoves() {
        //TODO pawns can double move on first move and single move after with diagonal capture and en passant
        int[] indecies = color ? new int[]{0, 1, 2} : new int[]{5, 6, 7};
        return null;
    }

    @Override
    public void updatePosition(int move) {
        super.updatePosition(move);
    }
}
