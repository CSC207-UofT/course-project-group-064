import java.util.ArrayList;

public class Queen extends Piece{
    private final int[] offsets = {-9, -8, -7, -1, 1, 7, 8, 9};

    public Queen(boolean color, int file, int rank){
        super(color, file, rank);
    }
    /**
     * Loops over every direction to the edge of the board
     * @return array of valid queen moves
     */

    @Override
    public int[] getValidMoves(){
        ArrayList<Integer> temp = new ArrayList<Integer>();
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < Utils.NUMSQUARESTOEDGE[getPos()][i]; j++){
                temp.add(getPos() + offsets[i] * (j + 1));
            }
        }
        return temp.stream().mapToInt(i -> i).toArray();
    }
}
