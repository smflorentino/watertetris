/**
 * Created by Scott on 5/5/2014.
 */
class Grid {
    static Space[][] GRID;

    public static void initGrid()
    {
        GRID = new Space[defs.GRID_COUNT_X][defs.GRID_COUNT_Y];

        for(int i =0;i<defs.GRID_COUNT_X;i++)
        {
            for(int j = 0;j<defs.GRID_COUNT_Y;j++)
            {
                GRID[i][j] = new Space(i,j,null);
            }
        }
    }

    public static void drawGrid()
    {
        for(int i =0;i<defs.GRID_COUNT_X;i++)
        {
            for(int j = 0;j<defs.GRID_COUNT_Y;j++)
            {
                GRID[i][j].display();
            }
        }
    }

    public static boolean offGrid(int x, int y)
    {
        return !(x < defs.GRID_COUNT_X && y < defs.GRID_COUNT_Y && x >= 0);
    }
}
