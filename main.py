from pyprocessing import *
import defs
import grid
import random

def setup():
    global vx, vy, curPiece, space,griddata
    global counter
    counter = 0
    # img = loadImage("1.png")
    size(640,480)
    vx = 5
    vy = 0
    griddata = grid.initGrid()
    curPiece = defs.Piece(vx,vy,2)
    defs.Piece.grid = griddata
    # curPiece.setLocation(5,0)

def draw():
    global vx,vy,curPiece, grid
    global counter
    background(200)
    # image(img,0,0,defs.PIECE_WIDTH,defs.PIECE_HEIGHT)
    # x = defs.O()
    # x.display()

    if curPiece.isBottom():
        vx = 5
        vy = 0
        pieceType = random.randint(1,defs.NUM_PIECE_TYPES)
        curPiece = defs.Piece(vx,vy,pieceType)

    if counter is defs.DROP_SPEED:
        vy += 1
        counter = 0
    elif key.code == defs.KEY_DOWN and key.pressed:
        vy += 1
    # elif key.code == defs.KEY_UP and key.pressed:
        # curPiece.rotate()
    elif key.code == defs.KEY_RIGHT and key.pressed:
        vx += 1
    elif key.code == defs.KEY_LEFT and key.pressed:
        vx -= 1
    elif key.code == defs.KEY_LEFT_CONTROL and key.pressed:
        print key.code

    curPiece.setLocation(vx,vy)
    
    counter = counter + 1

    grid.drawGrid()


def keyPressed():
    global vx,vy, curPiece
    if key.code == defs.KEY_UP:
        curPiece.rotate()
    # if key.code == 65362: vy = vy - 5 #decrases vy if the key was up arrow
    # if key.code == 65361: vx = vx - 5 #decrases vx if the key was left arrow
    # if key.code == 65364: vy = vy + 5 #increases vy if the key was down arrow
    # if key.code == 65363: vx = vx + 5 #increases vx if the key was right arrow
run()