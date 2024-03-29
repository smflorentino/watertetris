from pyprocessing import *
import defs

grid = []

def initGrid():
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
        self.x = x
        self.y = y
        self.piece = piece
        self.filled = False
        self.px = defs.GRID_OFFSET_X + (self.x * defs.GRID_SPACE_SIZE)
        self.py = defs.GRID_OFFSET_Y + (self.y * defs.GRID_SPACE_SIZE)
    def display(self):
        if self.piece is None:
            fill(255,255,255)
        else:
            self.piece.setColor()
        rect(self.px,self.py,defs.GRID_SPACE_SIZE,defs.GRID_SPACE_SIZE)
    def setPiece(self,piece):
        self.piece = piece
        if piece is None:
            self.filled = False
        else:
            self.filled = True
        # print "X:%d Y:%d Filled:%s" % (self.x,self.y,self.filled)