package com.playchessgame.chessgame.Entities;

import java.util.ArrayList;

public class Knight extends Piece{
    private final int[] offsets = {-17, -15, -10, -6, 6, 10, 15, 17};
    private final int[][] knightMoves = {{1, 2}, {1, 2}, {2, 1}, {2, 1}, {2, 1}, {2, 1}, {1, 2}, {1, 2}};
    private final int[][] checkSquares = {{3, 1}, {4, 1}, {3, 1}, {4, 1}, {3, 6}, {4, 6}, {3, 6}, {4, 6}};


    public Knight(boolean color, int file, int rank){
        super(color, file, rank);
    }

    /**
     * Knights have at most eight legal moves on each turn. We check each using integer offsets to those squares
     * and using checkSquares and knightMoves to prevent the piece jumping from one side of the board to the other.
     * @return array of knight moves.
     */
    @Override
    public int[] getValidMoves(){
        ArrayList<Integer> temp = new ArrayList<>();
        for (int i = 0; i < offsets.length; i++){
            if (Utils.NUMSQUARESTOEDGE[getPos()][checkSquares[i][0]] >= knightMoves[i][0] &&
                    Utils.NUMSQUARESTOEDGE[getPos()][checkSquares[i][1]] >= knightMoves[i][1]){
                temp.add(getPos() + offsets[i]);
            }
        }
        return temp.stream().mapToInt(i -> i).toArray();
    }
}