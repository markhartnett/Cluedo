/*
 16310943 James Byrne
 16314763 Jakub Gajewski
 16305706 Mark Hartnett
 */

public class Map {
    //declare array of Tile objects
    private Tile[][] tile = new Tile[24][25];

    public Tile getTile(int i, int j) {
        return tile[i][j];
    }

    //Rooms
    private Room[] rooms;

    public Map() {
        initialiseTiles(); // initialise the 2D array of tiles with their coordinates
        setRoomTypes(); // set the room types, i.e. what room they're in including corridor and where there is no room.
        initialiseDoors(); // set the tiles that have doors and the direction that the door is in relation to the corridor tile beside the door
        initialiseRooms(); // initialize the rooms and assign them to the rooms array;
    }

    public Room getRoom(int roomIndex) {
        return rooms[roomIndex];
    }

    //function to set the coordinates of a grid with border
    public void initialiseTiles(){
        for (int x = 0; x < 24; x++) {
            for(int y = 0; y < 25; y++) {
                tile[x][y] = new Tile(x, y);
            }
        }
    }

    public void initialiseDoors() {

        tile[4][6].setDoorDirection(Direction.UP); // Monica + Chandler's Kitchen door

        tile[7][12].setDoorDirection(Direction.LEFT); // Monica + Chandler's Living Room door
        tile[6][15].setDoorDirection(Direction.UP); // Monica + Chandler's Living Room door

        tile[5][19].setDoorDirection(Direction.DOWN); // Rachel's Office door

        tile[8][5].setDoorDirection(Direction.RIGHT); // Central Perk door
        tile[9][7].setDoorDirection(Direction.UP); // Central Perk door
        tile[14][7].setDoorDirection(Direction.UP); // Central Perk door
        tile[15][5].setDoorDirection(Direction.LEFT); // Central Perk door


        tile[11][16].setDoorDirection(Direction.UP); // Monica + Chandler's Balcony Door
        tile[12][16].setDoorDirection(Direction.UP); // Monica + Chandler's Balcony Door
        tile[13][16].setDoorDirection(Direction.UP); // Monica + Chandler's Balcony Door

        tile[11][18].setDoorDirection(Direction.DOWN); // The Geller Household Door
        tile[12][18].setDoorDirection(Direction.DOWN); // The Geller Household Door
        tile[14][20].setDoorDirection(Direction.LEFT); // The Geller Household Door

        tile[18][4].setDoorDirection(Direction.UP); // Joey's Kitchen door

        tile[18][9].setDoorDirection(Direction.RIGHT); // Joey's Living Room door
        tile[22][12].setDoorDirection(Direction.UP); // Joey's Living Room room door

        tile[20][14].setDoorDirection(Direction.DOWN); // Phoebe's Apartment door
        tile[17][16].setDoorDirection(Direction.RIGHT); // Phoebe's Apartment door

        tile[17][21].setDoorDirection(Direction.DOWN); // Allesandro's door

    }

    public void setRoomTypes() {
        // 2-D array of the roomtypes for each tile corresponding to the tiles 2-D array
        RoomType[][] roomTypes = {
            {RoomType.NO_ROOM, RoomType.MC_KITCHEN, RoomType.MC_KITCHEN, RoomType.MC_KITCHEN, RoomType.MC_KITCHEN,RoomType.MC_KITCHEN, RoomType.NO_ROOM, RoomType.CORRIDOR, RoomType.NO_ROOM, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.NO_ROOM, RoomType.CORRIDOR, RoomType.NO_ROOM, RoomType.R_OFFICE, RoomType.R_OFFICE, RoomType.R_OFFICE, RoomType.R_OFFICE, RoomType.R_OFFICE, RoomType.R_OFFICE},
            {RoomType.NO_ROOM, RoomType.MC_KITCHEN, RoomType.MC_KITCHEN, RoomType.MC_KITCHEN, RoomType.MC_KITCHEN, RoomType.MC_KITCHEN, RoomType.MC_KITCHEN, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.R_OFFICE, RoomType.R_OFFICE, RoomType.R_OFFICE, RoomType.R_OFFICE, RoomType.R_OFFICE, RoomType.R_OFFICE},
            {RoomType.NO_ROOM, RoomType.MC_KITCHEN, RoomType.MC_KITCHEN, RoomType.MC_KITCHEN, RoomType.MC_KITCHEN, RoomType.MC_KITCHEN, RoomType.MC_KITCHEN, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.R_OFFICE, RoomType.R_OFFICE, RoomType.R_OFFICE, RoomType.R_OFFICE, RoomType.R_OFFICE, RoomType.R_OFFICE},
            {RoomType.NO_ROOM, RoomType.MC_KITCHEN, RoomType.MC_KITCHEN, RoomType.MC_KITCHEN, RoomType.MC_KITCHEN, RoomType.MC_KITCHEN, RoomType.MC_KITCHEN, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.R_OFFICE, RoomType.R_OFFICE, RoomType.R_OFFICE, RoomType.R_OFFICE, RoomType.R_OFFICE, RoomType.R_OFFICE},
            {RoomType.NO_ROOM, RoomType.MC_KITCHEN, RoomType.MC_KITCHEN, RoomType.MC_KITCHEN, RoomType.MC_KITCHEN, RoomType.MC_KITCHEN, RoomType.MC_KITCHEN, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.R_OFFICE, RoomType.R_OFFICE, RoomType.R_OFFICE, RoomType.R_OFFICE, RoomType.R_OFFICE, RoomType.R_OFFICE},
            {RoomType.NO_ROOM, RoomType.MC_KITCHEN, RoomType.MC_KITCHEN, RoomType.MC_KITCHEN, RoomType.MC_KITCHEN, RoomType.MC_KITCHEN, RoomType.MC_KITCHEN, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.R_OFFICE, RoomType.R_OFFICE, RoomType.R_OFFICE, RoomType.R_OFFICE, RoomType.R_OFFICE, RoomType.R_OFFICE},
            {RoomType.NO_ROOM, RoomType.NO_ROOM, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.R_OFFICE, RoomType.R_OFFICE, RoomType.R_OFFICE, RoomType.R_OFFICE, RoomType.R_OFFICE, RoomType.NO_ROOM},
            {RoomType.NO_ROOM, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.MC_LIVINGROOM, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR},
            {RoomType.NO_ROOM, RoomType.CORRIDOR, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.NO_ROOM},
            {RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.GELLERHOUSE, RoomType.GELLERHOUSE, RoomType.GELLERHOUSE, RoomType.GELLERHOUSE, RoomType.GELLERHOUSE, RoomType.GELLERHOUSE, RoomType.GELLERHOUSE},
            {RoomType.NO_ROOM, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CORRIDOR, RoomType.GELLERHOUSE, RoomType.GELLERHOUSE, RoomType.GELLERHOUSE, RoomType.GELLERHOUSE, RoomType.GELLERHOUSE, RoomType.GELLERHOUSE, RoomType.GELLERHOUSE},
            {RoomType.NO_ROOM, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CORRIDOR, RoomType.GELLERHOUSE, RoomType.GELLERHOUSE, RoomType.GELLERHOUSE, RoomType.GELLERHOUSE, RoomType.GELLERHOUSE, RoomType.GELLERHOUSE, RoomType.GELLERHOUSE},
            {RoomType.NO_ROOM, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CORRIDOR, RoomType.GELLERHOUSE, RoomType.GELLERHOUSE, RoomType.GELLERHOUSE, RoomType.GELLERHOUSE, RoomType.GELLERHOUSE, RoomType.GELLERHOUSE, RoomType.GELLERHOUSE},
            {RoomType.NO_ROOM, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CORRIDOR, RoomType.GELLERHOUSE, RoomType.GELLERHOUSE, RoomType.GELLERHOUSE, RoomType.GELLERHOUSE, RoomType.GELLERHOUSE, RoomType.GELLERHOUSE, RoomType.GELLERHOUSE},
            {RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CELLAR, RoomType.CORRIDOR, RoomType.GELLERHOUSE, RoomType.GELLERHOUSE, RoomType.GELLERHOUSE, RoomType.GELLERHOUSE, RoomType.GELLERHOUSE, RoomType.GELLERHOUSE, RoomType.GELLERHOUSE},
            {RoomType.NO_ROOM, RoomType.CORRIDOR, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CENTRALPERK, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.NO_ROOM},
            {RoomType.NO_ROOM, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR},
            {RoomType.NO_ROOM, RoomType.NO_ROOM, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.P_APARTMENT, RoomType.P_APARTMENT, RoomType.P_APARTMENT, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.ALESANDROS, RoomType.ALESANDROS, RoomType.ALESANDROS, RoomType.NO_ROOM},
            {RoomType.NO_ROOM, RoomType.J_KITCHEN, RoomType.J_KITCHEN, RoomType.J_KITCHEN, RoomType.J_KITCHEN, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.J_LIVINGROOM, RoomType.J_LIVINGROOM, RoomType.J_LIVINGROOM, RoomType.J_LIVINGROOM, RoomType.J_LIVINGROOM, RoomType.CORRIDOR, RoomType.P_APARTMENT, RoomType.P_APARTMENT, RoomType.P_APARTMENT, RoomType.P_APARTMENT, RoomType.P_APARTMENT, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.ALESANDROS, RoomType.ALESANDROS, RoomType.ALESANDROS, RoomType.ALESANDROS},
            {RoomType.NO_ROOM, RoomType.J_KITCHEN, RoomType.J_KITCHEN, RoomType.J_KITCHEN, RoomType.J_KITCHEN, RoomType.J_KITCHEN, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.J_LIVINGROOM, RoomType.J_LIVINGROOM, RoomType.J_LIVINGROOM, RoomType.J_LIVINGROOM, RoomType.J_LIVINGROOM, RoomType.CORRIDOR, RoomType.P_APARTMENT, RoomType.P_APARTMENT, RoomType.P_APARTMENT, RoomType.P_APARTMENT, RoomType.P_APARTMENT, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.ALESANDROS, RoomType.ALESANDROS, RoomType.ALESANDROS, RoomType.ALESANDROS},
            {RoomType.NO_ROOM, RoomType.J_KITCHEN, RoomType.J_KITCHEN, RoomType.J_KITCHEN, RoomType.J_KITCHEN, RoomType.J_KITCHEN, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.J_LIVINGROOM, RoomType.J_LIVINGROOM, RoomType.J_LIVINGROOM, RoomType.J_LIVINGROOM, RoomType.J_LIVINGROOM, RoomType.CORRIDOR, RoomType.P_APARTMENT, RoomType.P_APARTMENT, RoomType.P_APARTMENT, RoomType.P_APARTMENT, RoomType.P_APARTMENT, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.ALESANDROS, RoomType.ALESANDROS, RoomType.ALESANDROS, RoomType.ALESANDROS},
            {RoomType.NO_ROOM, RoomType.J_KITCHEN, RoomType.J_KITCHEN, RoomType.J_KITCHEN, RoomType.J_KITCHEN, RoomType.J_KITCHEN, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.J_LIVINGROOM, RoomType.J_LIVINGROOM, RoomType.J_LIVINGROOM, RoomType.J_LIVINGROOM, RoomType.J_LIVINGROOM, RoomType.CORRIDOR, RoomType.P_APARTMENT, RoomType.P_APARTMENT, RoomType.P_APARTMENT, RoomType.P_APARTMENT, RoomType.P_APARTMENT, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.ALESANDROS, RoomType.ALESANDROS, RoomType.ALESANDROS, RoomType.ALESANDROS},
            {RoomType.NO_ROOM, RoomType.J_KITCHEN, RoomType.J_KITCHEN, RoomType.J_KITCHEN, RoomType.J_KITCHEN, RoomType.J_KITCHEN, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.J_LIVINGROOM, RoomType.J_LIVINGROOM, RoomType.J_LIVINGROOM, RoomType.J_LIVINGROOM, RoomType.J_LIVINGROOM, RoomType.CORRIDOR, RoomType.P_APARTMENT, RoomType.P_APARTMENT, RoomType.P_APARTMENT, RoomType.P_APARTMENT, RoomType.P_APARTMENT, RoomType.CORRIDOR, RoomType.CORRIDOR, RoomType.ALESANDROS, RoomType.ALESANDROS, RoomType.ALESANDROS, RoomType.ALESANDROS},
            {RoomType.NO_ROOM, RoomType.J_KITCHEN, RoomType.J_KITCHEN, RoomType.J_KITCHEN, RoomType.J_KITCHEN, RoomType.NO_ROOM, RoomType.CORRIDOR, RoomType.NO_ROOM, RoomType.J_LIVINGROOM, RoomType.J_LIVINGROOM, RoomType.J_LIVINGROOM, RoomType.J_LIVINGROOM, RoomType.J_LIVINGROOM, RoomType.NO_ROOM, RoomType.NO_ROOM, RoomType.P_APARTMENT, RoomType.P_APARTMENT, RoomType.P_APARTMENT, RoomType.NO_ROOM, RoomType.CORRIDOR, RoomType.NO_ROOM, RoomType.ALESANDROS, RoomType.ALESANDROS, RoomType.ALESANDROS, RoomType.ALESANDROS},
        };
        
        // loop through 2-D board array setting the roomType for each tile
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 25; j++) {
                tile[i][j].setRoomType(roomTypes[i][j]);
            }
        }
    }

    public Room getRoomByType(RoomType type){
        for (int i = 0; i < rooms.length; i++){
            if (rooms[i].getRoomType() == type)
                return rooms[i];
        }
        return null;
    }

    // initialise the rooms and assign them to the rooms array where their order is the same as the enum. The x and y parameters are the coordinates of the centre tile of the room
    public void initialiseRooms() {
        rooms = new Room[10];
        rooms[0] = new Room(this, 2, 3, RoomType.MC_KITCHEN, new Tile[] {tile[4][7]});
        rooms[1] = new Room(this, 4, 13, RoomType.MC_LIVINGROOM, new Tile[] {tile[8][12], tile[6][16]});
        rooms[2] = new Room(this, 3, 22, RoomType.R_OFFICE, new Tile[] {tile[5][18]});
        rooms[3] = new Room(this, 12, 4, RoomType.CENTRALPERK, new Tile[] {tile[7][5], tile[9][8], tile[14][8], tile[16][5]});
        rooms[4] = new Room(this, 12, 13, RoomType.CELLAR, new Tile[] {tile[11][17], tile[12][17], tile[13][17]});
        rooms[5] = new Room(this, 11, 21, RoomType.GELLERHOUSE, new Tile[] {tile[11][17], tile[12][17], tile[15][20]});
        rooms[6] = new Room(this, 20, 3, RoomType.J_KITCHEN, new Tile[] {tile[18][5]});
        rooms[7] = new Room(this, 21, 10, RoomType.J_LIVINGROOM, new Tile[] {tile[17][9], tile[22][13]});
        rooms[8] = new Room(this, 21, 16, RoomType.P_APARTMENT, new Tile[] {tile[20][13], tile[16][16]});
        rooms[9] = new Room(this, 21, 22, RoomType.ALESANDROS, new Tile[] {tile[17][20]});
        
        rooms[RoomType.MC_KITCHEN.ordinal()].setSecretPassage(rooms[RoomType.ALESANDROS.ordinal()]);
        rooms[RoomType.ALESANDROS.ordinal()].setSecretPassage(rooms[RoomType.MC_KITCHEN.ordinal()]);
        rooms[RoomType.R_OFFICE.ordinal()].setSecretPassage(rooms[RoomType.J_KITCHEN.ordinal()]);
        rooms[RoomType.J_KITCHEN.ordinal()].setSecretPassage(rooms[RoomType.R_OFFICE.ordinal()]);
    }
}
