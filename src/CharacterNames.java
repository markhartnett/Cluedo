/*
 * Enum used to specify the name of each playable character.
 *
 * @author Jakub Gajewski
 */

/*
16310943 James Byrne
16314763 Jakub Gajewski
16305706 Mark Hartnett
 */

public enum CharacterNames {
    PHOEBE, RACHEL, ROSS, JOEY, MONICA, CHANDLER;

    public static CharacterNames getValue(String str) {
        switch (str.trim().toLowerCase()) {
            case "mustard":
            case "joey":
                 return CharacterNames.JOEY;
            case "scarlet":
            case "phoebe":
                return CharacterNames.PHOEBE;
            case "white":
            case "monica":
                return CharacterNames.MONICA;
            case "green":
            case "chandler":
                return CharacterNames.CHANDLER;
            case "plum":
            case "ross":
                return CharacterNames.ROSS;
            case "peacock":
            case "rachel":
                return CharacterNames.RACHEL;
            default:
                return null;
        }
    }
}
