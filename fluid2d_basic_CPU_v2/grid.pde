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
  boolean finalized;
  int stage;
  boolean row;
  
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
    stroke(255,255,255);
    if(this.piece == null)
    {
      this.stage = 0;
      int quader_size = 4;
      int xpos = (int)(px/(float)cell_size) - quader_size/2;
      int ypos = (int)(py/(float)cell_size) - quader_size/2;
      xpos += 2;
      ypos += 2;
      addObject(fluid, xpos, ypos, quader_size, quader_size, 1);
//      addObject(fluid,px,py,defs.GRID_SPACE_SIZE,defs.GRID_SPACE_SIZE,1);
      fill(255,255,255,0);
      rect(this.px,this.py,defs.GRID_SPACE_SIZE,defs.GRID_SPACE_SIZE);
    }
    else
    {
      int quader_size = 4;
      int xpos = (int)(px/(float)cell_size) - quader_size/2;
      int ypos = (int)(py/(float)cell_size) - quader_size/2;
      xpos += 2;
      ypos += 2;
      addObject(fluid, xpos, ypos, quader_size, quader_size, 0);
//      addObject(fluid,px,py,defs.GRID_SPACE_SIZE,defs.GRID_SPACE_SIZE,0);
      this.piece.setColor();
      if(finalized) { fill(255,255,255); }
      rect(this.px,this.py,defs.GRID_SPACE_SIZE,defs.GRID_SPACE_SIZE);
      if(!row)
      {
          fill(0,0,255,stage/1);
          rect(this.px,this.py,defs.GRID_SPACE_SIZE,defs.GRID_SPACE_SIZE);
          stage ++;
          if(stage == 255)
          {
            shiftDownCol(this.x);
          }
          
      }
    }
    
  }
  
  public void setPiece(Piece piece,boolean moving)
  {
    stage = 0;
    this.piece = piece;
    this.filled = piece != null;
    this.finalized  = !moving;
  }
}
