import javax.swing.*;

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
        P.fluidSpeed = .01f + (.1f * (P.score / 250));
    }

    //Points for having completed foundation levels
    public static void computeWallLevelScore()
    {
        incrementStore((defs.GRID_COUNT_Y - P.nextRowLevel) * 30);
        int heightAdjustment = P.score / 1000;
        if(P.difficulty == Difficulty.HARD)
        {
            P.fluidY = P.height - 10 - 10*heightAdjustment;
        }
    }

    private static synchronized void incrementStore(int x)
    {
        P.score += x;
    }

    public static void displayScore()
    {
        P.textSize(32);
        P.fill(200);
        P.text("Score: " + P.score, 30, 30);
        if(P.score >= P.winningScore)
        {
            JOptionPane.showMessageDialog(null,"Congrats!! You won!! You protected your village!");
            System.exit(0);
        }
    }
}
