/*
 * Enum used to specify the type of each weapon token in the game.
 */

/*
16310943 James Byrne
16314763 Jakub Gajewski
16305706 Mark Hartnett
 */

public enum WeaponTypes {
    ROPE, DAGGER, WRENCH, PISTOL, CANDLESTICK, PIPE;

    public static WeaponTypes getValue(String str) {
        switch (str.trim().toLowerCase()) {
            case "rope":
                return WeaponTypes.ROPE;
            case "dagger":
                return WeaponTypes.DAGGER;
            case "wrench":
                return WeaponTypes.WRENCH;
            case "pistol":
                return WeaponTypes.PISTOL;
            case "candlestick":
            case "candle stick":
                return WeaponTypes.CANDLESTICK;
            case "pipe":
                return WeaponTypes.PIPE;
            default:
                return null;
        }
    }
}
