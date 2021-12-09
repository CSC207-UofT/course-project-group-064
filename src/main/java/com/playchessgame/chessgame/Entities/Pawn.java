package com.playchessgame.chessgame.Entities;

import java.util.ArrayList;

public class Pawn extends Piece {
    private boolean notMoved = true;
    private final int[] offsets = {-9, -8, -7, -16};
    private int[] indices;

    public Pawn(boolean color, int file, int rank) {
        super(color, file, rank);
        if (!color) {
            offsets[0] = 7;
            offsets[1] = 8;
            offsets[2] = 9;
            offsets[3] = 16;
        }
        this.indices = color ? new int[]{0, 1, 2} : new int[]{5, 6, 7};
    }

    /**
     * Uses notMoved to check if pawn can double move. Adds capture squares which are checked in Board.
     *
     * @return array of valid pawn moves
     */
    @Override
    public int[] getValidMoves() {
        ArrayList<Integer> temp = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            if (Utils.NUMSQUARESTOEDGE[getPos()][indices[i]] >= 1) {
                temp.add(getPos() + offsets[i]);
            }
        }
        if (notMoved) {
            temp.add(getPos() + offsets[3]);
        }
        return temp.stream().mapToInt(i -> i).toArray();
    }

    @Override
    public void updatePosition(int move) {
        super.updatePosition(move);
        notMoved = false;
    }
}