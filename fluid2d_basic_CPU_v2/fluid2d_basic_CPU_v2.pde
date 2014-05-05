//------------------------------------------------------------------------------
//
// author: thomas diewald
// date: 27.07.2011
//
// basic example to show how to initialize a GPU-based fluidsolver
//
// interaction:
//    LMB: set density white
//    MMB: set density red
//    RMB: set velocity
//
//    key 'y' + LMB: add obstacle
//    key 'y' + MMB: remove obstacle
//
//    key 's': to visualize the difference in smoothing
//
//------------------------------------------------------------------------------

import diewald_fluid.Fluid2D;
import diewald_fluid.Fluid2D_CPU;
import diewald_fluid.Fluid2D_GPU;
import java.util.Random;

int  CPU_GPU        = 1; // 0 is GPU, 1 is CPU;
int  fluid_size_x = 150; 
int  fluid_size_y = 150;

int  cell_size    = 4;
int  window_size_x = fluid_size_x  * cell_size + (cell_size * 2);
int  window_size_y = fluid_size_y  * cell_size + (cell_size * 2);

Fluid2D fluid;
PImage output_densityMap;
boolean edit_quader = false;

int counter = 0;
int Y = 0;
/* Tetris Data*/
Piece curPiece;
int vx = 0;
int vy = 0;
Random random = new Random();
int pieceType;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
public void setup() {
  size(window_size_x, window_size_y, JAVA2D);


  fluid = createFluidSolver(CPU_GPU);
  frameRate(60);
  
  /* Tetris Code */
  initGrid();
  curPiece = new Piece(vx,vy,1);
}



/////////////////////////////////////////////////////////////////////////////////////////////////////////////
public void draw() {
  background(255);
    
  if ( mousePressed ) 
    fluidInfluence(fluid);
  float speed = .1;
  
  int off = 15;
  setVel (fluid,       off,        height-10, 2, 2, speed, 0);
  setDens(fluid,       off,        height-10, 3, 3, 1, 0, 0);
  
  //setVel (fluid, width-off,        10, 2, 2, -speed, speed);
  //setDens(fluid, width-off,        10, 3, 3, 1, 1, 1);
  
  //setVel (fluid, width-off, height-10, 2, 2, -speed, -speed);
  //setDens(fluid, width-off, height-10, 3, 3, 0, 0, 1);
  
  //setVel (fluid,       off, height-10, 2, 2, speed, -speed);
  //setDens(fluid,       off, height-10, 3, 3, 0, 1, 0);
  
//  fluid.smoothDensityMap(( keyPressed && key == 's'));
  fluid.smoothDensityMap(true);
  fluid.update();
  image(fluid.getDensityMap(), 0, 0, width, height);
  println(frameRate);
  
    /* Tetris Code */
  if(curPiece.isBottom()) {
    vx = 5;
    vy = 0;
    pieceType = random.nextInt(defs.NUM_PIECE_TYPES);
    curPiece = new Piece(vx,vy,pieceType);
  }   
  if(counter == defs.DROP_SPEED) {
    vy +=1;
    counter = 0;
  }
  else if(key == CODED && keyCode == DOWN && keyPressed)
  {
    vy += 1;
  }
  else if(key == CODED && keyCode == RIGHT && keyPressed)
  {
    vx += 1;
  }
  else if(key == CODED && keyCode == LEFT && keyPressed)
  {
    vx -= 1;
  }
  curPiece.setLocation(vx,vy);
  counter++;
  drawGrid();
  /* End Tetris Code*/
}


/////////////////////////////////////////////////////////////////////////////////////////////////////////////
// createFluidSolver();
//
Fluid2D createFluidSolver(int type) {
  Fluid2D fluid_tmp = null;
  
  if ( type == 0) fluid_tmp = new Fluid2D_GPU(this, fluid_size_x, fluid_size_y);
  if ( type == 1) fluid_tmp = new Fluid2D_CPU(this, fluid_size_x, fluid_size_y);

  fluid_tmp.setParam_Timestep  ( 0.10f );
  fluid_tmp.setParam_Iterations( 8 );
  fluid_tmp.setParam_IterationsDiffuse(1);
  fluid_tmp.setParam_Viscosity ( 0.00000000f );
  fluid_tmp.setParam_Diffusion ( 0.000001f );
  fluid_tmp.setParam_Vorticity ( 2.0f );
  fluid_tmp.processDensityMap  ( true );
  fluid_tmp.processDiffusion   ( true );
  fluid_tmp.processViscosity   ( !true );
  fluid_tmp.processVorticity   ( true );
  fluid_tmp.setObjectsColor    (1, 1, 1, 1); 
  
  output_densityMap    = createImage(window_size_x, window_size_y, RGB);
  fluid_tmp.setDensityMap(output_densityMap);
  return fluid_tmp;
}

