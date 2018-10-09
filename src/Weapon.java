/*
 * The Weapon class is a subclass of Token and specifies the type of weapon this token should represent.
 */

/*
16310943 James Byrne
16314763 Jakub Gajewski
16305706 Mark Hartnett
 */

public class Weapon extends Token{
    private WeaponTypes type;

    public Weapon(Tile currentTile, WeaponTypes type){
        super(currentTile);
        this.type = type;
    }

    public WeaponTypes getType() {
        return type;
    }
}