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

PIECE_WIDTH = 80
PIECE_HEIGHT = 40

UP = 65362
LEFT = 65361
DOWN = 65364
RIGHT = 65363

iImg = loadImage("1.png")
oImg = loadImage("4.png")

class Shape:
    O = 0

class Piece:
    grid = None

    def __init__(self,x,y,shape):
        self.x = x
        self.y = y
        self.type = shape

    def updateGrid(self,piece):
        print "sad"
        if(self.type == Shape.O):
            Piece.grid[self.x][self.y].setPiece(piece)
            Piece.grid[self.x][self.y+1].setPiece(piece)
            Piece.grid[self.x+1][self.y].setPiece(piece)
            Piece.grid[self.x+1][self.y+1].setPiece(piece)
        # grid.grid[self.x][self.y].setPiece(self)
		# if self.type == Shape.O:
		# 	print "derp"
    def setLocation(self,x,y):
        self.updateGrid(None)
        self.x = x
        self.y = y 
        self.updateGrid(self)



class I:
    height = 10
    width = 40

    def __init__(self):
        self.x = 240
        self.y = 30

    def display(self):
        image(iImg, self.x, self.y, I.height, I.width)

    def displayXY(self, x, y):
        image(oImg, x, y, O.height, O.width)


class O:
    height = 20
    width = 20

    def __init__(self):
        self.x = 240
        self.y = 0

    def display(self):
        image(oImg, self.x, self.y, O.height, O.width)

    def displayXY(self, x, y):
        image(oImg, x, y, O.height, O.width)