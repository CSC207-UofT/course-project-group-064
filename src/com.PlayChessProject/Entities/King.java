package Entities;

import java.util.ArrayList;

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
        ArrayList<Integer> temp = new ArrayList<>();
        for (int i = 0; i < 8; i++){
            if(Utils.NUMSQUARESTOEDGE[getPos()][i] > 0) {
                temp.add(getPos() + offsets[i]);
            }
        }
        return temp.stream().mapToInt(i -> i).toArray();
    }

    @Override
    public void updatePosition(int move) {
        super.updatePosition(move);
        not_moved = false;
    }
}
