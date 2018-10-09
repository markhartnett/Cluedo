import java.util.ArrayList;
import java.util.Collection;

/*
 * 16310943 James Byrne
 * 16314763 Jakub Gajewski
 * 16305706 Mark Hartnett
 *
 * The Player class is the class that contains the information of the
 * person playing the game.
 */
public class Player {
    //name of the human playing the game
    private String playerName;

    private boolean playing = true;

    private final ArrayList<Card> cards = new ArrayList<>();

    //the character token that the player has chosen to move around the room
    private Character playerToken;

    //the note page each player has to record the cards they have and have seen
    private Notes playerNotes;
    
    //constructor of the player object
    public Player(String playerName, Character playerToken){
        this.playerName = playerName;
        this.playerToken = playerToken;
        this.playerNotes = new Notes();
    }

    public boolean hasName(String name) {
        return this.playerName.toLowerCase().equals(name.trim());
    }
    
    //access playerName
    public String getPlayerName() {
        return playerName;
    }
    
    //access playerToken
    public Character getPlayerToken() {
        return playerToken;
    }
    
    //access the playerNotes
    public Notes getPlayerNotes() {
        return playerNotes;
    }

    public void setPlaying(boolean play){
        playing = play;
    }

    public boolean isPlaying() {
        return playing;
    }

    public Collection<Card> getCards(){
        return cards;
    }

    @Override
    public String toString() {
        return playerName + " (" + playerToken.getName() + ")";
    }
}
