package Entities;

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

    public static boolean contains(int[] array, int num)
    {
        for (int token : array){
            if (token == num){
                return true;
            }
        }
        return false;
    }

    public static void calcElo(User winner, User loser) {
        // calculate change in elo for winner and loser
        // winner and loser elo
        double winnerElo = winner.getElo();
        double loserElo = loser.getElo();
        double finalWinnerElo = winnerElo;
        double finalLoserElo = loserElo;

        //Winner
        //Use inflated k factor for players with fewer than 10 games played
        finalWinnerElo = winnerElo + winner.getkFactor() * (1 - adjustedDifference(winnerElo, loserElo));
        winner.setElo((int)finalWinnerElo);

        //Loser
        finalLoserElo = loserElo + loser.getkFactor() * (0 - adjustedDifference(loserElo, winnerElo));
        loser.setElo((int)finalLoserElo);
    }

    //First Part of Calculation
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
