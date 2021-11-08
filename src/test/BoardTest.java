import game.Board;
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
}
