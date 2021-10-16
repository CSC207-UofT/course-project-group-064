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
}
