from pyprocessing import *
import defs

grid = []

def initGrid():
    piece = defs.I()
    for i in range(0,defs.GRID_COUNT_X):
        grid.append([])
        for j in range(0,defs.GRID_COUNT_Y):
            space = Space(i,j,None)
            grid[i].append(space)
    return grid

def drawGrid():
    for i in range(0,defs.GRID_COUNT_X):
        for j in range(0,defs.GRID_COUNT_Y):
            # print grid[i][j]
            grid[i][j].display()

class Space:
    def __init__(self,x,y,piece):
        self.x = x;
        self.y = y;
        self.piece = piece;

    def display(self):
        x = defs.GRID_OFFSET_X + (self.x * defs.GRID_SPACE_SIZE)
        y = defs.GRID_OFFSET_Y + (self.y * defs.GRID_SPACE_SIZE)

        if self.piece is None:
            fill(255,255,255)

        if self.piece is not None:
            print self.piece
            print "HELLO WORLD %d %d" % (self.x,self.y)
            fill(255,0,0)
        rect(x,y,10,10)
    def setPiece(self,piece):
        self.piece = piece
        if piece is None:
            self.free = False