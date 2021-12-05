package com.playchessgame.chessgame.Entities;

import java.util.ArrayList;

public class Bishop extends Piece{
    private final int[] offsets = {-9, -7, 7, 9};
    private final int[] checkSquares = {0, 2, 5, 7};

    public Bishop(boolean color, int file, int rank){
        super(color, file, rank);
    }

    /**
     * Loops over the four diagonal directions until the edge of the board.
     * @return array of valid bishop moves
     */
    @Override
    public int[] getValidMoves(){
        ArrayList<Integer> temp = new ArrayList<>();
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < Utils.NUMSQUARESTOEDGE[getPos()][checkSquares[i]]; j++){
                temp.add(getPos() + offsets[i] * (j + 1));
            }
        }
        return temp.stream().mapToInt(i -> i).toArray();
    }
}