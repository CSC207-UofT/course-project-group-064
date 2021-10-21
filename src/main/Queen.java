import java.util.ArrayList;

public class Queen extends Piece{
    private int rank;
    private int file;
    private boolean color;
    private int pos;
    private final int[] offsets = {-9, -8, -7, -1, 1, 7, 8, 9};

    public Queen(boolean color, int file, int rank){
        super(color, file, rank);
    }

    public int getRank(){
        return super.getRank();
    }

    public int getFile(){
        return super.getFile();
    }

    @Override
    public int getPos() {
        return super.getPos();
    }

    public boolean getColor() {
        return super.getColor();
    }

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

    @Override
    public void updatePosition(int move) {
        super.updatePosition(move);
    }
}
