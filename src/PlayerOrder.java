import java.util.Arrays;

public class PlayerOrder {
    // class to contain a reference to the player and the dice number that they rolled
    class DiceRollPlayer {
        private int diceRoll;
        private Player player;

        DiceRollPlayer(Player players) {
            this.player = players;
        }

        public int getDiceRoll() {
            return diceRoll;
        }

        public void setDiceRoll(int diceRoll) {
            this.diceRoll = diceRoll;
        }

        public Player getPlayer() {
            return player;
        }
    }

    private DiceRollPlayer[] diceRollPlayers;
    private UI ui;
    private final DicePanel dicePanel;

    PlayerOrder(CircularlyLinkedList<Player> players, UI ui, DicePanel dicePanel) {
        diceRollPlayers = new DiceRollPlayer[players.getSize()];

        // store the players in the diceRollPlayers array
        int playerNum = 0;
        for (Player player : players) {
            diceRollPlayers[playerNum] = new DiceRollPlayer(player);
            playerNum++;
        }

        this.ui = ui;
        this.dicePanel = dicePanel;
    }

    public CircularlyLinkedList<Player> playerStartOrder(CircularlyLinkedList<Player> players) {
        ui.getInfo().addText("All players will roll the dice to find the order that you will play, whoever rolls the highest number goes first, the second highest next, so on and so forth");
        rollDice(0, diceRollPlayers.length-1); // get each of the players to roll the dice
        sortPlayers(0, diceRollPlayers.length-1); //  sort the players highest to lowest
        checkForTies(0, diceRollPlayers.length-1); // check if there are any players that have rolled the same number, if so get them to roll again

        // put the array into a circularly linked list in the order that the players will play
        CircularlyLinkedList<Player> orderedPlayers = new CircularlyLinkedList<Player>();
        for (int i = 0; i < diceRollPlayers.length; i++) {
            orderedPlayers.addLast(diceRollPlayers[i].getPlayer());
        }

        return orderedPlayers; // return circularly linked list in the order that the players will play
    }

    // get each of the players to roll the dice between start and end inclusive
    private void rollDice(int start, int end) {
        int playerNum = 0;

        // loop through the players between start and end integers inclusive
        for (int i = start; i <= end; i++) {
            ui.getInfo().addText(diceRollPlayers[i].getPlayer().getPlayerName() + ", type \"roll\" to roll the dice.");

            String cmdInput = ui.getCmd().getCommand().trim(); //read the command entered by the user
            String name = diceRollPlayers[i].getPlayer().getPlayerName(); // get the name of the player

            // check that the right command has been entered, if not, inform the player and ask them to enter roll again
            while (!cmdInput.equals("roll")) {
                if (cmdInput.equals("help")) {
                    ui.getInfo().addText("Enter \"roll\" to roll the dice to order all of the players, the player with the highest will go first, second highest second, so on and so forth");
                } else {
                    ui.getInfo().addText(name + ", you can only roll the dice right now, try again!");
                }
                cmdInput = ui.getCmd().getCommand();
            }

            dicePanel.waitToFinish();
            dicePanel.start(); // roll the dice
            int diceRollNum = dicePanel.getTotalDiceNumber();
            ui.getInfo().addText(name + " rolled " + diceRollNum);

            diceRollPlayers[i].setDiceRoll(diceRollNum);
        }
    }

    //  sort the players highest to lowest between start and end integers inclusive
    private void sortPlayers(int start, int end) {
        for (int i = start; i <= end-1; i++) {
            int maxDiceRollIndex = i;
            for (int j = i+1; j <= end; j++) {
                if (diceRollPlayers[j].getDiceRoll() > diceRollPlayers[maxDiceRollIndex].getDiceRoll()) {
                    maxDiceRollIndex = j;
                }
            }

            // swap
            DiceRollPlayer temp = diceRollPlayers[maxDiceRollIndex];
            diceRollPlayers[maxDiceRollIndex] = diceRollPlayers[i];
            diceRollPlayers[i] = temp;
        }
    }

    // check if there are any players that have rolled the same number, if so get them to roll again between start and end integers inclusive
    private void checkForTies(int start, int end) {
        int player = start;
        while (player < diceRollPlayers.length) {
            int tieIndex = player;

            /* change while loop as its only checking the ones beforehand not the ones after i.e 5, 5, 5 its only checking the first to five and then doing recusion, go other way */

            // find the index of the last player in the array that has rolled the same number, NOTE: players are ordered
            while (tieIndex < end && diceRollPlayers[tieIndex].getDiceRoll() == diceRollPlayers[tieIndex+1].getDiceRoll()) {
                tieIndex++;
            }

            // check to see if more than one player has had the same dice roll number
            if (tieIndex > player) {
                // get a string containing all of the players names who have the same dice roll number
                String names = "";
                for (int i = player; i <= tieIndex; i++) {
                    if (i == tieIndex) {
                        names = names.substring(0, names.length()-2) + " and " + diceRollPlayers[i].getPlayer().getPlayerName();
                    } else {
                        names += diceRollPlayers[i].getPlayer().getPlayerName() + ", ";
                    }
                }

                ui.getInfo().addText(names + " have rolled the same number, you will have to roll again");

                // ask players to roll again, sort those players again and check for ties again
                rollDice(player, tieIndex);
                sortPlayers(player, tieIndex);
                checkForTies(player, tieIndex);

                player = tieIndex++;
            } else {
                player++;
            }
        }
    }
}
