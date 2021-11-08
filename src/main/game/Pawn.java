package game;

import java.util.ArrayList;

public class Pawn extends Piece{
    private int rank;
    private int file;
    private boolean color;
    private int pos;
    private boolean not_moved;
    private final int[] offsets = {-9, -8, -7, -16};
    private int[] indecies = new int[3];

    public Pawn(boolean color, int file, int rank){
        super(color, file, rank);
        if (!color){
            offsets[0] = 7;
            offsets[1] = 8;
            offsets[2] = 9;
            offsets[3] = 16;
        }
        this.indecies = color ? new int[]{0, 1, 2} : new int[]{5, 6, 7};
        this.not_moved = true;
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
        ArrayList<Integer> temp = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            if(Utils.NUMSQUARESTOEDGE[getPos()][indecies[i]] >= 1){
                temp.add(getPos() + offsets[i]);
            }
        }
        if (not_moved){temp.add(getPos() + offsets[3]);}
        return temp.stream().mapToInt(i -> i).toArray();
    }

    @Override
    public void updatePosition(int move) {
        super.updatePosition(move);
        not_moved = false;
    }
}
