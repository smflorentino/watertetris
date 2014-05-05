//
Space[][] grid;

public void initGrid()
{
  grid = new Space[defs.GRID_COUNT_X][defs.GRID_COUNT_Y];
  
  for(int i =0;i<defs.GRID_COUNT_X;i++)
  {
    for(int j = 0;j<defs.GRID_COUNT_Y;j++)
    {
      grid[i][j] = new Space(i,j,null);
    }
  }
}

public void drawGrid()
{
  for(int i =0;i<defs.GRID_COUNT_X;i++)
  {
    for(int j = 0;j<defs.GRID_COUNT_Y;j++)
    {
      grid[i][j].display();
    }
  }
}

class Space {
  int x;
  int y;
  Piece piece;
  boolean filled;
  int px;
  int py;
  
  public Space(int x, int y, Piece p)
  {
    this.x = x;
    this.y = y;
    this.piece = piece;
    this.filled = false;
    this.px = defs.GRID_OFFSET_X + (this.x * defs.GRID_SPACE_SIZE);
    this.py = defs.GRID_OFFSET_Y + (this.y * defs.GRID_SPACE_SIZE);
  }
  
  public void display()
  {
    if(this.piece == null)
    {
      fill(255,255,255,0);
    }
    else
    {
      this.piece.setColor();
    }

    rect(this.px,this.py,defs.GRID_SPACE_SIZE,defs.GRID_SPACE_SIZE);
  }
  
  public void setPiece(Piece piece)
  {
    this.piece = piece;
    this.filled = piece != null;
  }
}
