import org.junit.*;
import static org.junit.Assert.*;

public class PieceTest {

    @Test(timeout = 50)
    public void TestPositionCalculation(){
        Piece piece = new Rook(true, 0, 0 );
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
    public void TestKingValidMoves(){
        Piece king = new King(true, 4, 0);
        assertArrayEquals(new int[]{51, 52, 53, 59, 61, -1, -1, -1, 58, 62}, king.getValidMoves());
        king.updatePosition(62);
        assertArrayEquals(new int[]{53, 54, 55, 61, 63, -1, -1, -1}, king.getValidMoves());
        king.updatePosition(63);
        assertArrayEquals(new int[]{54, 55, -1, 62, -1, -1, -1, -1}, king.getValidMoves());
        king.updatePosition(55);
        assertArrayEquals(new int[]{46, 47, -1, 54, -1, 62, 63, -1}, king.getValidMoves());
    }

    @Test(timeout = 50)
    public void TestQueenValidMoves(){
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
    public void TestRookValidMoves(){
        Piece rook = new Rook(true, 0, 0);
        assertArrayEquals(new int[]{48, 40, 32, 24, 16, 8, 0, 57, 58, 59, 60, 61, 62, 63}, rook.getValidMoves());
        rook.updatePosition(0);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 16, 24, 32, 40, 48, 56}, rook.getValidMoves());
        rook.updatePosition(3);
        assertArrayEquals(new int[]{2, 1, 0, 4, 5, 6, 7, 11, 19, 27, 35, 43, 51, 59}, rook.getValidMoves());
        rook.updatePosition(35);
        assertArrayEquals(new int[]{27, 19, 11, 3, 34, 33, 32, 36, 37, 38, 39, 43, 51, 59}, rook.getValidMoves());
    }
}
