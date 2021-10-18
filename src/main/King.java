public class King extends Piece{
    private boolean not_moved;
    private final int[] offsets = {-9, -8, -7, -1, 1, 7, 8, 9, -2, 2};

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
    public int[] getValidMoves(){
        int castle_square = getColor() ? 60 : 4;
        boolean can_castle = not_moved && castle_square == getPos();
        int offsetLen = can_castle ? 10 : 8;
        int[] ret = new int[offsetLen];
        for (int i = 0; i < 8; i++){
            if(Utils.NUMSQUARESTOEDGE[getPos()][i] > 0) {
                ret[i] = getPos() + offsets[i];
            }
            else {
                ret[i] = -1;
            }
        }
        if (can_castle){
            ret[8] = getPos() + offsets[8];
            ret[9] = getPos() + offsets[9];
        }
        return ret;
    }

    @Override
    public void updatePosition(int move) {
        super.updatePosition(move);
        not_moved = false;
    }
}
