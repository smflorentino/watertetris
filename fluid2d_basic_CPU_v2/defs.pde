class defs {
  public static final int GRID_SPACE_SIZE = 10;
  public static final int GRID_COUNT_X = 10;
  public static final int GRID_COUNT_Y = 61;
  
  public static final int GRID_OFFSET_X = 200;
  public static final int GRID_OFFSET_Y = 0;
  
  public static final int DROP_SPEED = 30;
  
  public static final int NUM_PIECE_TYPES = 2;
  
  public static final int KEY_UP = 65362;
  public static final int KEY_LEFT = 65361;
  public static final int KEY_DOWN = 65364;
  public static final int KEY_RIGHT = 65363;
  public static final int KEY_LEFT_CONTROL = 65507;
  
}

class Shape {
  public static final int O = 0;
  public static final int T = 1;
}

class Piece {
  
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
  
  public void updateGrid(Piece piece)
  {
    switch(this.type)
    {
      case Shape.O:
        grid[x][y].setPiece(piece);
        grid[x][y+1].setPiece(piece);
        grid[x+1][y].setPiece(piece);
        grid[x+1][y+1].setPiece(piece);
        break;
      case Shape.T:
        switch(this.degrees)
        {
          case 0:
            grid[x+1][y].setPiece(piece);
            grid[x][y+1].setPiece(piece);
            grid[x+1][y+1].setPiece(piece);
            grid[x+2][y+1].setPiece(piece);
            break;
            
          case 90:
            grid[x][y].setPiece(piece);
            grid[x][y+1].setPiece(piece);
            grid[x][y+2].setPiece(piece);
            grid[x+1][y+1].setPiece(piece);
            break;
            
          case 180:
            grid[x][y].setPiece(piece);
            grid[x+1][y].setPiece(piece);
            grid[x+2][y].setPiece(piece);
            grid[x+1][y+1].setPiece(piece);
            break;
            
          case 270:
            grid[x+1][y].setPiece(piece);
            grid[x+1][y+1].setPiece(piece);
            grid[x+1][y+2].setPiece(piece);
            grid[x][y+1].setPiece(piece);
            break;
        }
        break;
    }
  }
  
  public void setLocation(int x, int y)
  {
    this.updateGrid(null);
    if(this.canMoveToX(x))
    {
      this.x = x;
    }
    this.y = y;
    this.updateGrid(this);
  }
  
  public boolean isBottom()
  {
    switch(this.type)
    {
      case Shape.O:
        if(y == defs.GRID_COUNT_Y - 2) { return true; }
        return grid[x][y+2].filled || grid[x+1][y+2].filled;
      case Shape.T:
        switch(this.degrees)
        {
          case 0:
            if(y == defs.GRID_COUNT_Y - 2) { return true; }
            return grid[x][y+2].filled || grid[x+1][y+2].filled || grid[x+2][y+2].filled;
          case 90:
            if(y == defs.GRID_COUNT_Y - 3) {return true ; }
            return grid[x][y+3].filled || grid[x+1][y+2].filled;
          case 180:
            if(y == defs.GRID_COUNT_Y -2 ) {return true; }
            return grid[x][y+1].filled || grid[x+1][y+2].filled || grid[x+2][y+1].filled;
          case 270:
            if(y == defs.GRID_COUNT_Y - 3) { return true; }
            return grid[x][y+2].filled || grid[x+1][y+3].filled;
        }
        break;
    }
    return false;
  }
  
  public void setColor()
  {
    switch(this.type) {
      case Shape.O:
        fill(255,255,0);
        break;
      case Shape.T:
        fill(255,0,255);
        break;
      default:
        fill(0,0,0);
    }
  }
  
  public boolean canMoveToX(int x)
  {
    switch(this.type)
    {
      case Shape.O:
        return x < defs.GRID_COUNT_X - 1 && x >= 0;
      case Shape.T:
        return x < defs.GRID_COUNT_X - 2 && x >= 0;
    }
    return false;   
  }
  
  public void rotate()
  {
    this.updateGrid(null);
    this.degrees += 90;
    this.degrees = degrees == 360 ? 0 : degrees;
    this.updateGrid(this);
  }
}
//  
