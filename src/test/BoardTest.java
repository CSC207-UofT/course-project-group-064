import org.junit.*;
import static org.junit.Assert.*;

public class BoardTest {
    Board board = new Board("Standard");
    @Test(timeout = 50)
    public void TestPieceMove(){
        board.makePlayerMove("e2,e4");
        assertEquals(36, board.getPiecePositions().get(36).getPos());
    }

    @Test(timeout = 50)
    public void TestCapture(){
        board.makePlayerMove("e7,e5");
        board.makePlayerMove("g1,f3");
        board.makePlayerMove("g8,f6");
        board.makePlayerMove("f3,e5");
        assertEquals(28, board.getPiecePositions().get(28).getPos());
    }

    @Test(timeout = 50)
    public void TestGetMoves(){
        int[][] moves = board.getLegalMoves();
        int[][] equalArray = {{48, 40, 32}, {49, 41, 33}, {50, 42, 34}, {51, 43, 35}, {52, 44, 36}, {53, 45, 37},
                {54, 46, 38}, {55, 47, 39}, {56}, {57, 40, 42}, {58}, {59}, {60}, {61}, {62, 45, 47}, {63}};
        assertArrayEquals(moves, equalArray);
    }

    @Test(timeout = 50)
    public void TestLegal(){
        assertTrue(board.makePlayerMove("e2,e4"));
        assertTrue(board.makePlayerMove("e7,e5"));
        assertTrue(board.makePlayerMove("g1,f3"));
        assertFalse(board.makePlayerMove("e5,e6"));
        assertFalse(board.makePlayerMove("e1,e2"));
        assertFalse(board.makePlayerMove("e8,f7"));
        assertTrue(board.makePlayerMove("b8,c6"));
    }

    @Test(timeout = 50)
    public void TestSlidingMove(){
        board.makePlayerMove("e2,e4");
        board.makePlayerMove("e7,e5");
        board.makePlayerMove("d1,f3");
        int [] moves = board.getSlidingMoves(45);
        assertArrayEquals(new int[]{37, 29, 21, 13, 38, 31, 44, 43, 42, 41, 40, 46, 47, 52, 59}, moves);
        moves = board.getSlidingMoves(0);
        assertArrayEquals(new int[] {}, moves);
        board.makePlayerMove("d8,f6");
        moves = board.getSlidingMoves(21);
        assertArrayEquals(new int[]{12, 3, 20, 19, 18, 17, 16, 22, 23, 29, 37, 45, 30, 39}, moves);
        board.makePlayerMove("a2,a4");
        board.makePlayerMove("a7,a5");
        board.makePlayerMove("a1,a3");
        moves = board.getSlidingMoves(40);
        assertArrayEquals(new int[]{41, 42, 43, 44, 48, 56}, moves);
        moves = board.getSlidingMoves(5);
        assertArrayEquals(new int[]{12, 19, 26, 33, 40}, moves);
        board.makePlayerMove("f8,a3");
        moves = board.getSlidingMoves(40);
        assertArrayEquals(new int[]{33, 26, 19, 12, 5, 49}, moves);
    }

    @Test(timeout = 50)
    public void TestKnightMove(){
        int [] moves = board.getKnightMoves(62);
        assertArrayEquals(new int[]{45, 47}, moves);
        board.makePlayerMove("g1,f3");
        moves = board.getKnightMoves(45);
        assertArrayEquals(new int[]{28, 30, 35, 39, 62}, moves);
        board.makePlayerMove("f7,f6");
        board.makePlayerMove("e2,e4");
        board.makePlayerMove("h7,h6");
        moves = board.getKnightMoves(6);
        assertArrayEquals(new int[]{}, moves);
    }

    @Test(timeout = 50)
    public void TestPawnMove(){
        int [] moves = board.getPawnMoves(52);
        assertArrayEquals(new int[]{44, 36}, moves);
        board.makePlayerMove("e2,e4");
        moves = board.getPawnMoves(12);
        assertArrayEquals(new int[] {20, 28}, moves);
        board.makePlayerMove("e7,e5");
        moves = board.getPawnMoves(36);
        assertArrayEquals(new int []{}, moves);
        board.makePlayerMove("f2,f4");
        moves = board.getPawnMoves(28);
        assertArrayEquals(new int[]{37}, moves);
        board.makePlayerMove("e5,f4");
        board.makePlayerMove("g2,g4");
        moves = board.getPawnMoves(37);
        assertArrayEquals(new int[]{45, 46}, moves);
        moves = board.getPawnMoves(55);
        assertArrayEquals(new int[]{47, 39}, moves);
        moves = board.getPawnMoves(15);
        assertArrayEquals(new int[]{23, 31}, moves);
        board.makePlayerMove("h7,h5");
        board.makePlayerMove("e1,e2");
        board.makePlayerMove("h5,h4");
        board.makePlayerMove("a2,a4");
        moves = board.getPawnMoves(39);
        assertArrayEquals(new int[]{47}, moves);
    }

    @Test(timeout = 50)
    public void TestKingMoves(){
        int [] moves = board.getKingMoves(60);
        assertArrayEquals(new int[]{}, moves);
        board.makePlayerMove("e2,e4");
        board.makePlayerMove("e7,e5");
        moves = board.getKingMoves(60);
        assertArrayEquals(new int[]{52}, moves);
        board.makePlayerMove("e1,e2");
        moves = board.getKingMoves(52);
        assertArrayEquals(new int[]{43, 44, 45, 60}, moves);
    }

    @Test(timeout = 50)
    public void TestInCheckBQueenDiag(){
        board.makePlayerMove("f2,f4");
        board.makePlayerMove("e7,e5");
        board.makePlayerMove("d2,d4");
        assertFalse(board.inCheck());
        board.makePlayerMove("d8,h4");
        int [] moves = board.getSlidingMoves(39);
        assertTrue(board.inCheck());
    }

    @Test(timeout = 50)
    public void TestInCheckBbishop(){
        board.makePlayerMove("d2,d4");
        board.makePlayerMove("e7,e5");
        board.makePlayerMove("e2,e3");
        assertFalse(board.inCheck());
        board.makePlayerMove("f8,b4");
        assertTrue(board.inCheck());
    }

    @Test(timeout = 50)
    public void TestInCheckBRook(){
        board.makePlayerMove("e2,e4");
        board.makePlayerMove("h7,h5");
        board.makePlayerMove("e4,e5");
        board.makePlayerMove("h8,h6");
        board.makePlayerMove("e5,e6");
//        assertEquals("", board.inCheck(board));
        board.makePlayerMove("h6,e6");
        assertTrue(board.inCheck());
    }

    @Test(timeout = 50)
    public void TestInCheckBknight(){
        board.makePlayerMove("d2,d4");
        board.makePlayerMove("b8,a6");
        board.makePlayerMove("e2,e3");
        board.makePlayerMove("a6,b4");
        assertFalse(board.inCheck());
        board.makePlayerMove("e3,e4");
        board.makePlayerMove("b4,c2");
        assertTrue(board.inCheck());
    }

    @Test(timeout = 50)
    public void TestInCheckWQueenDiag(){
        board.makePlayerMove("e2,e4");
        board.makePlayerMove("f7,f5");
        assertFalse(board.inCheck());
        board.makePlayerMove("d1,h5");
        assertTrue(board.inCheck());
    }

    @Test(timeout = 50)
    public void TestInCheckWPawn(){
        board.makePlayerMove("e2,e4");
        board.makePlayerMove("f7,f5");
        board.makePlayerMove("e4,f5");
        board.makePlayerMove("h7,h6");
        board.makePlayerMove("f5,f6");
        board.makePlayerMove("h6,h5");
        assertFalse(board.inCheck());
        board.makePlayerMove("f6,f7");
        assertTrue(board.inCheck());
    }

    //TODO remove King checks when checkMoveLegal is implemented
    @Test(timeout = 50)
    public void TestInCheckBPawnKing(){
        board.makePlayerMove("d2,d4");
        board.makePlayerMove("e7,e5");
        board.makePlayerMove("e1,d2");
        board.makePlayerMove("e8,e7");
        board.makePlayerMove("d2,e3");
        assertFalse(board.inCheck());
        board.makePlayerMove("e5,d4");
        assertTrue(board.inCheck());
        board.makePlayerMove("e3,d4");
        board.makePlayerMove("e7,e6");
        assertFalse(board.inCheck());
        board.makePlayerMove("d4,d5");
        //black in check because it checks for the black king first
        assertTrue(board.inCheck());
        //check side by side kings
        board.makePlayerMove("e6,d6");
        assertTrue(board.inCheck());
    }

    @Test(timeout = 50)
    public void TestInCheckWhiteRNB(){
        board.makePlayerMove("a2,a4");
        board.makePlayerMove("e7,e5");
        board.makePlayerMove("a1,a3");
        board.makePlayerMove("e5,e4");
        board.makePlayerMove("a4,a5");
        board.makePlayerMove("e4,e3");
        assertFalse(board.inCheck());
        board.makePlayerMove("a3,e3");
        assertTrue(board.inCheck());
        board.makePlayerMove("d8,e7");
        board.makePlayerMove("e3,h3");
        board.makePlayerMove("d7,d6");
        board.makePlayerMove("e2,e3");
        board.makePlayerMove("h7,h6");
        assertFalse(board.inCheck());
        board.makePlayerMove("f1,b5");
        assertTrue(board.inCheck());
        board.makePlayerMove("c7,c6");
        board.makePlayerMove("b1,c3");
        board.makePlayerMove("h6,h5");
        board.makePlayerMove("c3,d5");
        board.makePlayerMove("h5,h4");
        assertFalse(board.inCheck());
        board.makePlayerMove("d5,c7");
        assertTrue(board.inCheck());
    }

//    @Test(timeout = 50)
//    public void TestCastleWhite(){
//        board.makePlayerMove("e2,e4");
//        board.makePlayerMove("f7,f5");
//        board.makePlayerMove("g1,f3");
//
//        board.makePlayerMove("h7,h6");
//        board.makePlayerMove("f5,f6");
//        board.makePlayerMove("h6,h5");
//        board.makePlayerMove("f6,f7");
//    }
}
