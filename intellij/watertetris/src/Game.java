
/**
 * Created by Scott on 5/5/2014.
 */
public class Game {
    static Main P;

    public static void init(Main m)
    {
        P = m;
    }
    public static void shiftDownCol(int col) {
        //Get the bottom-most block of the column and remove it
        Space bottom = Grid.GRID[col][defs.GRID_COUNT_Y-1];
        bottom.setPiece(null,true);

        //Now, move all the other blocks in the column down.
        for (int i = defs.GRID_COUNT_Y - 2; i > 0; i--) {
            Space cur = Grid.GRID[col][i];
            //If we're looking at part of a completed row, do nothing.
            if(cur.row) { continue; }
            Piece curPiece = cur.piece;
            Space below = Grid.GRID[col][i+1];
            //If we have dropped block, shift it down.
            if(cur.finalized && cur.piece != null)
            {
                P.score = P.score >= 1 ? P.score -= P.difficulty.ordinal()*2 : P.score;
                //Copy the block down.
                below.setPiece(curPiece,false);
                //Set current to blank
                cur.setPiece(null,true);
            }
        }
        //TODO Handle the top differently.
    }

}
