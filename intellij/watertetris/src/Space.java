/**
 * Created by Scott on 5/5/2014.
 */
public class Space {

    static Main P;

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

    public static void init(Main m)
    {
        P = m;
    }

    public void display()
    {
        P.stroke(255,255,255);
        if(this.piece == null)
        {
            this.stage = 0;
            int quader_size = 4;
            int xpos = (int)(px/(float)P.cell_size) - quader_size/2;
            int ypos = (int)(py/(float)P.cell_size) - quader_size/2;
            xpos += 2;
            ypos += 2;
            P.addObject(P.fluid, xpos, ypos, quader_size, quader_size, 1);
//      addObject(fluid,px,py,defs.GRID_SPACE_SIZE,defs.GRID_SPACE_SIZE,1);
            P.fill(255,255,255,0);
            P.rect(this.px,this.py,defs.GRID_SPACE_SIZE,defs.GRID_SPACE_SIZE);
        }
        else
        {
            int quader_size = 4;
            int xpos = (int)(px/(float)P.cell_size) - quader_size/2;
            int ypos = (int)(py/(float)P.cell_size) - quader_size/2;
            xpos += 2;
            ypos += 2;
            P.addObject(P.fluid, xpos, ypos, quader_size, quader_size, 0);
//      addObject(fluid,px,py,defs.GRID_SPACE_SIZE,defs.GRID_SPACE_SIZE,0);
            this.piece.setColor();
            if(finalized) { P.fill(255,255,255); }
            P.rect(this.px,this.py,defs.GRID_SPACE_SIZE,defs.GRID_SPACE_SIZE);
            if(!row)
            {
                P.fill(0,0,255,stage);
                P.rect(this.px,this.py,defs.GRID_SPACE_SIZE,defs.GRID_SPACE_SIZE);
                stage += 1;
                if(stage >= 255)
                {
                    Game.shiftDownCol(this.x);
                    stage = 0;
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
