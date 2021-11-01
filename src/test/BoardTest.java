import org.junit.*;
import static org.junit.Assert.*;

public class BoardTest {
    Board board = new Board("Standard");
    @Test(timeout = 50)
    public void TestPieceMove(){
        board.makePlayerMove("e2,e4", true);
        assertEquals(36, board.getPiecePositions().get(36).getPos());
    }

    @Test(timeout = 50)
    public void TestCapture(){
        board.makePlayerMove("e7,e5", false);
        board.makePlayerMove("g1,f3", true);
        board.makePlayerMove("g8,f6", false);
        board.makePlayerMove("f3,e5", true);
        assertEquals(28, board.getPiecePositions().get(28).getPos());
    }

    @Test(timeout = 50)
    public void TestSlidingMove(){
        board.makePlayerMove("e2,e4", false);
        board.makePlayerMove("e7,e5", true);
        board.makePlayerMove("d1,f3", false);
        int [] moves = board.getSlidingMoves(45, board.getPiecePositions().get(45));
        assertArrayEquals(new int[]{37, 29, 21, 13, 38, 31, 44, 43, 42, 41, 40, 46, 47, 52, 59}, moves);
        moves = board.getSlidingMoves(0, board.getPiecePositions().get(0));
        assertArrayEquals(new int[] {}, moves);
        board.makePlayerMove("d8,f6", true);
        moves = board.getSlidingMoves(21, board.getPiecePositions().get(21));
        assertArrayEquals(new int[]{12, 3, 20, 19, 18, 17, 16, 22, 23, 29, 37, 45, 30, 39}, moves);
        board.makePlayerMove("a2,a4", false);

    }
}
