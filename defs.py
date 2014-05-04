from pyprocessing import *

#X and Y size of a grid space
GRID_SPACE_SIZE = 10
#Number of spaces in the grid in the X direction
GRID_COUNT_X = 10
#Number of spaces in the grid in the Y direction
GRID_COUNT_Y = 35

#Starting X & Y position of Grid on the Window
GRID_OFFSET_X = 200
GRID_OFFSET_Y = 0

#Frequency of auto-drop (60 = 1 second)
DROP_SPEED = 30

#Number of Piece Types
NUM_PIECE_TYPES = 2

PIECE_WIDTH = 80
PIECE_HEIGHT = 40

KEY_UP = 65362
KEY_LEFT = 65361
KEY_DOWN = 65364
KEY_RIGHT = 65363
KEY_LEFT_CONTROL = 65507

# iImg = loadImage("1.png")
# oImg = loadImage("4.png")

class Shape:
    O = 1
    T = 2

class Piece:
    grid = None

    def __init__(self,x,y,shape):
        self.x = x
        self.y = y
        self.type = shape
        self.degrees = 0

    def updateGrid(self,piece):
        if self.type is Shape.O:
            Piece.grid[self.x][self.y].setPiece(piece)
            Piece.grid[self.x][self.y+1].setPiece(piece)
            Piece.grid[self.x+1][self.y].setPiece(piece)
            Piece.grid[self.x+1][self.y+1].setPiece(piece)
        elif self.type is Shape.T:
            if self.degrees == 0:
                Piece.grid[self.x+1][self.y].setPiece(piece)
                Piece.grid[self.x][self.y+1].setPiece(piece)
                Piece.grid[self.x+1][self.y+1].setPiece(piece)
                Piece.grid[self.x+2][self.y+1].setPiece(piece)
            elif self.degrees == 90:
                Piece.grid[self.x][self.y].setPiece(piece)
                Piece.grid[self.x][self.y+1].setPiece(piece)
                Piece.grid[self.x][self.y+2].setPiece(piece)
                Piece.grid[self.x+1][self.y+1].setPiece(piece)
            elif self.degrees == 180:
                Piece.grid[self.x][self.y].setPiece(piece)
                Piece.grid[self.x+1][self.y].setPiece(piece)
                Piece.grid[self.x+2][self.y].setPiece(piece)
                Piece.grid[self.x+1][self.y+1].setPiece(piece)
            elif self.degrees == 270:
                Piece.grid[self.x+1][self.y].setPiece(piece)
                Piece.grid[self.x+1][self.y+1].setPiece(piece)
                Piece.grid[self.x+1][self.y+2].setPiece(piece)
                Piece.grid[self.x][self.y+1].setPiece(piece)
        # grid.grid[self.x][self.y].setPiece(self)
		# if self.type == Shape.O:
		# 	print "derp"

    def setLocation(self,x,y):
        self.updateGrid(None)
        if self.canMoveToX(x):
            self.x = x
        self.y = y 
        self.updateGrid(self)

    def isBottom(self):
        if self.type is Shape.O:
            if self.y is GRID_COUNT_Y - 2:
                return True
            elif Piece.grid[self.x][self.y+2].filled or Piece.grid[self.x+1][self.y+2].filled:
                return True
        if self.type is Shape.T:
            if self.degrees == 0:
                if self.y == GRID_COUNT_Y - 2:
                    return True
                return Piece.grid[self.x][self.y+2].filled or Piece.grid[self.x+1][self.y+2].filled or Piece.grid[self.x+2][self.y+2].filled
            elif self.degrees == 90:
                if self.y == GRID_COUNT_Y - 3:
                    return True
                    return Piece.grid[self.x][self.y+3].filled or Piece.grid[self.x+1][self.y+2].filled
            elif self.degrees == 180:
                if self.y == GRID_COUNT_Y - 2:
                    return True
                return Piece.grid[self.x][self.y+1].filled or Piece.grid[self.x+1][self.y+2].filled or Piece.grid[self.x+2][self.y+1].filled
            elif self.degrees == 270:
                if self.y == GRID_COUNT_Y - 3:
                    return True
                return Piece.grid[self.x][self.y+2].filled or Piece.grid[self.x+1][self.y+3].filled
        return False

    def canMoveToX(self,x):
        if self.type is Shape.O:
            return x < GRID_COUNT_X - 1 and x >= 0
        if self.type is Shape.T:
            return x < GRID_COUNT_X - 2 and x >= 0
        return True

    def setColor(self):
        if self.type is Shape.O:
            fill(255,255,0)
        elif self.type is Shape.T:
            fill(255,0,255)
        else:
            fill(0,0,0)

    def rotate(self):
        self.updateGrid(None)
        self.degrees = self.degrees + 90
        if self.degrees == 360:
            self.degrees = 0
        self.updateGrid(self)

