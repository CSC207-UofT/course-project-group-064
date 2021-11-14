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
        int [] moves = board.getSlidingMoves(45);
        assertArrayEquals(new int[]{37, 29, 21, 13, 38, 31, 44, 43, 42, 41, 40, 46, 47, 52, 59}, moves);
        moves = board.getSlidingMoves(0);
        assertArrayEquals(new int[] {}, moves);
        board.makePlayerMove("d8,f6", true);
        moves = board.getSlidingMoves(21);
        assertArrayEquals(new int[]{12, 3, 20, 19, 18, 17, 16, 22, 23, 29, 37, 45, 30, 39}, moves);
        board.makePlayerMove("a2,a4", false);
        board.makePlayerMove("a7,a5", true);
        board.makePlayerMove("a1,a3", false);
        moves = board.getSlidingMoves(40);
        assertArrayEquals(new int[]{41, 42, 43, 44, 48, 56}, moves);
        moves = board.getSlidingMoves(5);
        assertArrayEquals(new int[]{12, 19, 26, 33, 40}, moves);
        board.makePlayerMove("f8,a3", true);
        moves = board.getSlidingMoves(40);
        assertArrayEquals(new int[]{33, 26, 19, 12, 5, 49}, moves);
    }

    @Test(timeout = 50)
    public void TestKnightMove(){
        int [] moves = board.getKnightMoves(62);
        assertArrayEquals(new int[]{45, 47}, moves);
        board.makePlayerMove("g1,f3", false);
        moves = board.getKnightMoves(45);
        assertArrayEquals(new int[]{28, 30, 35, 39, 62}, moves);
        board.makePlayerMove("f7,f6", true);
        board.makePlayerMove("e2,e4", false);
        board.makePlayerMove("h7,h6", true);
        moves = board.getKnightMoves(6);
        assertArrayEquals(new int[]{}, moves);
    }

    @Test(timeout = 50)
    public void TestPawnMove(){
        int [] moves = board.getPawnMoves(52);
        assertArrayEquals(new int[]{44, 36}, moves);
        board.makePlayerMove("e2,e4", false);
        moves = board.getPawnMoves(12);
        assertArrayEquals(new int[] {20, 28}, moves);
        board.makePlayerMove("e7,e5", true);
        moves = board.getPawnMoves(36);
        assertArrayEquals(new int []{}, moves);
        board.makePlayerMove("f2,f4", false);
        moves = board.getPawnMoves(28);
        assertArrayEquals(new int[]{37}, moves);
        board.makePlayerMove("e5,f4", true);
        board.makePlayerMove("g2,g4", false);
        moves = board.getPawnMoves(37);
        assertArrayEquals(new int[]{45, 46}, moves);
        moves = board.getPawnMoves(55);
        assertArrayEquals(new int[]{47, 39}, moves);
        moves = board.getPawnMoves(15);
        assertArrayEquals(new int[]{23, 31}, moves);
        board.makePlayerMove("h7,h5", true);
        board.makePlayerMove("e1,e2", false);
        board.makePlayerMove("h5,h4", true);
        board.makePlayerMove("a2,a4", false);
        moves = board.getPawnMoves(39);
        assertArrayEquals(new int[]{47}, moves);
    }

    @Test(timeout = 50)
    public void TestKingMoves(){
        int [] moves = board.getKingMoves(60);
        assertArrayEquals(new int[]{}, moves);
        board.makePlayerMove("e2,e4", false);
        board.makePlayerMove("e7,e5", true);
        moves = board.getKingMoves(60);
        assertArrayEquals(new int[]{52}, moves);
        board.makePlayerMove("e1,e2", false);
        moves = board.getKingMoves(52);
        assertArrayEquals(new int[]{43, 44, 45, 60}, moves);
    }

    @Test(timeout = 50)
    public void TestInCheckBQueenDiag(){
        board.makePlayerMove("f2,f4", true);
        board.makePlayerMove("e7,e5", false);
        board.makePlayerMove("d2,d4", true);
        assertEquals("", board.inCheck(board));
        board.makePlayerMove("d8,h4", false);
        int [] moves = board.getSlidingMoves(39);
        assertEquals("white", board.inCheck(board));
    }

    @Test(timeout = 50)
    public void TestInCheckBbishop(){
        board.makePlayerMove("d2,d4", true);
        board.makePlayerMove("e7,e5", false);
        board.makePlayerMove("e2,e3", true);
        assertEquals("", board.inCheck(board));
        board.makePlayerMove("f8,b4", false);
        assertEquals("white", board.inCheck(board));
    }

    @Test(timeout = 50)
    public void TestInCheckBRook(){
        board.makePlayerMove("e2,e4", true);
        board.makePlayerMove("h7,h5", false);
        board.makePlayerMove("e4,e5", true);
        board.makePlayerMove("h8,h6", false);
        board.makePlayerMove("e5,e6", true);
//        assertEquals("", board.inCheck(board));
        board.makePlayerMove("h6,e6", false);
        assertEquals("white", board.inCheck(board));
    }

    @Test(timeout = 50)
    public void TestInCheckBknight(){
        board.makePlayerMove("d2,d4", true);
        board.makePlayerMove("b8,a6", false);
        board.makePlayerMove("e2,e3", true);
        board.makePlayerMove("a6,b4", false);
        assertEquals("", board.inCheck(board));
        board.makePlayerMove("e3,e4", true);
        board.makePlayerMove("b4,c2", false);
        assertEquals("white", board.inCheck(board));
    }

    @Test(timeout = 50)
    public void TestInCheckWQueenDiag(){
        board.makePlayerMove("e2,e4", true);
        board.makePlayerMove("f7,f5", false);
        assertEquals("", board.inCheck(board));
        board.makePlayerMove("d1,h5", true);
        assertEquals("black", board.inCheck(board));
    }

    @Test(timeout = 50)
    public void TestInCheckWPawn(){
        board.makePlayerMove("e2,e4", true);
        board.makePlayerMove("f7,f5", false);
        board.makePlayerMove("e4,f5", true);
        board.makePlayerMove("h7,h6", false);
        board.makePlayerMove("f5,f6", true);
        board.makePlayerMove("h6,h5", false);
        assertEquals("", board.inCheck(board));
        board.makePlayerMove("f6,f7", true);
        assertEquals("black", board.inCheck(board));
    }

    //TODO remove King checks when checkMoveLegal is implemented
    @Test(timeout = 50)
    public void TestInCheckBPawnKing(){
        board.makePlayerMove("d2,d4", true);
        board.makePlayerMove("e7,e5", false);
        board.makePlayerMove("e1,d2", true);
        board.makePlayerMove("e8,e7", false);
        board.makePlayerMove("d2,e3", true);
        assertEquals("", board.inCheck(board));
        board.makePlayerMove("e5,d4", false);
        assertEquals("white", board.inCheck(board));
        board.makePlayerMove("e3,d4", false);
        board.makePlayerMove("e7,e6", false);
        assertEquals("", board.inCheck(board));
        board.makePlayerMove("d4,d5", false);
        //black in check because it checks for the black king first
        assertEquals("black", board.inCheck(board));
        //check side by side kings
        board.makePlayerMove("e6,d6", false);
        assertEquals("black", board.inCheck(board));
    }

    @Test(timeout = 50)
    public void TestInCheckWhiteRNB(){
        board.makePlayerMove("a2,a4", true);
        board.makePlayerMove("e7,e5", false);
        board.makePlayerMove("a1,a3", true);
        board.makePlayerMove("e5,e4", false);
        board.makePlayerMove("a4,a5", true);
        board.makePlayerMove("e4,e3", false);
        assertEquals("", board.inCheck(board));
        board.makePlayerMove("a3,e3", true);
        assertEquals("black", board.inCheck(board));
        board.makePlayerMove("d8,e7", false);
        board.makePlayerMove("e3,h3", true);
        board.makePlayerMove("d7,d6", false);
        board.makePlayerMove("e2,e3", true);
        board.makePlayerMove("h7,h6", false);
        assertEquals("", board.inCheck(board));
        board.makePlayerMove("f1,b5", true);
        assertEquals("black", board.inCheck(board));
        board.makePlayerMove("c7,c6", false);
        board.makePlayerMove("b1,c3", true);
        board.makePlayerMove("h6,h5", false);
        board.makePlayerMove("c3,d5", true);
        board.makePlayerMove("h5,h4", false);
        assertEquals("", board.inCheck(board));
        board.makePlayerMove("d5,c7", true);
        assertEquals("black", board.inCheck(board));
    }
}
