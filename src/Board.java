import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.net.URL;
import java.util.ArrayList;

/*z
16310943 James Byrne
16314763 Jakub Gajewski
16305706 Mark Hartnett
 */

public class Board extends JComponent {
    //construct a logical board
    private Map map;

    //sideLength = length of tile side
    public static final int X_SIDE_LENGTH = 23;
    public static final int Y_SIDE_LENGTH = 23;

    //xDistance = x axis distance from border to tiles
    //yDistance = y axis distance from border to tiles
    public static final int X_BORDER = 20;
    public static final int Y_BORDER = 20;

    // width of walls
    private int wallWidth = 6;

    // stores references to doors which should be numbered on the board while a player is prompted to exit a room
    private final ArrayList<Tile> doorsToNumber = new ArrayList<>();

    public Board() {
        map = new Map();
        setSize(getXBoard(), getYBoard());
    }

    //method to access the logical representation of the board through the Board class
    public Map getMap() {
        return map;
    }

    public int getXBoard() {
        return X_BORDER *2 + X_SIDE_LENGTH *24;
    }

    public int getYBoard() {
        return Y_BORDER *2 + Y_SIDE_LENGTH *25;
    }

    public void addDoorToNumber(Tile door){
        doorsToNumber.add(doorsToNumber.size(), door);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setStroke(new BasicStroke(0.001f)); // set the stroke for the outline of the tiles of roomType CORRIDOR

        // loop through each tile
        for (int x = 0; x < 24; x++) {
            for(int y = 0; y < 25; y++) {
                if (map.getTile(x,y).getRoomType().equals(RoomType.NO_ROOM)) { // if the tile is not a tile where a player can move
                    g2.setColor(new Color(71, 124, 94)); // set the color
                } else if (map.getTile(x,y).getRoomType() == RoomType.CORRIDOR) { // if the room is of roomType corridor
                    g2.setColor(new Color(217, 177, 103)); // set the color
                } else { // if the tile is any other roomType
                    g2.setColor(new Color(146, 131, 105)); // set the color
                }
                Rectangle rect = new Rectangle(map.getTile(x,y).getXCoordinate()-(X_SIDE_LENGTH /2), map.getTile(x,y).getYCoordinate()-(Y_SIDE_LENGTH /2), X_SIDE_LENGTH, Y_SIDE_LENGTH); // create a rectangle with the coordinates of the tile
                g2.fill(rect); // fill that rectangle on the board with the color specified above

                if (map.getTile(x,y).getRoomType() == RoomType.CORRIDOR) { // if the room is a corridor
                    g2.setColor(new Color(171, 135, 86)); // set the color
                    g2.draw(rect); // draw the outline of the rectangle
                }
            }
        }


        // draw green border around board
        g2.setColor(new Color(71, 124, 94));
        g2.fill(new Rectangle(0, 0, getXBoard(), Y_BORDER));
        g2.fill(new Rectangle(0, 0, X_BORDER, getYBoard()));
        g2.fill(new Rectangle(getXBoard()- X_BORDER, 0, X_BORDER, getYBoard()));
        g2.fill(new Rectangle(0, getYBoard()- Y_BORDER, getXBoard(), Y_BORDER));
        
        drawSecretPassages(g2);

        g2.setColor(new Color(109, 31, 36));

        drawWalls(g2);

        g2.setColor(Color.BLACK); // set the color to black

        // display the names of each of the rooms on the JPanel
        g2.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, g2.getFont().getSize()));
        g2.drawString("Monica + Chandler's", (int) (X_BORDER + .2* X_SIDE_LENGTH), (int) (Y_BORDER + 4.2* Y_SIDE_LENGTH));
        g2.drawString("Kitchen", (int) (X_BORDER + 1.7*X_SIDE_LENGTH) , (int) (Y_BORDER + 4.8* Y_SIDE_LENGTH));
        g2.drawString("Monica + Chandler's", (int) (X_BORDER + 1.2* X_SIDE_LENGTH), (int) (Y_BORDER + 13.5* Y_SIDE_LENGTH));
        g2.drawString("Living Room", (int) (X_BORDER + 2.2* X_SIDE_LENGTH), (int) (Y_BORDER + 14.1* Y_SIDE_LENGTH));
        g2.drawString("Rachel's Office", (int) (X_BORDER + 1.5* X_SIDE_LENGTH), Y_BORDER + 23* Y_SIDE_LENGTH);
        g2.drawString("Central Perk", (int) (X_BORDER + 10.5* X_SIDE_LENGTH), Y_BORDER + 6* Y_SIDE_LENGTH);
        g2.drawString("The Geller", (int) (X_BORDER + 10.5* X_SIDE_LENGTH), Y_BORDER + 22* Y_SIDE_LENGTH);
        g2.drawString("Household", (int) (X_BORDER + 10.45* X_SIDE_LENGTH), (int) (Y_BORDER + 22.6* Y_SIDE_LENGTH));
        g2.drawString("Joey's Kitchen", (int) (X_BORDER + 19.5* X_SIDE_LENGTH), Y_BORDER + 4* Y_SIDE_LENGTH);
        g2.drawString("Joey's Living Room", (int) (X_BORDER + 18.5* X_SIDE_LENGTH), (int) (Y_BORDER + 10.5* Y_SIDE_LENGTH));
        g2.drawString("Phoebe's Apartment", X_BORDER + 18* X_SIDE_LENGTH, (int) (Y_BORDER + 17.2* Y_SIDE_LENGTH));
        g2.drawString("Allesandro's", X_BORDER + 19* X_SIDE_LENGTH, (int) (Y_BORDER + 23.8* Y_SIDE_LENGTH));

        drawDoorNumbers(g2);

        drawSecretPassages(g2);
    }

    private void drawDoorNumbers(Graphics2D g2){
        for (Tile door: doorsToNumber){
            Integer doorNumber = doorsToNumber.indexOf(door) + 1;
            g2.drawString(doorNumber.toString(), door.getXCoordinate(), door.getYCoordinate());
        }
    }

    public void clearDoorsToNumber(){
        doorsToNumber.clear();
    }

    private void drawWalls(Graphics2D g2) {
        // filling the walls for Monica + Chandler's Kitchen
        g2.fill(new Rectangle(X_BORDER + 6* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + Y_SIDE_LENGTH - wallWidth/2, wallWidth, 6* Y_SIDE_LENGTH + wallWidth)); //vertical
        g2.fill(new Rectangle(X_BORDER - wallWidth/2, Y_BORDER + Y_SIDE_LENGTH - wallWidth/2, wallWidth, 5* Y_SIDE_LENGTH + wallWidth)); // vertical
        g2.fill(new Rectangle(X_BORDER + X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 6* Y_SIDE_LENGTH - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); // vertical
        g2.fill(new Rectangle(X_BORDER - wallWidth/2, Y_BORDER + Y_SIDE_LENGTH - wallWidth/2, 6* X_SIDE_LENGTH + wallWidth, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER - wallWidth/2, Y_BORDER + 6* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth, wallWidth)); // horizontal
        g2.fill(new Rectangle(X_BORDER + X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 7* Y_SIDE_LENGTH - wallWidth/2, 3* X_SIDE_LENGTH + wallWidth/2,  wallWidth)); // horizontal
        g2.fill(new Rectangle(X_BORDER + 5* X_SIDE_LENGTH, Y_BORDER + 7* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2,  wallWidth)); // horizontal

        // filling the walls for Monica + Chandler's Living Room
        g2.fill(new Rectangle(X_BORDER - wallWidth/2, Y_BORDER + 9* Y_SIDE_LENGTH, wallWidth, 7* Y_SIDE_LENGTH)); // vertical
        g2.fill(new Rectangle(X_BORDER + 5* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 9* Y_SIDE_LENGTH, wallWidth, Y_SIDE_LENGTH)); // vertical
        g2.fill(new Rectangle(X_BORDER + 8* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 10* Y_SIDE_LENGTH, wallWidth, 2* Y_SIDE_LENGTH)); // vertical
        g2.fill(new Rectangle(X_BORDER + 8* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 13* Y_SIDE_LENGTH, wallWidth, 3* Y_SIDE_LENGTH + wallWidth/2)); // vertical
        g2.fill(new Rectangle(X_BORDER - wallWidth/2, Y_BORDER + 9* Y_SIDE_LENGTH - wallWidth/2, 5* X_SIDE_LENGTH + wallWidth, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 5* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 10* Y_SIDE_LENGTH - wallWidth/2, 3* X_SIDE_LENGTH + wallWidth, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER - wallWidth/2, Y_BORDER + 16* Y_SIDE_LENGTH - wallWidth/2, 6* X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 7* X_SIDE_LENGTH, Y_BORDER + 16* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal

        // filling the walls for Rachel's Office
        g2.fill(new Rectangle(X_BORDER - wallWidth/2, Y_BORDER + 19* Y_SIDE_LENGTH, wallWidth, 6* Y_SIDE_LENGTH)); // vertical
        g2.fill(new Rectangle(X_BORDER + 6* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 24* Y_SIDE_LENGTH - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); // vertical
        g2.fill(new Rectangle(X_BORDER + 7* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 19* Y_SIDE_LENGTH, wallWidth, 5* Y_SIDE_LENGTH)); // vertical
        g2.fill(new Rectangle(X_BORDER - wallWidth/2, Y_BORDER + 19* Y_SIDE_LENGTH - wallWidth/2, 5* X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 6* X_SIDE_LENGTH, Y_BORDER + 19* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER - wallWidth/2, Y_BORDER + 25* Y_SIDE_LENGTH - wallWidth/2, 6* X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 6* X_SIDE_LENGTH, Y_BORDER + 24* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal

        // filling the walls for Central Perk
        g2.fill(new Rectangle(X_BORDER + 10* X_SIDE_LENGTH - wallWidth/2, Y_BORDER, wallWidth, 2* Y_SIDE_LENGTH + wallWidth/2)); // vertical
        g2.fill(new Rectangle(X_BORDER + 14* X_SIDE_LENGTH - wallWidth/2, Y_BORDER, wallWidth, 2* Y_SIDE_LENGTH + wallWidth/2)); // vertical
        g2.fill(new Rectangle(X_BORDER + 8* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 2* Y_SIDE_LENGTH - wallWidth/2, wallWidth, 3* Y_SIDE_LENGTH + wallWidth/2)); // vertical
        g2.fill(new Rectangle(X_BORDER + 16* X_SIDE_LENGTH, Y_BORDER + 2* Y_SIDE_LENGTH - wallWidth/2, wallWidth, 3* Y_SIDE_LENGTH + wallWidth/2)); // vertical
        g2.fill(new Rectangle(X_BORDER + 8* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 6* Y_SIDE_LENGTH, wallWidth, 2* Y_SIDE_LENGTH + wallWidth/2)); // vertical
        g2.fill(new Rectangle(X_BORDER + 16* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 6* Y_SIDE_LENGTH, wallWidth, 2* Y_SIDE_LENGTH + wallWidth/2)); // vertical
        g2.fill(new Rectangle(X_BORDER + 10* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + Y_SIDE_LENGTH - wallWidth/2, 4* X_SIDE_LENGTH + wallWidth, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 8* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 2* Y_SIDE_LENGTH - wallWidth/2, 2* X_SIDE_LENGTH + wallWidth, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 14* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 2* Y_SIDE_LENGTH - wallWidth/2, 2* X_SIDE_LENGTH + wallWidth, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 8* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 8* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 15* X_SIDE_LENGTH, Y_BORDER + 8* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 10* X_SIDE_LENGTH, Y_BORDER + 8* Y_SIDE_LENGTH - wallWidth/2, 4* X_SIDE_LENGTH, wallWidth)); //horizontal

        // filling the walls for Monica + Chandler's Balcony
        g2.fill(new Rectangle(X_BORDER + 10* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 10* Y_SIDE_LENGTH, wallWidth, 7* Y_SIDE_LENGTH + wallWidth/2)); // vertical
        g2.fill(new Rectangle(X_BORDER + 15* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 10* Y_SIDE_LENGTH, wallWidth, 7* Y_SIDE_LENGTH + wallWidth/2)); // vertical
        g2.fill(new Rectangle(X_BORDER + 10* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 10* Y_SIDE_LENGTH - wallWidth/2, 5* X_SIDE_LENGTH + wallWidth, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 10* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 17* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 14* X_SIDE_LENGTH, Y_BORDER + 17* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal

        // filling the walls for The Geller Household
        g2.fill(new Rectangle(X_BORDER + 9* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 18* Y_SIDE_LENGTH, wallWidth, 7* Y_SIDE_LENGTH + wallWidth/2)); // vertical
        g2.fill(new Rectangle(X_BORDER + 15* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 18* Y_SIDE_LENGTH - wallWidth/2, wallWidth, 2* Y_SIDE_LENGTH + wallWidth/2)); // vertical
        g2.fill(new Rectangle(X_BORDER + 15* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 21* Y_SIDE_LENGTH, wallWidth, 4* Y_SIDE_LENGTH + wallWidth/2)); // vertical
        g2.fill(new Rectangle(X_BORDER + 9* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 18* Y_SIDE_LENGTH - wallWidth/2, 2* X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 13* X_SIDE_LENGTH, Y_BORDER + 18* Y_SIDE_LENGTH - wallWidth/2, 2* X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 9* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 25* Y_SIDE_LENGTH - wallWidth/2, 6* X_SIDE_LENGTH + wallWidth, wallWidth)); //horizontal

        // filling the walls for Joey's Kitchen
        g2.fill(new Rectangle(X_BORDER + 18* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + Y_SIDE_LENGTH, wallWidth, 4* Y_SIDE_LENGTH)); // vertical
        g2.fill(new Rectangle(X_BORDER + 19* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 5* Y_SIDE_LENGTH, wallWidth, Y_SIDE_LENGTH + wallWidth/2)); // vertical
        g2.fill(new Rectangle(X_BORDER + 23* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 5* Y_SIDE_LENGTH, wallWidth, Y_SIDE_LENGTH + wallWidth/2)); // vertical
        g2.fill(new Rectangle(X_BORDER + 24* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + Y_SIDE_LENGTH, wallWidth, 4* Y_SIDE_LENGTH + wallWidth/2)); // vertical
        g2.fill(new Rectangle(X_BORDER + 18* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + Y_SIDE_LENGTH - wallWidth/2, 6* X_SIDE_LENGTH + wallWidth, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 23* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 5* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 19* X_SIDE_LENGTH, Y_BORDER + 6* Y_SIDE_LENGTH - wallWidth/2, 4* X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal

        // filling the walls for Joey's Living Room
        g2.fill(new Rectangle(X_BORDER + 18* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 8* Y_SIDE_LENGTH - wallWidth/2, 6* X_SIDE_LENGTH + wallWidth, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 18* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 13* Y_SIDE_LENGTH - wallWidth/2, 4* X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 23* X_SIDE_LENGTH, Y_BORDER + 13* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 18* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 8* Y_SIDE_LENGTH, wallWidth, Y_SIDE_LENGTH)); // vertical
        g2.fill(new Rectangle(X_BORDER + 18* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 10* Y_SIDE_LENGTH, wallWidth, 3* Y_SIDE_LENGTH)); // vertical
        g2.fill(new Rectangle(X_BORDER + 24* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 8* Y_SIDE_LENGTH, wallWidth, 5* Y_SIDE_LENGTH)); // vertical

        // filling the walls for Phoebe's Apartment
        g2.fill(new Rectangle(X_BORDER + 18* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 14* Y_SIDE_LENGTH, wallWidth, Y_SIDE_LENGTH)); // vertical
        g2.fill(new Rectangle(X_BORDER + 23* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 14* Y_SIDE_LENGTH, wallWidth, Y_SIDE_LENGTH)); // vertical
        g2.fill(new Rectangle(X_BORDER + 18* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 18* Y_SIDE_LENGTH, wallWidth, Y_SIDE_LENGTH)); // vertical
        g2.fill(new Rectangle(X_BORDER + 23* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 18* Y_SIDE_LENGTH, wallWidth, Y_SIDE_LENGTH)); // vertical
        g2.fill(new Rectangle(X_BORDER + 17* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 15* Y_SIDE_LENGTH, wallWidth, Y_SIDE_LENGTH)); // vertical
        g2.fill(new Rectangle(X_BORDER + 17* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 17* Y_SIDE_LENGTH, wallWidth, Y_SIDE_LENGTH)); // vertical
        g2.fill(new Rectangle(X_BORDER + 24* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 15* Y_SIDE_LENGTH, wallWidth, 3* Y_SIDE_LENGTH)); // vertical
        g2.fill(new Rectangle(X_BORDER + 18* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 14* Y_SIDE_LENGTH - wallWidth/2, 2* X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 21* X_SIDE_LENGTH, Y_BORDER + 14* Y_SIDE_LENGTH - wallWidth/2, 2* X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 18* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 19* Y_SIDE_LENGTH - wallWidth/2, 5* X_SIDE_LENGTH + wallWidth, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 17* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 15* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 23* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 15* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 17* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 18* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 23* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 18* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth, wallWidth)); //horizontal

        // filling the walls for Allesandro's
        g2.fill(new Rectangle(X_BORDER + 17* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 21* Y_SIDE_LENGTH, wallWidth, 3* Y_SIDE_LENGTH + wallWidth/2)); //vertical
        g2.fill(new Rectangle(X_BORDER + 18* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 24* Y_SIDE_LENGTH - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); // vertical
        g2.fill(new Rectangle(X_BORDER + 24* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 21* Y_SIDE_LENGTH - wallWidth/2, wallWidth, 4* Y_SIDE_LENGTH + wallWidth)); // vertical
        g2.fill(new Rectangle(X_BORDER + 18* X_SIDE_LENGTH, Y_BORDER + 21* Y_SIDE_LENGTH - wallWidth/2, 6* X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 17* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 24* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth, wallWidth)); // horizontal
        g2.fill(new Rectangle(X_BORDER + 18* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 25* Y_SIDE_LENGTH - wallWidth/2, 6* X_SIDE_LENGTH + wallWidth/2,  wallWidth)); // horizontal

        // Setting the walls for the edges of the corridor
        g2.fill(new Rectangle(X_BORDER - wallWidth/2, Y_BORDER + 7* Y_SIDE_LENGTH - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); //vertical
        g2.fill(new Rectangle(X_BORDER + X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 8* Y_SIDE_LENGTH - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); //vertical
        g2.fill(new Rectangle(X_BORDER, Y_BORDER + 7* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER, Y_BORDER + 8* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 16* Y_SIDE_LENGTH - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); //vertical
        g2.fill(new Rectangle(X_BORDER - wallWidth/2, Y_BORDER + 17* Y_SIDE_LENGTH - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); //vertical
        g2.fill(new Rectangle(X_BORDER + X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 18* Y_SIDE_LENGTH - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); //vertical
        g2.fill(new Rectangle(X_BORDER, Y_BORDER + 17* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER, Y_BORDER + 18* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 7* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 24* Y_SIDE_LENGTH - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); //vertical
        g2.fill(new Rectangle(X_BORDER + 8* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 24* Y_SIDE_LENGTH - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); //vertical
        g2.fill(new Rectangle(X_BORDER + 7* X_SIDE_LENGTH, Y_BORDER + 25* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 8* X_SIDE_LENGTH, Y_BORDER + 24* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 16* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 24* Y_SIDE_LENGTH - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); //vertical
        g2.fill(new Rectangle(X_BORDER + 17* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 24* Y_SIDE_LENGTH - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); //vertical
        g2.fill(new Rectangle(X_BORDER + 16* X_SIDE_LENGTH, Y_BORDER + 25* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 15* X_SIDE_LENGTH, Y_BORDER + 24* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 24* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 6* Y_SIDE_LENGTH - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); //vertical
        g2.fill(new Rectangle(X_BORDER + 23* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 7* Y_SIDE_LENGTH - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); //vertical
        g2.fill(new Rectangle(X_BORDER + 23* X_SIDE_LENGTH, Y_BORDER + 6* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 23* X_SIDE_LENGTH, Y_BORDER + 7* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 23* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 13* Y_SIDE_LENGTH - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); //vertical
        g2.fill(new Rectangle(X_BORDER + 24* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 19* Y_SIDE_LENGTH - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); //vertical
        g2.fill(new Rectangle(X_BORDER + 23* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 20* Y_SIDE_LENGTH - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); //vertical
        g2.fill(new Rectangle(X_BORDER + 23* X_SIDE_LENGTH, Y_BORDER + 19* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 23* X_SIDE_LENGTH, Y_BORDER + 20* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 7* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + Y_SIDE_LENGTH - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); //vertical
        g2.fill(new Rectangle(X_BORDER + 9* X_SIDE_LENGTH - wallWidth/2, Y_BORDER - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); //vertical
        g2.fill(new Rectangle(X_BORDER + 6* X_SIDE_LENGTH, Y_BORDER + 2* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 7* X_SIDE_LENGTH, Y_BORDER + Y_SIDE_LENGTH - wallWidth/2, 2* X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 9* X_SIDE_LENGTH, Y_BORDER - wallWidth/2, X_SIDE_LENGTH + wallWidth/2, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 17* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + Y_SIDE_LENGTH - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); //vertical
        g2.fill(new Rectangle(X_BORDER + 15* X_SIDE_LENGTH - wallWidth/2, Y_BORDER - wallWidth/2, wallWidth, Y_SIDE_LENGTH + wallWidth)); //vertical
        g2.fill(new Rectangle(X_BORDER + 17* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + 2* Y_SIDE_LENGTH - wallWidth/2, X_SIDE_LENGTH + wallWidth, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 15* X_SIDE_LENGTH - wallWidth/2, Y_BORDER + Y_SIDE_LENGTH - wallWidth/2, 2* X_SIDE_LENGTH + wallWidth, wallWidth)); //horizontal
        g2.fill(new Rectangle(X_BORDER + 14* X_SIDE_LENGTH - wallWidth/2, Y_BORDER - wallWidth/2, X_SIDE_LENGTH + wallWidth, wallWidth)); //horizontal
    }

    public void drawSecretPassages(Graphics2D g2) {
        Font font = g2.getFont();
        g2.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 8));

        g2.drawString("Secret Passage to Allesandros", X_BORDER + 10, Y_BORDER + Y_SIDE_LENGTH  + 15);
        g2.drawString("Secret Passage to Rachel's Office", X_BORDER + 18*X_SIDE_LENGTH + 10, Y_BORDER + Y_SIDE_LENGTH  + 15);
        g2.drawString("Secret Passage to Joey's Kitchen", X_BORDER + 10, Y_BORDER + 19*Y_SIDE_LENGTH  + 15);
        g2.drawString("Secret Passage to M&C's Kitchen", X_BORDER + 18*X_SIDE_LENGTH + 10, Y_BORDER + 21*Y_SIDE_LENGTH  + 15);

        g2.setFont(font);
    }
}
