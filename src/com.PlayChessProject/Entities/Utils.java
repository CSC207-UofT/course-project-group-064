package Entities;

public class Utils {
    public static final int[][] NUMSQUARESTOEDGE = fillNumSquares();

    /**
     * Fills the number of squares to the edge of the board in each direction from each square. Runs on startup
     * and stores list for use throughout play. Used to calculate all piece move legality.
     * @return 2d array [i][j] where first index is the square on the board and second corresponds to a direction of the
     * number of squares to the edge of the board from i in direction j is the returned integer.
     */
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

    /**
     * Array containment helper
     * @param array any integer array
     * @param num number checked for containment
     * @return true if number is in the array and false otherwise.
     */
    public static boolean contains(int[] array, int num)
    {
        for (int token : array){
            if (token == num){
                return true;
            }
        }
        return false;
    }

    /**0, .5, 1 are possible ints passed in
     * 0 if player 1 lost
     * .5 if draw
     * 1 if player 1 won
     * */
    public static void calculateElo(double result, User white, User black) {
        // calculate change in elo for winner and loser
        // winner and loser elo
        int p1Elo = white.getElo();
        int p2Elo = black.getElo();

        //Player 1
        double expected = adjustedDifference(p1Elo, p2Elo);
        double finalp1Elo = p1Elo + white.getkFactor() * (result - adjustedDifference(p1Elo, p2Elo));
        white.setElo((int)finalp1Elo);

        //Player 2
        double finalp2Elo = p2Elo + black.getkFactor() * (1-result - adjustedDifference(p2Elo, p1Elo));
        black.setElo((int)finalp2Elo);
    }

    /**
     * Calculates the expected score of a game based on the players' respective elos.
     * @param elo1 elo of the first player
     * @param elo2 elo of second player
     * @return double representing expected score.
     */
    public static double adjustedDifference(double elo1, double elo2) {
        double exp1 = elo1/400.0;
        double exp2 = elo2/400.0;
        double num = Math.pow(10, exp1);
        double den1 = Math.pow(10, exp1);
        double den2 = Math.pow(10, exp2);
        double den = den1+den2;
        return num / den;
    }
}
