import java.awt.Point;

/*
16310943 James Byrne
16314763 Jakub Gajewski
16305706 Mark Hartnett
 */

/*
 * The Token class is a superclass of Character and Weapon. It stores the Tile that the movable token
 * is located on and provides method to move tile and return its coordinates.
 */

public class Token{
    private Tile currentTile;
    private Point position;

    //constructor
    public Token(Tile currentTile){
        this.currentTile = currentTile;
        currentTile.setOccupied(true);
        position = new Point(currentTile.getXCoordinate(), currentTile.getYCoordinate());
    }

    /**
     * @param newTile the Tile which the Token should move to
     */
    public void moveToken(Tile newTile){
        currentTile.setOccupied(false);
        currentTile = newTile;
        currentTile.setOccupied(true);
    }

    public void setCurrentTile(Tile currTile) {
        currentTile = currTile;
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}



