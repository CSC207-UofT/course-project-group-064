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
}
