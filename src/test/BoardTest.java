import Entities.Board;
import Entities.King;
import Entities.Rook;
import org.junit.Test;

import static org.junit.Assert.*;


public class BoardTest {
    Board board = new Board("Standard");
    @Test(timeout = 50)
    public void TestPieceMove(){
        board.makePlayerMove(52, 36);
        assertEquals(36, board.getPiecePositions().get(36).getPos());
    }

    @Test(timeout = 50)
    public void TestCapture(){
        board.makePlayerMove(12, 28);
        board.makePlayerMove(62, 45);
        board.makePlayerMove(6, 21);
        board.makePlayerMove(45, 28);
        assertEquals(28, board.getPiecePositions().get(28).getPos());
    }

    @Test(timeout = 50)
    public void TestGetMoves(){
        int[][] moves = board.getLegalMoves(true);
        int[][] equalArray = {{48, 40, 32}, {49, 41, 33}, {50, 42, 34}, {51, 43, 35}, {52, 44, 36}, {53, 45, 37},
                {54, 46, 38}, {55, 47, 39}, {56}, {57, 40, 42}, {58}, {59}, {60}, {61}, {62, 45, 47}, {63}};
        assertArrayEquals(moves, equalArray);
        board.makePlayerMove(52, 36);
    }

    @Test(timeout = 150)
    public void TestLegal(){
        assertEquals(Board.LEGAL, board.makePlayerMove(52, 36));
        assertEquals(Board.LEGAL, board.makePlayerMove(12, 28));
        assertEquals(Board.LEGAL, board.makePlayerMove(62, 45));
        assertEquals(Board.ILLEGAL, board.makePlayerMove(28, 20));
        assertEquals(Board.ILLEGAL, board.makePlayerMove(60, 52));
        assertEquals(Board.ILLEGAL, board.makePlayerMove(4, 13));
        assertEquals(Board.LEGAL, board.makePlayerMove(1, 18));
        assertEquals(Board.LEGAL, board.makePlayerMove(61, 34));
        assertEquals(Board.LEGAL, board.makePlayerMove(6, 21));
        assertEquals(Board.LEGAL, board.makePlayerMove(45, 30));
        assertEquals(Board.LEGAL, board.makePlayerMove(11, 27));
        assertEquals(Board.LEGAL, board.makePlayerMove(36, 27));
        assertEquals(Board.LEGAL, board.makePlayerMove(21, 27));
        assertEquals(Board.LEGAL, board.makePlayerMove(30, 13));
        assertEquals(Board.LEGAL, board.makePlayerMove(4, 13));
        assertEquals(Board.LEGAL, board.makePlayerMove(59, 45));
        assertEquals(Board.ILLEGAL, board.makePlayerMove(13, 21));
        assertEquals(Board.ILLEGAL, board.makePlayerMove(27, 44));
        assertEquals(Board.ILLEGAL, board.makePlayerMove(5, 26));
        assertEquals(Board.LEGAL, board.makePlayerMove(13, 6));
        assertEquals(Board.ILLEGAL, board.makePlayerMove(5, 26));
        assertEquals(Board.LEGAL, board.makePlayerMove(34, 27));
        assertEquals(Board.LEGAL, board.makePlayerMove(2,20));
        assertEquals(Board.CHECKMATE, board.makePlayerMove(27, 20));
    }

    @Test(timeout = 150)
    public void TestGame(){
        assertEquals(Board.LEGAL, board.makePlayerMove(52, 36));
        assertEquals(Board.LEGAL, board.makePlayerMove(12, 28));
        assertEquals(Board.LEGAL, board.makePlayerMove(62, 45));
        assertEquals(Board.ILLEGAL, board.makePlayerMove(28, 20));
        assertEquals(Board.ILLEGAL, board.makePlayerMove(60, 52));
        assertEquals(Board.ILLEGAL, board.makePlayerMove(4, 13));
        assertEquals(Board.LEGAL, board.makePlayerMove(1, 18));
        assertEquals(Board.LEGAL, board.makePlayerMove(61, 34));
        assertEquals(Board.LEGAL, board.makePlayerMove(6, 21));
        assertEquals(Board.LEGAL, board.makePlayerMove(45, 30));
        assertEquals(Board.LEGAL, board.makePlayerMove(11, 27));
        assertEquals(Board.LEGAL, board.makePlayerMove(36, 27));
        assertEquals(Board.LEGAL, board.makePlayerMove(21, 27));
        assertEquals(Board.LEGAL, board.makePlayerMove(30, 13));
        assertEquals(Board.LEGAL, board.makePlayerMove(4, 13));
        assertEquals(Board.LEGAL, board.makePlayerMove(59, 45));
        assertEquals(Board.ILLEGAL, board.makePlayerMove(13, 21));
        assertEquals(Board.ILLEGAL, board.makePlayerMove(27, 44));
        assertEquals(Board.ILLEGAL, board.makePlayerMove(5, 26));
        assertEquals(Board.LEGAL, board.makePlayerMove(13, 6));
        assertEquals(Board.ILLEGAL, board.makePlayerMove(5, 26));
        assertEquals(Board.LEGAL, board.makePlayerMove(34, 27));
        assertEquals(Board.LEGAL, board.makePlayerMove(2,20));
        assertEquals(Board.CHECKMATE, board.makePlayerMove(27, 20));
    }

    @Test(timeout = 150)
    public void TestCastle1(){
        assertEquals(Board.LEGAL, board.makePlayerMove(52, 36));
        assertEquals(Board.LEGAL, board.makePlayerMove(12, 28));
        assertEquals(Board.LEGAL, board.makePlayerMove(53, 37));
        assertEquals(Board.LEGAL, board.makePlayerMove(11, 27));
        assertEquals(Board.LEGAL, board.makePlayerMove(62, 45));
        assertEquals(Board.LEGAL, board.makePlayerMove(1, 18));
        assertEquals(Board.LEGAL, board.makePlayerMove(61, 52));
        assertEquals(Board.LEGAL, board.makePlayerMove(2, 11));
        assertEquals(Board.LEGAL, board.makePlayerMove(60, 62));
        assertEquals(Board.LEGAL, board.makePlayerMove(3, 21));
        assertEquals(Board.LEGAL, board.makePlayerMove(50, 42));
        assertEquals(Board.LEGAL, board.makePlayerMove(4, 2));
        assertTrue(board.getPiecePositions().get(62) instanceof King);
        assertFalse(board.getPiecePositions().get(62).getNotMoved());
        assertTrue(board.getPiecePositions().get(61) instanceof Rook);
        assertFalse(board.getPiecePositions().get(61).getNotMoved());
        assertNull(board.getPiecePositions().get(63));
    }

    @Test(timeout = 150)
    public void TestCastle2(){
        assertEquals(Board.LEGAL, board.makePlayerMove(51, 35));
        assertEquals(Board.LEGAL, board.makePlayerMove(12, 28));
        assertEquals(Board.LEGAL, board.makePlayerMove(58, 44));
        assertEquals(Board.LEGAL, board.makePlayerMove(5, 12));
        assertEquals(Board.LEGAL, board.makePlayerMove(59, 43));
        assertEquals(Board.LEGAL, board.makePlayerMove(6, 21));
        assertEquals(Board.LEGAL, board.makePlayerMove(57, 42));
        assertEquals(Board.LEGAL, board.makePlayerMove(4, 6));
        assertEquals(Board.LEGAL, board.makePlayerMove(60, 58));
        assertTrue(board.getPiecePositions().get(58) instanceof King);
        assertFalse(board.getPiecePositions().get(58).getNotMoved());
        assertTrue(board.getPiecePositions().get(59) instanceof Rook);
        assertFalse(board.getPiecePositions().get(59).getNotMoved());
        assertNull(board.getPiecePositions().get(56));

    }

    @Test(timeout = 150)
    public void TestStalemateGame(){
        board.makePlayerMove(52,36);
        board.makePlayerMove(12, 28);
        board.makePlayerMove(62, 45);
        board.makePlayerMove(1, 18);
        board.makePlayerMove(45, 28);
        board.makePlayerMove(18, 28);
        board.makePlayerMove(51, 35);
        board.makePlayerMove(3, 21);
        board.makePlayerMove(35, 28);
        board.makePlayerMove(21, 28);
        assertEquals(Board.LEGAL, board.makePlayerMove(61, 25));
        board.makePlayerMove(28, 25);
        board.makePlayerMove(59, 11);
        board.makePlayerMove(25, 11);
        assertEquals(Board.LEGAL, board.makePlayerMove(60, 62));
        board.makePlayerMove(5, 26);
        board.makePlayerMove(58, 37);
        board.makePlayerMove(11, 59);
        board.makePlayerMove(61, 59);
        board.makePlayerMove(26, 53);
        board.makePlayerMove(62, 53);
        board.makePlayerMove(2, 38);
        board.makePlayerMove(55, 47);
        board.makePlayerMove(38, 47);
        board.makePlayerMove(54, 47);
        board.makePlayerMove(0, 3);
        board.makePlayerMove(59, 11);
        board.makePlayerMove(6, 21);
        board.makePlayerMove(11, 10);
        board.makePlayerMove(3, 51);
        board.makePlayerMove(53, 45);
        board.makePlayerMove(51, 50);
        board.makePlayerMove(10, 9);
        board.makePlayerMove(50, 49);
        board.makePlayerMove(9, 8);
        board.makePlayerMove(49, 48);
        board.makePlayerMove(56, 48);
        board.makePlayerMove(15, 31);
        board.makePlayerMove(36, 28);
        board.makePlayerMove(14, 30);
        board.makePlayerMove(28, 21);
        board.makePlayerMove(30, 38);
        assertEquals(Board.LEGAL, board.makePlayerMove(47, 38));
        board.makePlayerMove(31, 38);
        board.makePlayerMove(45, 38);
        board.makePlayerMove(7, 31);
        assertEquals(Board.LEGAL, board.makePlayerMove(37, 19));
        board.makePlayerMove(31, 29);
        board.makePlayerMove(19, 12);
        board.makePlayerMove(29, 37);
        assertEquals(Board.STALEMATE, board.makePlayerMove(38, 37));
    }
}
