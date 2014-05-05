
/**
 * Created by Scott on 5/5/2014.
 */
public class Game {
    public static void shiftDownCol(int col) {
        for (int i = defs.GRID_COUNT_Y - 1; i > 0; i--) {
            Piece p = Grid.GRID[col][i - 1].piece;
            if (Grid.GRID[col][i].finalized && !Grid.GRID[col][i].row) {
                Grid.GRID[col][i].setPiece(p, false);
            }
        }
    }

    }
