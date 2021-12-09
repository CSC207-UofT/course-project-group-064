package com.playchessgame.chessgame.Entities;

import java.util.ArrayList;

public class King extends Piece {
    private boolean notMoved;
    private final int[] offsets = {-9, -8, -7, -1, 1, 7, 8, 9, -2, 2};

    public King(boolean color, int file, int rank) {
        super(color, file, rank);
        this.notMoved = true;
    }

    /**
     * Checks each direction but only once and adds them to array.
     *
     * @return array of valid king moves
     */
    @Override
    public int[] getValidMoves() {
        ArrayList<Integer> temp = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            if (Utils.NUMSQUARESTOEDGE[getPos()][i] > 0) {
                temp.add(getPos() + offsets[i]);
            }
        }
        return temp.stream().mapToInt(i -> i).toArray();
    }

    @Override
    public void updatePosition(int move) {
        super.updatePosition(move);
        notMoved = false;
    }
}