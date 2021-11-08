package game;

import java.lang.Math.*;
import java.util.HashMap;

public class Utils {
    public static final int[][] NUMSQUARESTOEDGE = fillNumSquares();

    public static int[][] fillNumSquares(){
        int[][] ret = new int[64][];
        for (int file = 0; file < 8; file++){
            for (int rank = 0; rank < 8; rank++){
                int num_south = rank;
                int num_north = 7-rank;
                int num_west = file;
                int num_east = 7-file;

                int square_index = 8 * (7-rank) + file;

                ret[square_index] = new int[]{
                        Math.min(num_north, num_west), //Up Left = 0
                        num_north,                     //Up Middle = 1
                        Math.min(num_north, num_east), //Up Right = 2
                        num_west,                      //Left = 3
                        num_east,                      //Right = 4
                        Math.min(num_south, num_west), //Down Left = 5
                        num_south,                     //Down Middle = 6
                        Math.min(num_south, num_east)  //Down Right = 7
                };

            }
        }
        return ret;
    }
}
