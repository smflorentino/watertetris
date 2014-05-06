/**
 * Created by Scott on 5/6/2014.
 */
public class Score {

    static Main P;
    public static void init(Main m)
    {
        P = m;
    }
    //Points for having a tall wall
    public static void calculateTopPieceScore()
    {
        for(int i =0;i<defs.GRID_COUNT_X;i++)
        {
            for(int j = 0;j<defs.GRID_COUNT_Y;j++)
            {
                if(Grid.GRID[i][j].finalized)
                {
                    P.score += (defs.GRID_COUNT_Y - j) / 5;
                }
            }
        }
    }

    //Points for having completed foundation levels
    public static void computeWallLevelScore()
    {
        incrementStore((defs.GRID_COUNT_Y - P.nextRowLevel) * 30);
    }

    private static synchronized void incrementStore(int x)
    {
        P.score += x;
    }
}
