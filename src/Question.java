/*
 * Class that represents the player questions, where they select the murderer and the murder weapon
 */
public class Question {
    private Card weapon;
    private Card murderer;
    private Card location;

    private final static WeaponTypes[] WEAPON_NAMES = {WeaponTypes.ROPE, WeaponTypes.DAGGER, WeaponTypes.WRENCH, WeaponTypes.PISTOL, WeaponTypes.CANDLESTICK, WeaponTypes.PIPE};
    private final static CharacterNames[] CHARACTER_NAMES = {CharacterNames.JOEY, CharacterNames.MONICA, CharacterNames.CHANDLER, CharacterNames.PHOEBE, CharacterNames.RACHEL, CharacterNames.ROSS}; // list of players

    // constructor for class
    public Question(RoomType room){
        location = new Card(room.toString());
        murderer = null;
        weapon = null;
    }

    // ask the player what the murder weapon and who the murderer was
    public void ask(UI ui, Player currentPlayer) {
        murderer = murdererName(ui, currentPlayer); // ask retrieve and validate who the player thinks the murderer is
        ui.getInfo().addText("You chose " + murderer.getName() + " as the murderer"); // inform user of data received from them
        weapon = murderWeapon(ui, currentPlayer); // ask retrieve and validate what the player thinks the murder weapon was
        ui.getInfo().addText("You chose " + weapon.getName() + " as the murder weapon"); // inform user of data received from them
        ui.getInfo().addText(murderer.getName() + " killed Gunther with a " + weapon.getName() + " in the " + currentPlayer.getPlayerToken().getCurrentTile().getRoomType()); // display the overall information
    }

    // ask retrieve and validate who the player thinks the murderer is
    private Card murdererName(UI ui, Player currentPlayer) {
        CardPanel cardPanel = new CardPanel(ui.getBoard(), ui.getCmd(), "characters");
        ui.getLayers().add(cardPanel, Integer.valueOf(6));
        cardPanel.showPanel();
        ui.getInfo().addText("Enter the name of the character you want to question from the list below"); // prompt

        // display all the characters to the player that they can select
        for (int i = 0; i < CHARACTER_NAMES.length; i++) {
            ui.getInfo().addText(CHARACTER_NAMES[i].toString().substring(0,1) + CHARACTER_NAMES[i].toString().substring(1).toLowerCase());
        }

        CharacterNames murderer = null;
        String murderersName;
        // check the input from the user whether it is a character name, help command, notes command or the wrong input
        do {
            murderersName = ui.getCmd().getCommand().toLowerCase();
            switch (murderersName) {
                case "mustard":
                case "joey":
                    System.out.println("MERP");
                    murderer = CharacterNames.JOEY;
                    System.out.println("DERP");
                    break;
                case "scarlet":
                case "phoebe":
                    murderer = CharacterNames.PHOEBE;
                    break;
                case "white":
                case "monica":
                    murderer = CharacterNames.MONICA;
                    break;
                case "green":
                case "chandler":
                    murderer = CharacterNames.CHANDLER;
                    break;
                case "plum":
                case "ross":
                    murderer = CharacterNames.ROSS;
                    break;
                case "peacock":
                case "rachel":
                    murderer = CharacterNames.RACHEL;
                    break;
                case "notes":
                    currentPlayer.getPlayerNotes().showNotes(currentPlayer);
                    break;
                case "help":
                    ui.getInfo().addText("Enter the player from the list above to question!");
                    break;
                default:
                    ui.getInfo().addText("That was an invalid entry, enter ther player from the list above to question!");
                    break;
            }
        } while (murderer == null); // while the player input was not a character

        cardPanel.removePanel();
        ui.getLayers().remove(cardPanel);
        return new Card(murderer.toString()); // return a card with the murderer selected by the user
    }

    // ask retrieve and validate what the player thinks the murder weapon was
    private Card murderWeapon(UI ui, Player currentPlayer) {
        CardPanel cardPanel = new CardPanel(ui.getBoard(), ui.getCmd(), "weapons");
        ui.getLayers().add(cardPanel, Integer.valueOf(6));
        cardPanel.showPanel();
        ui.getInfo().addText("Enter the weapon you think " + murderer.getName() + " killed Gunther in " + location.getName() + ". Listed Below"); // prompt

        // display all the characters to the player that they can select
        for (int i = 0; i < WEAPON_NAMES.length; i++) {
            ui.getInfo().addText(WEAPON_NAMES[i].toString().substring(0,1) + WEAPON_NAMES[i].toString().substring(1).toLowerCase());
        }

        WeaponTypes murderWeapon = null;
        String weapon;

        // check the input from the user whether it is a weapon, help command, notes command or the wrong input
        do {
            weapon = ui.getCmd().getCommand().toLowerCase();
            switch (weapon) {
                case "rope":
                    murderWeapon = WeaponTypes.ROPE;
                    break;
                case "dagger":
                    murderWeapon = WeaponTypes.DAGGER;
                    break;
                case "wrench":
                    murderWeapon = WeaponTypes.WRENCH;
                    break;
                case "pistol":
                    murderWeapon = WeaponTypes.PISTOL;
                    break;
                case "candlestick":
                    murderWeapon = WeaponTypes.CANDLESTICK;
                    break;
                case "pipe":
                    murderWeapon = WeaponTypes.PIPE;
                    break;
                case "notes":
                    currentPlayer.getPlayerNotes().showNotes(currentPlayer);
                    break;
                case "help":
                    ui.getInfo().addText("Select a weapon from the list above for the questioning!");
                    break;
                default:
                    ui.getInfo().addText("That was an invalid entry, enter a weapon from the list above for the questioning!");
                    break;
            }
        } while (murderWeapon == null); // while the player input was not a weapon

        cardPanel.removePanel();
        ui.getLayers().remove(cardPanel);
        return new Card(murderWeapon.toString()); // return a card with the murder weapon selected by the user
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
