/*
 * The Character class is a subclass of Token and specifies the name of the character token.
 *
 * @author Jakub Gajewski
 */

/*
16310943 James Byrne
16314763 Jakub Gajewski
16305706 Mark Hartnett
 */

public class Character extends Token{
    private CharacterNames name;
    private RoomType roomLastOccupied;

    public Character(Tile currentTile, CharacterNames name){
        super(currentTile);
        this.name = name;
        roomLastOccupied = null;
    }

    /*
        checks to see if the move is valid, if valid moves the player
        @return 0: move valid, 1: wall in the way, 2: player in the way, 3: attempt to return to same room in same turn
     */
    int moveToken(Direction dir, Map map) throws NullPointerException{
        Tile currTile = getCurrentTile();
        int x = currTile.getTileX(); // player X position on board
        int y = currTile.getTileY(); // player Y position on board
        Tile nextTile = null;
        switch(dir) {
            case UP: {
                if (y != 0) { // if player is not on the top edge of the board
                    nextTile = map.getTile(x, y-1); // tile player wants to move to
                    if (currTile.hasWallUp(map)) {
                        return 1;   // Cannot move through a wall
                    }
                    else if (nextTile.isOccupied()){
                        return 2;   // Cannot move through another player
                    }
                    else if (nextTile.getRoomType() == roomLastOccupied){
                        return 3; // Player cannot return to same room in the same turn
                    }
                    else if (nextTile.getDoorDirection() == Direction.UP) {
                        nextTile = map.getRoom(nextTile.getRoomType().ordinal()).addToken();
                    }
                } else {
                    return 1; // Cannot move outside the map i.e. through a wall
                }
                break;
            }
            case DOWN: {
                if (y != 25-1) { // if player is on the bottom edge of the board
                    nextTile = map.getTile(x, y+1);
                    if (currTile.hasWallDown(map)) {
                        return 1;   // Cannot move through a wall
                    }
                    else if (nextTile.isOccupied()){
                        return 2;   // Cannot move through another player
                    }
                    else if (nextTile.getRoomType() == roomLastOccupied){
                        return 3; // Player cannot return to same room in the same turn
                    }
                    else if (nextTile.getDoorDirection() == Direction.DOWN) {
                        nextTile = map.getRoom(nextTile.getRoomType().ordinal()).addToken();
                    }
                } else {
                    return 1; // Cannot move outside the map i.e. through a wall
                }
                break;
            }
            case LEFT: {
                if (x != 0) {// if player is on the left edge of the board
                    nextTile = map.getTile(x-1, y);
                    if (currTile.hasWallLeft(map)) {
                        return 1;   // Cannot move through a wall
                    }
                    else if (nextTile.isOccupied()){
                        return 2;   // Cannot move through another player
                    }
                    else if (nextTile.getRoomType() == roomLastOccupied){
                        return 3; // Player cannot return to same room in the same turn
                    }
                    else if (nextTile.getDoorDirection() == Direction.LEFT) {
                        nextTile = map.getRoom(nextTile.getRoomType().ordinal()).addToken();
                    }
                } else {
                    return 1; // Cannot move outside the map i.e. through a wall
                }
                break;
            }
            case RIGHT: {// if player is on the right edge of the board
                if (x != 24-1) {
                    nextTile = map.getTile(x + 1, y);
                    if (currTile.hasWallRight(map)) {
                        return 1;   // Cannot move through a wall
                    }
                    else if (nextTile.isOccupied()){
                        return 2;   // Cannot move through another player
                    }
                    else if (nextTile.getRoomType() == roomLastOccupied){
                        return 3; // Player cannot return to same room in the same turn
                    }
                    else if (nextTile.getDoorDirection() == Direction.RIGHT) {
                        nextTile = map.getRoom(nextTile.getRoomType().ordinal()).addToken();
                    }
                } else {
                    return 1; // Cannot move outside the map i.e. through a wall
                }
                break;
            }
        }
        if (nextTile != null) { // if nextTile has not been set
            super.moveToken(nextTile);
            return 0; // move successful
        } else {
            throw new NullPointerException("Attempt to move to a tile which has not been initialised");
        }
    }

    public boolean moveOutOfRoom(Tile nextTile) {
        if (nextTile.isOccupied()) {
            return false;
        }
        super.moveToken(nextTile);
        return true;
    }

    public CharacterNames getName(){
        return name;
    }

    public RoomType getRoomLastOccupied(){
        return roomLastOccupied;
    }

    public void setRoomLastOccupied(RoomType room){
        roomLastOccupied = room;
    }

    @Override
    public String toString(){
        return name.toString().charAt(0) + name.toString().substring(1).toLowerCase();
    }
}