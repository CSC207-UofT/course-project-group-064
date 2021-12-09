package com.playchessgame.chessgame.Entities;

import java.util.ArrayList;

public class Rook extends Piece {
    private boolean notMoved;
    private final int[] offsets = {-8, -1, 1, 8};
    private final int[] checkSquares = {1, 3, 4, 6};

    public Rook(boolean color, int file, int rank) {
        super(color, file, rank);
    }

    /**
     * Loops over orthogonal directions until edge of board and adds to list.
     *
     * @return array of valid rook moves
     */
    @Override
    public int[] getValidMoves() {
        ArrayList<Integer> temp = new ArrayList<>();
        for (int i = 0; i < offsets.length; i++) {
            for (int j = 0; j < Utils.NUMSQUARESTOEDGE[getPos()][checkSquares[i]]; j++) {
                temp.add(getPos() + offsets[i] * (j + 1));
            }
        }
        return temp.stream().mapToInt(i -> i).toArray();
    }

    /**
     * Updates position and indicates piece has moved
     *
     * @param move integer index of square the piece has been moved to.
     */
    @Override
    public void updatePosition(int move) {
        super.updatePosition(move);
        notMoved = false;
    }
}