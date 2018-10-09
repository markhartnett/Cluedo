import java.awt.*;
import java.util.LinkedList;
import javax.swing.*;

/*
 * The class Log stores all questions and responses during the game.
 *
 * 16310943 James Byrne
 * 16314763 Jakub Gajewski
 * 16305706 Mark Hartnett
 */

public class Log {
    /*
     * The class LogElement represents a single entry in the log, specifying the player asking the question, the card
     * shown to them and the player showing the card.
     */
    private class LogElement{
        private Player playerAsking;
        private Question questionAsked;
        private String cardShown;
        private Player playerShowingCard;

        /** Constructor for LogElement where a card has been shown by a player */
        public LogElement(Player playerAsking, Question question, String card, Player playerShowing){
            this.playerAsking = playerAsking;
            questionAsked = question;
            cardShown = card;
            playerShowingCard = playerShowing;
        }

        /** Constructor for LogElement where no card has been shown by any player */
        public LogElement(Player playerAsking, Question question){
            this.playerAsking = playerAsking;
            questionAsked = question;
        }
    }
    private static final int TEXT_AREA_HEIGHT = 38;
    private static final int CHARACTER_WIDTH = 60;
    private static final int FONT_SIZE = 12;
    private final LinkedList<LogElement> list = new LinkedList<>();

    /** Adds a question which has been answered and a card has been shown */
    public void addEntryWithReply(Player playerAsking, Question question, String card, Player playerShowing){
        list.add(new LogElement(playerAsking, question, card, playerShowing));
    }

    /** Adds a quetion to which no card has been shown */
    public void addEntryWithoutReply(Player playerAsking, Question question){
        list.add(new LogElement(playerAsking, question));
    }

    /**
     * Displays a new JFrame showing all the questions asked during the current game.
     *
     * @param playerRequesting the player who requested the log to be shown
     */
    public void showLog(Player playerRequesting){
        JFrame log = new JFrame("Log");
        JTextArea textArea = new JTextArea(TEXT_AREA_HEIGHT, CHARACTER_WIDTH);
        JScrollPane scrollPane = new JScrollPane(textArea);
        log.setSize(400, 500);
        log.setLocation(1000,0);
        log.setResizable(false);

        textArea.setEditable(false);
        textArea.setFont(new Font("monospaced", Font.PLAIN, FONT_SIZE));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setText(getLog(playerRequesting));

        log.setLayout(new BorderLayout());
        log.add(scrollPane, BorderLayout.CENTER);
        log.setVisible(true);
    }

    /**
     * Creates a string representation of all entries in the log.
     * If the requesting player did not ask some question or did not show a card, the card
     * shown will be replaced with "***".
     *
     * @param playerRequesting the player who requested the log to be shown
     */
    private String getLog(Player playerRequesting){
        String log = "";
        if (list.isEmpty())
            log += "Log is empty.";
        else {
            for (LogElement element : list) {
                log += "• " + element.playerAsking.getPlayerName() + " questions: " + element.questionAsked.getLocation() + ", " + element.questionAsked.getMurderer() + ", " + element.questionAsked.getWeapon() + "\n  ";
                if (element.cardShown == null)
                    log += "no card shown\n";
                else {
                    if (element.playerAsking == playerRequesting || element.playerShowingCard == playerRequesting)
                        log += element.cardShown + " shown by " + element.playerShowingCard.getPlayerName() + "\n";
                    else
                        log += "*** shown by " + element.playerShowingCard.getPlayerName() + "\n";
                }
            }
        }
        return log;
    }
}
