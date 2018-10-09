import java.util.Random;

/*
 * The class Envelope stores a random character, weapon and room card which are the solution of the game
 *
 * 16310943 James Byrne
 * 16314763 Jakub Gajewski
 * 16305706 Mark Hartnett
 */

public class Envelope{
    private Card weapon;
    private Card murderer;
    private Card location;

    /** Generates random cards to be stored in the envelope */
    public Envelope(){
        weapon = new Card(getRandomWeapon().toString());
        murderer = new Card(getRandomCharacter().toString());
        location = new Card(getRandomRoom().toString());
    }

    private WeaponTypes getRandomWeapon(){
        Random random = new Random();
        return WeaponTypes.values()[random.nextInt(WeaponTypes.values().length)];
    }

    private CharacterNames getRandomCharacter(){
        Random random = new Random();
        return CharacterNames.values()[random.nextInt(CharacterNames.values().length)];
    }

    private RoomType getRandomRoom(){
        Random random = new Random();
        RoomType room = null;
        while (room == null || room == RoomType.NO_ROOM || room == RoomType.CORRIDOR || room == RoomType.CELLAR){
            room = RoomType.values()[random.nextInt(RoomType.values().length)];
        }
        return room;
    }

    public Card getMurderer() {
        return murderer;
    }

    public Card getWeapon() {
        return weapon;
    }

    public Card getLocation() {
        return location;
    }
}
