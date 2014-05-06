import diewald_fluid.Fluid2D;
import diewald_fluid.Fluid2D_CPU;
//import diewald_fluid.Fluid2D_GPU;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.Random;

/**
 * Created by Scott on 5/5/2014.
 */
public class Main extends PApplet {

    int CPU_GPU = 1; // 0 is GPU, 1 is CPU;
    int fluid_size_x = 150;
    int fluid_size_y = 150;

    int cell_size = 4;
    int window_size_x = fluid_size_x * cell_size + (cell_size * 2);
    int window_size_y = fluid_size_y * cell_size + (cell_size * 2);

    Fluid2D fluid;
    PImage output_densityMap;
    boolean edit_quader = false;
    float fluidSpeed = 0.05f;
    int fluidX = 15;
    int fluidY;

    int counter = 0;
    /* Next Row Level Needed */
    int nextRowLevel = defs.GRID_COUNT_Y - 1;
    /* Background Image */
    PImage backgroundImage;
    /* Wall Image */
    PImage wallImage;

    /* Tetris Data*/
    Piece curPiece;
    int curPieceX = 0;
    int curPieceY = 0;
    Random random = new Random();
    int pieceType = Shape.O;
    //Allow player one second at bottom
    int bottomCounter = 0;
    boolean startingGame = true;
    int score = 1;
    boolean displayGridLines = false;
    int winningScore;
    Difficulty difficulty = Difficulty.EASY;
    int stageMultipler = 0;

    public static void main(String args[]) {
        PApplet.main(new String[]{"Main"});
    }


    public void setup() {
        //Initialize Processing Objects
        Space.init(this);
        Piece.init(this);
        Score.init(this);
        Game.init(this);
        //Load Images
        backgroundImage = loadImage("map.jpg");
        wallImage = loadImage("wall.jpg");
        //Set up Window
        size(window_size_x, window_size_y, JAVA2D);

        //Set up Fluid
        fluid = createFluidSolver(CPU_GPU);
        frameRate(60);

        //Initialize Tetris Grid
        Grid.initGrid();
        //Generate a Piece
        curPiece = new Piece(curPieceX, curPieceY, pieceType);

        background(0);

        fluidY = height - 10;

    }

    public void draw() {
        background(0);
        //Set tutorial until user hits spacebar
        if (startingGame) {
            tutorial();
            setupDifficulty();
            return;
        }
        //Set Background
        fluid.setTextureBackground(backgroundImage);

        if (mousePressed)
            fluidInfluence(fluid);

        setVel(fluid, fluidX, fluidY, 2, 2, fluidSpeed, 0);
        setDens(fluid, fluidX,fluidY, 6, 6, 0, .2f, 1);

        /* Compute & Display the Water*/
        fluid.smoothDensityMap(true);
        fluid.update();
        image(fluid.getDensityMap(), 0, 0, width, height);

        /* Tetris Code */
        //Allow player to move side to side when at bottom, for a bit.
        if (curPiece.isBottom()) {
            bottomCounter++;
            if (bottomCounter == defs.DROP_SPEED) {
                curPiece.makeFinalized();
                curPieceX = 5;
                curPieceY = 0;
                pieceType = random.nextInt(defs.NUM_PIECE_TYPES);
                curPiece = new Piece(curPieceX, curPieceY, pieceType);
                bottomCounter = 0;
            }
        }
        //Auto-drop
        if (counter > defs.DROP_SPEED && !curPiece.isBottom()) {
            curPieceY += 1;
            counter = 0;
        }
        /* Calculate Score */
        if(counter == 0)
        {
            Score.calculateTopPieceScore();
            println(score);
        }
        curPieceY = curPieceY >= defs.GRID_COUNT_Y ? defs.GRID_COUNT_Y - 1 : curPieceY;
        curPiece.setLocation(curPieceX, curPieceY);
        counter++;
        Grid.drawGrid();
        Score.displayScore();
    }

    public void setupDifficulty()
    {
        switch(difficulty)
        {
            case EASY:
                winningScore = 25000;
                stageMultipler = 4;
                break;
            case MEDIUM:
                winningScore = 50000;
                stageMultipler = 3;
                break;
            case HARD:
                winningScore = 75000;
                stageMultipler = 2;
                break;
        }
    }

    public void tutorial() {
        background(0);
        textSize(32);
        fill(150);
        text("Welcome to Water Tetris!", 100, 100);

        textSize(14);

        text(Messages.intro1, 100, 150, 400, 200);

        text(Messages.intro2, 100, 325, 400, 200);

        text(Messages.intro4, 100, 400, 400, 200);

        text("Difficulty: " + difficulty.name(),100,450);


        text(Messages.intro3, 200, 525, 400, 200);


//        startingGame = false;
    }

    Fluid2D createFluidSolver(int type) {
        Fluid2D fluid_tmp = null;

//        if ( type == 0) fluid_tmp = new Fluid2D_GPU(this, fluid_size_x, fluid_size_y);
        if (type == 1) fluid_tmp = new Fluid2D_CPU(this, fluid_size_x, fluid_size_y);

        fluid_tmp.setParam_Timestep(0.10f);
        fluid_tmp.setParam_Iterations(8);
        fluid_tmp.setParam_IterationsDiffuse(1);
        fluid_tmp.setParam_Viscosity(0.00000000f);
        fluid_tmp.setParam_Diffusion(0.000001f);
        fluid_tmp.setParam_Vorticity(2.0f);
        fluid_tmp.processDensityMap(true);
        fluid_tmp.processDiffusion(true);
        fluid_tmp.processViscosity(!true);
        fluid_tmp.processVorticity(true);
        fluid_tmp.setObjectsColor(1, 1, 1, 1);

        output_densityMap = createImage(window_size_x, window_size_y, RGB);
        fluid_tmp.setDensityMap(output_densityMap);
        return fluid_tmp;
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void keyPressed() {
        if (key == ' ') {
            startingGame = false;
        }
        if( key == 'g') {
            displayGridLines = !displayGridLines;
        }

        if(key == 'e') {
            difficulty = Difficulty.EASY;
        }
        if(key == 'm') {
            difficulty = Difficulty.MEDIUM;
        }
        if(key == 'h') {
            difficulty = Difficulty.HARD;
        }

        if (key == 'y') edit_quader = true;
        if (online && key == ESC) key = 0;

  /* Tetris Code */
        if (key == CODED && keyCode == UP) {
            curPiece.rotate();
        }

        if (key == CODED && keyCode == DOWN && keyPressed && !curPiece.isBottom()) {
            curPieceY += 1;
        } else if (key == CODED && keyCode == RIGHT && keyPressed) {
            curPieceX += 1;
        } else if (key == CODED && keyCode == LEFT && keyPressed) {
            curPieceX -= 1;
        }
    }


    public void keyReleased() {
        edit_quader = false;
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
// fluidInfluence();
//
    public void fluidInfluence(Fluid2D fluid2d) {
        if (mouseButton == LEFT) {
            if (edit_quader) {
                int quader_size = 2;
                int xpos = (int) (mouseX / (float) cell_size) - quader_size / 2;
                int ypos = (int) (mouseY / (float) cell_size) - quader_size / 2;
                addObject(fluid2d, xpos, ypos, quader_size, quader_size, 0);
            } else {
                setDens(fluid2d, mouseX, mouseY, 4, 4, 1, 1, 1);
            }
        }
        if (mouseButton == CENTER) {
            if (edit_quader) {
                int quader_size = 2;
                int xpos = (int) (mouseX / (float) cell_size) - quader_size / 2;
                int ypos = (int) (mouseY / (float) cell_size) - quader_size / 2;
                addObject(fluid2d, xpos, ypos, quader_size, quader_size, 1);
            } else {
                setDens(fluid2d, mouseX, mouseY, 4, 4, 2, 0, 0);
            }
        }
        if (mouseButton == RIGHT) {
            float vel_fac = 0.13f;
            int size = (int) (((fluid_size_x + fluid_size_y) / 2.0) / 50.0);
            size = max(size, 1);
            setVel(fluid2d, mouseX, mouseY, size, size, (mouseX - pmouseX) * vel_fac, (mouseY - pmouseY) * vel_fac);
        }
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
// setDens();
//
    void setDens(Fluid2D fluid2d, int x, int y, int sizex, int sizey, float r, float g, float b) {
        for (int y1 = 0; y1 < sizey; y1++) {
            for (int x1 = 0; x1 < sizex; x1++) {
                int xpos = (int) (x / (float) cell_size) + x1 - sizex / 2;
                int ypos = (int) (y / (float) cell_size) + y1 - sizey / 2;
                fluid2d.addDensity(0, xpos, ypos, 4 * r);
                fluid2d.addDensity(1, xpos, ypos, 4 * g);
                fluid2d.addDensity(2, xpos, ypos, 100 * b);
            }
        }
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
// setVel();
//
    void setVel(Fluid2D fluid2d, int x, int y, int sizex, int sizey, float velx, float vely) {
        for (int y1 = 0; y1 < sizey; y1++) {
            for (int x1 = 0; x1 < sizex; x1++) {
                int xpos = (int) ((x / (float) cell_size)) + x1 - sizex / 2;
                int ypos = (int) ((y / (float) cell_size)) + y1 - sizey / 2;
                fluid2d.addVelocity(xpos, ypos, velx, vely);
            }
        }
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
// addObject();
//
    public void addObject(Fluid2D fluid2d, int posx, int posy, int sizex, int sizey, int mode) {
        int offset = 0;
        int xlow = posx;
        int xhig = posx + sizex;
        int ylow = posy;
        int yhig = posy + sizey;
        for (int x = xlow - offset; x < xhig + offset; x++) {
            for (int y = ylow - offset; y < yhig + offset; y++) {
                if (x < 0 || x >= fluid2d.getSizeXTotal() || y < 0 || y >= fluid2d.getSizeYTotal())
                    continue;
                if (mode == 0) fluid2d.addObject(x, y);
                if (mode == 1) fluid2d.removeObject(x, y);
            }
        }
    }

}
