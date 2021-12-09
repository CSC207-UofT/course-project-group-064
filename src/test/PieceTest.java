import com.playchessgame.chessgame.Entities.*;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


public class PieceTest {

    @Test(timeout = 50)
    public void TestPositionCalculation() {
        Piece piece = new Rook(true, 0, 0);
        assertEquals(56, piece.getPos());
        piece.updatePosition(43);
        assertEquals(2, piece.getRank());
        assertEquals(3, piece.getFile());
        assertEquals(43, piece.getPos());
        piece.updatePosition(12);
        assertEquals(6, piece.getRank());
        assertEquals(4, piece.getFile());
        assertEquals(12, piece.getPos());
    }

    @Test(timeout = 50)
    public void TestKingValidMoves() {
        Piece king = new King(true, 4, 0);
        assertArrayEquals(new int[]{51, 52, 53, 59, 61}, king.getValidMoves());
        king.updatePosition(62);
        assertArrayEquals(new int[]{53, 54, 55, 61, 63}, king.getValidMoves());
        king.updatePosition(63);
        assertArrayEquals(new int[]{54, 55, 62}, king.getValidMoves());
        king.updatePosition(55);
        assertArrayEquals(new int[]{46, 47, 54, 62, 63}, king.getValidMoves());
    }

    @Test(timeout = 50)
    public void TestQueenValidMoves() {
        Piece queen = new Queen(true, 3, 0);
        assertArrayEquals(new int[]{50, 41, 32, 51, 43, 35, 27, 19, 11, 3, 52, 45, 38, 31, 58, 57, 56, 60, 61, 62, 63},
                queen.getValidMoves());
        queen.updatePosition(3);
        assertArrayEquals(new int[]{2, 1, 0, 4, 5, 6, 7, 10, 17, 24, 11, 19, 27, 35, 43, 51, 59, 12, 21, 30, 39},
                queen.getValidMoves());
        queen.updatePosition(0);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 16, 24, 32, 40, 48, 56, 9, 18, 27, 36, 45, 54, 63},
                queen.getValidMoves());
    }

    @Test(timeout = 50)
    public void TestRookValidMoves() {
        Piece rook = new Rook(true, 0, 0);
        assertArrayEquals(new int[]{48, 40, 32, 24, 16, 8, 0, 57, 58, 59, 60, 61, 62, 63}, rook.getValidMoves());
        rook.updatePosition(0);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 16, 24, 32, 40, 48, 56}, rook.getValidMoves());
        rook.updatePosition(3);
        assertArrayEquals(new int[]{2, 1, 0, 4, 5, 6, 7, 11, 19, 27, 35, 43, 51, 59}, rook.getValidMoves());
        rook.updatePosition(35);
        assertArrayEquals(new int[]{27, 19, 11, 3, 34, 33, 32, 36, 37, 38, 39, 43, 51, 59}, rook.getValidMoves());
    }

    @Test(timeout = 50)
    public void TestBishopValidMoves() {
        Piece bishop = new Bishop(true, 2, 0);
        assertArrayEquals(new int[]{49, 40, 51, 44, 37, 30, 23}, bishop.getValidMoves());
        bishop.updatePosition(44);
        assertArrayEquals(new int[]{35, 26, 17, 8, 37, 30, 23, 51, 58, 53, 62}, bishop.getValidMoves());
    }

    @Test(timeout = 50)
    public void TestKnightValidMoves() {
        Piece knight = new Knight(true, 1, 0);
        assertArrayEquals(new int[]{40, 42, 51}, knight.getValidMoves());
        knight.updatePosition(42);
        assertArrayEquals(new int[]{25, 27, 32, 36, 48, 52, 57, 59}, knight.getValidMoves());
    }

    @Test(timeout = 50)
    public void TestPawnValidMoves() {
        Piece wPawn = new Pawn(true, 1, 1);
        Piece bPawn = new Pawn(false, 2, 6);
        assertArrayEquals(new int[]{40, 41, 42, 33}, wPawn.getValidMoves());
        assertArrayEquals(new int[]{17, 18, 19, 26}, bPawn.getValidMoves());
    }
}