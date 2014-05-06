/**
 * Created by Scott on 5/5/2014.
 */
public class Piece {

    static Main P;

    int x;
    int y;
    int type;
    int degrees;

    public Piece(int x, int y, int type)
    {
        this.x = x;
        this.y = y;
        this.type = type;
        this.degrees = 0;
    }

    public static void init(Main m)
    {
       P = m;
    }

    public void updateGrid(Piece piece, boolean moving)
    {
        switch(this.type)
        {
            case Shape.O:
                if(Grid.offGrid(x+1,y+1)) { return ; }
                Grid.GRID[x][y].setPiece(piece, moving);
                Grid.GRID[x][y+1].setPiece(piece,moving);
                Grid.GRID[x+1][y].setPiece(piece,moving);
                Grid.GRID[x+1][y+1].setPiece(piece,moving);
                break;
            case Shape.T:
                switch(this.degrees)
                {
                    case 0:
                        if(Grid.offGrid(x+2,y+1)) { return ; }
                        Grid.GRID[x+1][y].setPiece(piece,moving);
                        Grid.GRID[x][y+1].setPiece(piece,moving);
                        Grid.GRID[x+1][y+1].setPiece(piece,moving);
                        Grid.GRID[x+2][y+1].setPiece(piece,moving);
                        break;

                    case 90:
                        if(Grid.offGrid(x+1,y+2)) { return ; }
                        Grid.GRID[x][y].setPiece(piece,moving);
                        Grid.GRID[x][y+1].setPiece(piece,moving);
                        Grid.GRID[x][y+2].setPiece(piece,moving);
                        Grid.GRID[x+1][y+1].setPiece(piece,moving);
                        break;

                    case 180:
                        if(Grid.offGrid(x+2,y+1)) { return ; }
                        Grid.GRID[x][y].setPiece(piece,moving);
                        Grid.GRID[x+1][y].setPiece(piece,moving);
                        Grid.GRID[x+2][y].setPiece(piece,moving);
                        Grid.GRID[x+1][y+1].setPiece(piece,moving);
                        break;

                    case 270:
                        if(Grid.offGrid(x+1,y+2)) { return ; }
                        Grid.GRID[x+1][y].setPiece(piece,moving);
                        Grid.GRID[x+1][y+1].setPiece(piece,moving);
                        Grid.GRID[x+1][y+2].setPiece(piece,moving);
                        Grid.GRID[x][y+1].setPiece(piece,moving);
                        break;
                }
                break;
            case Shape.I:
                switch(this.degrees)
                {
                    case 0:
                    case 180:
                        if(Grid.offGrid(x+3,y)) { return ; }
                        Grid.GRID[x][y].setPiece(piece,moving);
                        Grid.GRID[x+1][y].setPiece(piece,moving);
                        Grid.GRID[x+2][y].setPiece(piece,moving);
                        Grid.GRID[x+3][y].setPiece(piece,moving);
                        break;

                    case 90:
                    case 270:
                        if(Grid.offGrid(x,y+3)) { return ; }
                        Grid.GRID[x][y].setPiece(piece,moving);
                        Grid.GRID[x][y+1].setPiece(piece,moving);
                        Grid.GRID[x][y+2].setPiece(piece,moving);
                        Grid.GRID[x][y+3].setPiece(piece,moving);
                        break;
                }
                break;

        }
    }

    public void setLocation(int x, int y)
    {
        this.updateGrid(null,true);
        if(this.canMoveToX(x))
        {
            this.x = x;
        }
        this.y = y;
        this.updateGrid(this,true);
    }

    public synchronized boolean isBottom()
    {
        switch(this.type)
        {
            case Shape.O:
                if(y == defs.GRID_COUNT_Y - 2) { return true; }
                return Grid.GRID[x][y+2].filled || Grid.GRID[x+1][y+2].filled;
            case Shape.T:
                switch(this.degrees)
                {
                    case 0:
                        if(y == defs.GRID_COUNT_Y - 2) { return true; }
                        return Grid.GRID[x][y+2].filled || Grid.GRID[x+1][y+2].filled || Grid.GRID[x+2][y+2].filled;
                    case 90:
                        if(y == defs.GRID_COUNT_Y - 3) {return true ; }
                        return Grid.GRID[x][y+3].filled || Grid.GRID[x+1][y+2].filled;
                    case 180:
                        if(y == defs.GRID_COUNT_Y - 2 ) {return true; }
                        return Grid.GRID[x][y+1].filled || Grid.GRID[x+1][y+2].filled ||  Grid.GRID[x+2][y+1].filled;
                    case 270:
                        if(y == defs.GRID_COUNT_Y - 3) { return true; }
                        return Grid.GRID[x][y+2].filled || Grid.GRID[x+1][y+3].filled;
                }
                break;
            case Shape.I:
                switch(this.degrees)
                {
                    case 0:
                    case 180:
                        if(y == defs.GRID_COUNT_Y - 1) { return true; }
                        return Grid.GRID[x][y+1].filled || Grid.GRID[x+1][y+1].filled || Grid.GRID[x+2][y+1].filled || Grid.GRID[x+3][y+1].filled;
                    case 90:
                    case 270:
                        if(y == defs.GRID_COUNT_Y - 4) { return true; }
                        return Grid.GRID[x][y+4].filled;

                }
        }
        return false;
    }

    public void setColor()
    {
        switch(this.type) {
            case Shape.O:
                P.fill(255,255,0);
                break;
            case Shape.T:
                P.fill(255,0,255);
                break;
            case Shape.I:
                P.fill(0,255,255);
                break;
            default:
                P.fill(0,0,0);
        }
    }

    public boolean canMoveToX(int x)
    {
        if(x < 0) { return false; }
        switch(this.type)
        {
            case Shape.O:
                return x < defs.GRID_COUNT_X - 1;
            case Shape.T:
                if(this.degrees % 180 == 0) { //horizontal
                    return x < defs.GRID_COUNT_X - 2;
                }
                else { //vertical
                    return x < defs.GRID_COUNT_X - 1;
                }
            case Shape.I:
                if(this.degrees % 180 == 0) { //horizontal
                    return x < defs.GRID_COUNT_X - 3;
                }
                else {
                    return x < defs.GRID_COUNT_X;
                }
        }
        return false;
    }

    public void rotate()
    {
        this.updateGrid(null,true);
        this.degrees += 90;
        this.degrees = degrees == 360 ? 0 : degrees;
        this.updateGrid(this,true);
    }

    /* Piece laid to rest. Start fading, unless we get a full row*/
    public void makeFinalized()
    {
        this.updateGrid(this,false);
        //Check if row is complete:
        int fullCols = 0;
        for(int i =defs.GRID_COUNT_Y-1;i>=0;i--)
        {
            for(int j = defs.GRID_COUNT_X-1;j>=0;j--)
            {
                if(Grid.GRID[j][i].filled) { fullCols++; }
            }
            if(fullCols == 10 && i == P.nextRowLevel)
            {
                P.nextRowLevel--;
                for(int j = 0;j<defs.GRID_COUNT_X;j++)
                {
                    Grid.GRID[j][i].row = true;
                }
                Score.computeWallLevelScore();
            }
            fullCols = 0;
        }
    }
}
