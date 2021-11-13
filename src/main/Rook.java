import java.util.ArrayList;

public class Rook extends Piece{
    private final int[] offsets = {-8,-1, 1, 8};
    private final int[] checkSquares = {1, 3, 4, 6};

    public Rook(boolean color, int file, int rank){
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

    public boolean getColor(){
        return super.getColor();
    }

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

    @Override
    public void updatePosition(int move) {
        super.updatePosition(move);
    }
}
