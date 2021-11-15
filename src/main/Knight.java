import java.util.ArrayList;
import java.util.HashMap;

public class Knight extends Piece{
    private final int[] offsets = {-17, -15, -10, -6, 6, 10, 15, 17};
    private final int[][] knightMoves = {{1, 2}, {1, 2}, {2, 1}, {2, 1}, {2, 1}, {2, 1}, {1, 2}, {1, 2}};
    private final int[][] checkSquares = {{3, 1}, {4, 1}, {3, 1}, {4, 1}, {3, 6}, {4, 6}, {3, 6}, {4, 6}};


    public Knight(boolean color, int file, int rank){
        super(color, file, rank);
    }

    public int getRank(){
        return super.getRank();
    }

    public int getFile(){
        return super.getFile();
    }

    public boolean getNotMoved(){
        return super.getNotMoved();
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
        ArrayList<Integer> temp = new ArrayList<Integer>();
        for (int i = 0; i < 8; i++){
            if (Utils.NUMSQUARESTOEDGE[getPos()][checkSquares[i][0]] >= knightMoves[i][0] &&
                    Utils.NUMSQUARESTOEDGE[getPos()][checkSquares[i][1]] >= knightMoves[i][1]){
                temp.add(getPos() + offsets[i]);
            }
        }
        return temp.stream().mapToInt(i -> i).toArray();
    }

    @Override
    public void updatePosition(int move) {
        super.updatePosition(move);
    }
}
