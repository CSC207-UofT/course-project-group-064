
import com.playchessgame.chessgame.Entities.PlayerUser;
import com.playchessgame.chessgame.Entities.Utils;
import org.junit.*;

import static org.junit.Assert.*;


public class UtilsTest {

    @Test(timeout = 50)
    public void TestSquaresToEdge() {
        int[][] numSquares = Utils.fillNumSquares();
        assertArrayEquals(new int[]{0, 0, 0, 0, 7, 0, 7, 7}, numSquares[0]);
        assertArrayEquals(new int[]{0, 1, 1, 0, 7, 0, 6, 6}, numSquares[8]);
        assertArrayEquals(new int[]{2, 2, 2, 3, 4, 3, 5, 4}, numSquares[19]);
        assertArrayEquals(new int[]{3, 3, 1, 6, 1, 4, 4, 1}, numSquares[30]);
        assertArrayEquals(new int[]{7, 7, 0, 7, 0, 0, 0, 0}, numSquares[63]);
    }

    @Test(timeout = 50)
    public void TestCalcElo() {
        PlayerUser white = new PlayerUser("testwhite", "1000");
        PlayerUser black = new PlayerUser("testblack", "1000");
        Utils.calculateElo(.5, white, black);
        assertEquals(white.getElo(), 1000);
        assertEquals(black.getElo(), 1000);
        Utils.calculateElo(1, white, black);
        assertEquals(white.getElo(), 1025);
        assertEquals(black.getElo(), 975);
        Utils.calculateElo(0, white, black);
        assertEquals(white.getElo(), 996);
        assertEquals(black.getElo(), 1003);
        Utils.calculateElo(.5, white, black);
        assertEquals(white.getElo(), 996);
        assertEquals(black.getElo(), 1002);
    }
}
