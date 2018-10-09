
/*
16310943 James Byrne
16314763 Jakub Gajewski
16305706 Mark Hartnett
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Predicate;

public class Cluelessdo {
    private final TokenController tokenPanel;
    private final DicePanel dicePanel;
    private final IntroScreen introScreen;
    private UI ui;
    private CircularlyLinkedList<Player> players = new CircularlyLinkedList<>();
    private Iterator<Player> playerIterator;
    private int numberOfPlayersPlaying = 0;
    private boolean running;
    private ArrayList<Card> publicCards = new ArrayList<>();    // List of cards visible to all players. Initially contains all cards
    private final Envelope envelope = new Envelope();
    private final Log log = new Log();

    private final CharacterNames[] CHARACTER_NAMES = {CharacterNames.JOEY, CharacterNames.MONICA, CharacterNames.CHANDLER, CharacterNames.PHOEBE, CharacterNames.RACHEL, CharacterNames.ROSS};
    private final WeaponTypes[] WEAPON_NAMES = {WeaponTypes.CANDLESTICK, WeaponTypes.DAGGER, WeaponTypes.ROPE, WeaponTypes.PIPE, WeaponTypes.PISTOL, WeaponTypes.WRENCH};

    Cluelessdo() throws IOException {
        ui = new UI();
        tokenPanel = new TokenController(ui.getBoard().getMap(),ui.getBoard());     // Token drawing panel
        dicePanel = new DicePanel(ui.getBoard());
        introScreen = new IntroScreen(ui.getBoard(), tokenPanel, players);
        ui.getLayers().add(tokenPanel, Integer.valueOf(2));
        ui.getLayers().add(dicePanel, Integer.valueOf(3));
        ui.getLayers().add(introScreen, Integer.valueOf(9));
        running = true;
    }

    public void waitForPlayersInput(){
        Audio intro = new Audio(Sounds.INTRO);
        synchronized (players){
            try{
                players.wait();
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        numberOfPlayersPlaying = players.getSize();
        ui.getLayers().remove(introScreen);
        ui.repaint();
        intro.stop();
    }

    /**
     * checks to see if the move is valid, if valid moves the player, if not prints error message onto info panel
     * @return returns false if move is illegal, true if move has been made successfully
     */
    public boolean moveCharacter(Character playerToken, Direction dir, String playerName) {
        Tile sourceTile = playerToken.getCurrentTile();
        int returnCode = playerToken.moveToken(dir, ui.getBoard().getMap());
        if (returnCode != 0) {
            playerName = playerName.substring(0, 1) + playerName.substring(1).toLowerCase(); // capitalise the first letter and set the rest to lower case
            String errorMessage = playerName + " cannot move " + dir.toString().toLowerCase() + ": "; // make error message
            switch(returnCode){
                case 1:
                    errorMessage += "Cannot move through a wall.";
                    break;
                case 2:
                    errorMessage += "Cannot move through a token.";
                    break;
                case 3:
                    errorMessage += "Cannot return to the room which you left in the same turn.";
                    break;
            }
            ui.getInfo().addText(errorMessage); // add error message to info panel
            return false; // move not successful
        }
        tokenPanel.animateMovement(playerToken, sourceTile);
        return true; // move successful
    }

    /**
     * checks to see if the room occupied by the player has a secret passage and attempts to use it if it does
     * @return false if move is illegal
     */
    public boolean moveSecretPassage(Character playerToken) {
        Tile sourceTile = playerToken.getCurrentTile();
        Room currRoom = ui.getBoard().getMap().getRoom(playerToken.getCurrentTile().getRoomType().ordinal()); // get the room that the player is currently in
        if (currRoom.hasSecretPasssage()) {
            Room nextRoom = currRoom.getSecretPassage(); // get the room that the secret passage brings players to
            playerToken.moveToken(nextRoom.addToken()); // move the player to the token in the room that the secret passage is connected to
            tokenPanel.animateMovement(playerToken, sourceTile);
            return true; // successful
        } else {
            return false; // cannot move player
        }
    }

    public boolean isRunning(){
        return running;
    }

    /**
     * Gets a string from the command panel and checks if it is a valid game command string. If yes, returns
     * a CommandTypes value corresponding to that command string.
     * For the command "quit" prompts the user to confirm and ends the program.
     */
    private CommandTypes doCommand(){
        String command = ui.getCmd().getCommand().toLowerCase();
        switch (command){
            case "roll": return CommandTypes.ROLL;
            case "u": case "up": return CommandTypes.MOVE_UP;
            case "d": case "down": return CommandTypes.MOVE_DOWN;
            case "l": case "left": return CommandTypes.MOVE_LEFT;
            case "r": case "right": return CommandTypes.MOVE_RIGHT;
            case "done": return CommandTypes.DONE;
            case "passage": case "pass": return CommandTypes.PASSAGE;
            case "notes": return CommandTypes.NOTES;
            case "cheat": return CommandTypes.CHEAT;
            case "question": return CommandTypes.QUESTION;
            case "accuse" : return CommandTypes.ACCUSE;
            case "log" : return CommandTypes.LOG;
            case "quit": case "exit":
                ui.getInfo().addText("Are you sure you want to quit? (y/n)");
                boolean loop = true;
                do{
                    switch (ui.getCmd().getCommand()){
                        case "y":
                        case "yes":
                            System.exit(0);
                            break;
                        case "n":
                        case "no":
                            ui.getInfo().addText("Welcome back to the game!");
                            loop = false;
                            break;
                    }
                } while (loop);
                break;
            case "help":
                return CommandTypes.HELP;
            default:
                ui.getInfo().addText("Invalid command: \"" + command + "\"");
                return CommandTypes.INVALID;
        }
        return CommandTypes.INVALID;
    }

    public void dealCards(){
        /** Remove cards already present in the envelope */
        Predicate<Card> predicate = (Card c) -> c.getEnumName().equals(envelope.getLocation().getEnumName()) || c.getEnumName().equals(envelope.getMurderer().getEnumName()) || c.getEnumName().equals(envelope.getWeapon().getEnumName());
        publicCards.removeIf(predicate);

        /** Distribute public cards among players */
        int cardCount = 0;
        while (cardCount + numberOfPlayersPlaying <= publicCards.size()){
            for (int i = 0; i < players.getSize(); i++){
                Player player = playerIterator.next();
                Card card = publicCards.get(0);
                player.getCards().add(card);
                publicCards.remove(0);
                player.getPlayerNotes().getNoteItem(card.getEnumName()).setOwned();
            }
        }
        for (Card tmp: publicCards){
            for (int i = 0; i < players.getSize(); i++){
                Player player = playerIterator.next();
                player.getPlayerNotes().getNoteItem(tmp.getEnumName()).setShared();
            }
        }
    }

    /**
     * Prompts each player to roll the dice to determine the order in which they will be sorted
     */
    public void sortPlayers(){
        // get the players to roll the dice and then order them in that order for them to play
        PlayerOrder playerOrder = new PlayerOrder(players, ui, dicePanel);
        players = playerOrder.playerStartOrder(players);

        // get the name of each of the players in the order that they are going to play
        String names = "";
        int playerNum = 0;
        for (Player player : players) {
            if (playerNum == players.getSize()-1) {
                names = names.substring(0, names.length()-2) + " and then " + player.getPlayerName();
            } else {
                names += player.getPlayerName() + ", ";
            }
            playerNum++;
        }

        ui.getInfo().addText("The player order is " + names + "\nPress ENTER to start game!");

        String command = ui.getCmd().getCommand();

        ui.getInfo().clear();
        playerIterator = players.iterator();
    }

    /**
     * Method to control the progression of a single turn, i.e. dice roll, all movement and number of moves remaining
     * @param currentPlayer the player to take the turn
     */
    public void playTurn(Player currentPlayer){
        CommandTypes command;
        int numberOfMoves = 0;
        boolean questionAsked = false;
        ui.getInfo().addText(currentPlayer.getPlayerName() + " (" + currentPlayer.getPlayerToken().toString() + "), it's your turn! Type \"roll\" to roll the dice.");

        /** Execute dice roll and record the number on the dice */
        do{
            command = doCommand();
            if (command == CommandTypes.ROLL){
                dicePanel.waitToFinish();
                dicePanel.start();
                numberOfMoves = dicePanel.getTotalDiceNumber();
                ui.getInfo().addText("You rolled " + numberOfMoves);
                if (currentPlayer.getPlayerToken().getCurrentTile().getRoomType() == RoomType.CORRIDOR) { // if the character of the player is in the corridor
                    ui.getInfo().addText("Enter 'u', 'd', 'l' or 'r' to move up, down, left or right respectively, \"pass\" to move through the secret passage (if possible), \"notes\" to look at your notes, \"done\" when you are finished your turn");
                }
            } else if (command == CommandTypes.NOTES) {
                currentPlayer.getPlayerNotes().showNotes(currentPlayer);
            } else if (command == CommandTypes.LOG){
                log.showLog(currentPlayer);
            } else if (command == CommandTypes.HELP) { // if the player enters help
                ui.getInfo().addText("Enter \"roll\" to roll the dice or \"notes\" to display your notes");
            } else if (command == CommandTypes.CHEAT){
                ui.getInfo().addText(envelope.getMurderer().getName() + " in the " + envelope.getLocation().getName() + " with the " + envelope.getWeapon().getName());
            } else {
                ui.getInfo().addText("Invalid command. Enter \"roll\" to roll the dice or \"notes\" to display your notes");
            }

        } while (command != CommandTypes.ROLL);

        /** Overall control over player action after the dice roll. Main loop repeats until numberOfMoves
         *  reaches 0 through exhaustion of moves or the user typing "done" */
        do{
            /** Ask player to end the turn once numberOfMoves == 0 and the loop has repeated */
            if (numberOfMoves == 0) {
                do{
                    /** If a player is in the cellar they make an accusation*/
                    if(currentPlayer.getPlayerToken().getCurrentTile().getRoomType() == RoomType.CELLAR){
                        do {
                            ui.getInfo().addText("You are about to make an accusation, to check your notes one more time enter \"notes\" otherwise enter \"accuse\"");
                            command = doCommand();
                        }while (command != CommandTypes.ACCUSE && command != CommandTypes.NOTES && command != CommandTypes.CHEAT);

                        if (command == CommandTypes.NOTES){
                            currentPlayer.getPlayerNotes().showNotes(currentPlayer);
                        }
                        else if (command == CommandTypes.LOG){
                            log.showLog(currentPlayer);
                        }
                        else if (command == CommandTypes.CHEAT){
                            ui.getInfo().addText(envelope.getMurderer().getName() + " in the " + envelope.getLocation().getName() + " with the " + envelope.getWeapon().getName());
                        }
                        Accusation accuse = new Accusation();
                        accuse.ask(ui, CHARACTER_NAMES, WEAPON_NAMES, currentPlayer, envelope);

                        EnvelopePanel envelopePanel = new EnvelopePanel(ui.getBoard(), envelope, accuse);
                        ui.getLayers().add(envelopePanel, Integer.valueOf(11));
                        envelopePanel.displayEnvelope();

                        if(accuse.isCorrect(envelope)){
                            ui.getInfo().addText("Congratulations, you have won!");
                            VictoryPanel victoryPanel = new VictoryPanel(ui.getBoard(), currentPlayer);
                            ui.getLayers().add(victoryPanel, Integer.valueOf(15));
                            ui.getInfo().addText("Press ENTER to exit game");
                            command = doCommand();
                            System.exit(0);
                            return;
                        }

                        else{
                            currentPlayer.setPlaying(false);
                            numberOfPlayersPlaying--;
                            ui.getInfo().addText("Sorry that is incorrect, you are eliminated");
                            Audio fail = new Audio(Sounds.FAIL);
                            ui.getInfo().addText("Press ENTER to continue");
                            command = doCommand();
                            envelopePanel.removeEnvelope();
                            ui.getLayers().remove(envelopePanel);
                            return;
                        }
                    }

                    if (command == CommandTypes.NOTES) {
                        currentPlayer.getPlayerNotes().showNotes(currentPlayer);
                    }
                    if (command == CommandTypes.LOG){
                        log.showLog(currentPlayer);
                    }
                    else if (command == CommandTypes.CHEAT){
                        ui.getInfo().addText(envelope.getMurderer().getName() + " in the " + envelope.getLocation().getName() + " with the " + envelope.getWeapon().getName());
                    } else if (command == CommandTypes.QUESTION && currentPlayer.getPlayerToken().getCurrentTile().getRoomType() != RoomType.CORRIDOR) {
                        if (questionAsked) {
                            ui.getInfo().addText("You have already questioned and you are out of moves! Enter \"done\' to end yout turn or \"notes\" to look at your notes.");
                        } else {
                            questionAsked = true;
                            question(currentPlayer);
                        }
                    } else if (command == CommandTypes.QUESTION) {
                        ui.getInfo().addText("You have already questioned! Enter \"done\" to end yout turn or \"notes\" to look at your notes.");
                    } else { // if the player enters help or if the wrong input is entered (same message is displayed)
                        ui.getInfo().addText("You are out of moves! Type " + (questionAsked || currentPlayer.getPlayerToken().getCurrentTile().getRoomType() == RoomType.CORRIDOR ? "" : "\"question\" to make a question ") + "\"done\" to end your turn or enter \"notes\" to look at your notes.");
                    }
                    command = doCommand();
                } while (command != CommandTypes.DONE);

                if (currentPlayer.getPlayerToken().getCurrentTile().getRoomType() != RoomType.CORRIDOR)
                    currentPlayer.getPlayerToken().setRoomLastOccupied(currentPlayer.getPlayerToken().getCurrentTile().getRoomType());
                else
                    currentPlayer.getPlayerToken().setRoomLastOccupied(null);
            }

            /** If player is in a room, ask player to choose an exit or use a secret passage */
            else if (currentPlayer.getPlayerToken().getCurrentTile().getRoomType() != RoomType.CORRIDOR && currentPlayer.getPlayerToken().getCurrentTile().getRoomType() != RoomType.CELLAR){
                ui.getInfo().addText("Select an exit labelled on the screen or type \"pass\" or \"passage\" to use secret passage");

                /** Collect information about the room the player is occupying */
                int numberOfRoomExits = ui.getBoard().getMap().getRoomByType(currentPlayer.getPlayerToken().getCurrentTile().getRoomType()).getNumberOfDoors();
                Tile[] doors = new Tile[numberOfRoomExits];
                for (int i = 0; i < numberOfRoomExits; i++) {
                    doors[i] = ui.getBoard().getMap().getRoomByType(currentPlayer.getPlayerToken().getCurrentTile().getRoomType()).getDoor(i);
                    ui.getInfo().addText("Door " + (i + 1));
                    ui.getBoard().addDoorToNumber(doors[i]);
                }
                ui.getBoard().repaint();

                /** Prompt user to select exit or secret passage */
                boolean loop = true;
                do{
                    String commandString = ui.getCmd().getCommand();
                    /** User selects secret passage */
                    if (commandString.equals("pass") || commandString.equals("passage")){
                        Room room = ui.getBoard().getMap().getRoom(currentPlayer.getPlayerToken().getCurrentTile().getRoomType().ordinal());
                        room.removeToken();
                        if (moveSecretPassage(currentPlayer.getPlayerToken())) {
                            numberOfMoves = 0;
                            currentPlayer.getPlayerToken().setRoomLastOccupied(currentPlayer.getPlayerToken().getCurrentTile().getRoomType());
                            loop = false;
                        }
                        else
                            ui.getInfo().addText("This room does not have a secret passage!");
                    } else if (command == CommandTypes.QUESTION && currentPlayer.getPlayerToken().getCurrentTile().getRoomType() != RoomType.CORRIDOR && currentPlayer.getPlayerToken().getCurrentTile().getRoomType() != RoomType.CELLAR) {
                        questionAsked = true;
                        question(currentPlayer);
                        numberOfMoves = 0;
                    } else if (command == CommandTypes.QUESTION) {
                        ui.getInfo().addText("You have already questioned! Enter \"done\' to end yout turn or \"notes\" to look at your notes.");
                    } else if (commandString.equals("help")) { // if the user enters help
                        ui.getInfo().addText("Enter \"pass\" or \"passage\" to use the secret passage (if possible) or enter the door number you want to exit the room by");
                    }
                    /** User does not select passage. Check whether user input matches a door index */
                    else {
                        try {
                            int doorSelection = Integer.parseInt(commandString);
                            if (doorSelection < 1 || doorSelection > numberOfRoomExits)
                                ui.getInfo().addText("Please choose a number between 1 and " + numberOfRoomExits);
                            else{
                                Tile sourceTile = currentPlayer.getPlayerToken().getCurrentTile();
                                if (!currentPlayer.getPlayerToken().moveOutOfRoom(doors[doorSelection - 1]))
                                    ui.getInfo().addText("This door seems to be blocked. Choose a different one.");
                                else {
                                    tokenPanel.animateMovement(currentPlayer.getPlayerToken(), sourceTile);
                                    numberOfMoves--;
                                    loop = false;
                                }
                            }
                        }
                        catch (NumberFormatException e){
                            ui.getInfo().addText("Invalid input: \"" + commandString + "\"");
                        }
                    }
                } while (loop);
                ui.getBoard().clearDoorsToNumber();
                ui.getBoard().repaint();
            }

            /** If player is not in a room, continue normal progression of movement */
            else{
                command = doCommand();
                switch (command) {
                    case MOVE_UP:
                        if (moveCharacter(currentPlayer.getPlayerToken(), Direction.UP, currentPlayer.getPlayerName()))
                            numberOfMoves--;
                        break;
                    case MOVE_DOWN:
                        if (moveCharacter(currentPlayer.getPlayerToken(), Direction.DOWN, currentPlayer.getPlayerName()))
                            numberOfMoves--;
                        break;
                    case MOVE_LEFT:
                        if (moveCharacter(currentPlayer.getPlayerToken(), Direction.LEFT, currentPlayer.getPlayerName()))
                            numberOfMoves--;
                        break;
                    case MOVE_RIGHT:
                        if (moveCharacter(currentPlayer.getPlayerToken(), Direction.RIGHT, currentPlayer.getPlayerName()))
                            numberOfMoves--;
                        break;
                    case NOTES:
                        currentPlayer.getPlayerNotes().showNotes(currentPlayer);
                        break;
                    case CHEAT:
                        ui.getInfo().addText(envelope.getMurderer().getName() + " in the " + envelope.getLocation().getName() + " with the " + envelope.getWeapon().getName());
                        break;
                    case QUESTION:
                        ui.getInfo().addText("You cannot question right now, you must be in a room! Try another command");
                        break;
                    case ACCUSE:
                        ui.getInfo().addText("You cannot accuse right now, you must be in the cellar! Try another command");
                        break;
                    case PASSAGE:
                        ui.getInfo().addText("Cannot use secret passage: you are not in a room");
                        break;
                    case HELP:
                        ui.getInfo().addText("Enter \"u\" to move up, \"d\" to move down, \"l\" to move left, \"r\" to move right,\n\"passage\" to move through the secret passage,\n\"notes\" to display your notes,\n\"done\" When you are finished your turn");
                        break;
                    case LOG:
                        log.showLog(currentPlayer);
                        break;
                    case DONE:
                        ui.getInfo().addText("You have ended your turn!");
                        numberOfMoves = 0;
                        currentPlayer.getPlayerToken().setRoomLastOccupied(null);
                        break;
                }
                if (currentPlayer.getPlayerToken().getCurrentTile().getRoomType() != RoomType.CORRIDOR) {
                    numberOfMoves = 0;
                    currentPlayer.getPlayerToken().setRoomLastOccupied(currentPlayer.getPlayerToken().getCurrentTile().getRoomType());
                }
            }
            if (dicePanel.isRunning()){
                synchronized (dicePanel){
                    dicePanel.notify();
                }
            }
        } while (command != CommandTypes.DONE);
    }

    public void question(Player currentPlayer) {
        Question question = new Question(currentPlayer.getPlayerToken().getCurrentTile().getRoomType()); // player question

        question.ask(ui, currentPlayer); // get the murderer and the murder weapon that the player thinks killed gunther

        CharacterNames murderer = CharacterNames.getValue(question.getMurderer().getEnumName()); // convert the card murderer string to CharacterNames
        WeaponTypes murderWeapon = WeaponTypes.getValue(question.getWeapon().getEnumName()); // convert the card murderer string to WeaponTypes
        RoomType murderLocation = RoomType.getValue(question.getLocation().getEnumName()); // convert the card murderer string to RoomType

        Room room = ui.getBoard().getMap().getRoom(murderLocation.ordinal()); // get the room that the current player is in/player thinks the murder happened

        Tile movePlayerTile = room.addToken(); // get the tile in the room that the potential murderer will be placed
        Tile moveWeaponTile = room.addToken(); // get the tile in the room that the potential murder weapon will be placed

        Tile sourceTile = tokenPanel.getPlayerToken(murderer).getCurrentTile();
        tokenPanel.getPlayerToken(murderer).moveToken(movePlayerTile); // move the potential murderer to their assigned tile in the room
        tokenPanel.animateMovement(tokenPanel.getPlayerToken(murderer), sourceTile);
        sourceTile = tokenPanel.getWeaponToken(murderWeapon).getCurrentTile();
        tokenPanel.getWeaponToken(murderWeapon).moveToken(moveWeaponTile); // move the potential murder weapon to its assigned tile in the room
        tokenPanel.animateMovement(tokenPanel.getWeaponToken(murderWeapon), sourceTile);

        String questioningResult = "";

        boolean tokenMatch = false; // boolean of whether the user inputs either done (i.e. doesnt have any of the tokens) or one of the token names that they have

        CardPanel cardPanel = new CardPanel(ui.getBoard(), ui.getCmd(), question);
        ui.getLayers().add(cardPanel, Integer.valueOf(6));
        //loop through the other players asking whether they have one of the tokens or not
        for (Player player : players) {
            if (!currentPlayer.getPlayerName().equals(player.getPlayerName())) { // if not the current player
                ui.getInfo().clear(); // clear the info panel
                ui.getInfo().addText("Pass the game to " + player.getPlayerName() + " and press ENTER to continue"); // prompt

                String userInput = ui.getCmd().getCommand(); // read input
                cardPanel.showPanel();

                // prompt
                ui.getInfo().addText("If you have the character, weapon and/or the room below:\n" + question.getMurderer().getName() + "\n" + question.getLocation().getName() + "\n" + question.getWeapon().getName() + "\nenter the name of the one that you have. Enter \"done\" if you don't have any of them. If you have more than one just enter one of them.");

                boolean canContinue = false; //
                do {
                    String input = ui.getCmd().getCommand();
                    System.out.println("AGHH: " + input + " " + CharacterNames.getValue(input) + ", " + RoomType.getValue(input) + ", " + WeaponTypes.getValue(input));

                    if (input.equals("done")) {
                        /* check if the user really has no valid cards and is allowed to type "done" */
                        canContinue = true;
                        for (Card card: player.getCards()){
                            if (card.toString().equals(question.getLocation().toString()) || card.toString().equals(question.getMurderer().toString()) || card.toString().equals(question.getWeapon().toString())){
                                canContinue = false;
                            }
                        }
                        if (!canContinue)
                            ui.getInfo().addText("You have at least one card in question. You must show one.");
                    } else if (input.equals("notes")) {
                        player.getPlayerNotes().showNotes(player); // display players notes
                    } else if (input.equals("help")) {
                        ui.getInfo().addText("Enter \"done\" if you don't have either the character, room or weapon, the name of the character, room or weapon that you have (pick one if you've more than one), \"notes\" to view youre notes");
                    } else if (murderer == CharacterNames.getValue(input)) { // player has the potential murderer
                        if (player.getPlayerNotes().getNoteItem(murderer.toString()).getChecked() == 'X') { // if the player has that token
                            currentPlayer.getPlayerNotes().setPlayerChecked(question.getMurderer().getEnumName());
                            canContinue = true; // boolean to exit loop
                            log.addEntryWithReply(currentPlayer, question, murderer.toString(), player);
                            questioningResult = player.getPlayerName() + " has " + question.getMurderer().getName() + "."; // create response to current player questioning
                        } else {
                            ui.getInfo().addText("You don't have that character! please enter either the room or weapon if you have one of them, if not just enter \"done\"");
                        }
                    } else if (murderLocation == RoomType.getValue(input)) { // player has the potential murder location
                        if (player.getPlayerNotes().getNoteItem(murderLocation.toString()).getChecked() == 'X') { // if the player has that token
                            currentPlayer.getPlayerNotes().setRoomChecked(question.getLocation().getEnumName());
                            canContinue = true; // boolean to exit loop
                            log.addEntryWithReply(currentPlayer, question, murderLocation.toString(), player);
                            questioningResult = player.getPlayerName() + " has " + question.getLocation().getName() + "."; // create response to current player questioning
                        } else {
                            ui.getInfo().addText("You don't have that room! please enter either the character or weapon if you have one of them, if not just enter \"done\"");
                        }
                    } else if (murderWeapon == WeaponTypes.getValue(input)) { // player has the potential murder weapon
                        if (player.getPlayerNotes().getNoteItem(murderWeapon.toString()).getChecked() == 'X') { // if the player has that token
                            currentPlayer.getPlayerNotes().setWeaponChecked(question.getWeapon().getEnumName());
                            canContinue = true; // boolean to exit loop
                            log.addEntryWithReply(currentPlayer, question, murderWeapon.toString(), player);
                            questioningResult = player.getPlayerName() + " has " + question.getWeapon().getName() + "."; // create response to current player questioning
                        } else {
                            ui.getInfo().addText("You don't have that weapon! please enter either the character or room if you have one of them, if not just enter \"done\"");
                        }
                    } else {
                        ui.getInfo().addText("That's not a valid entry, try again!"); // inform user of invalid input
                    }
                } while (!canContinue); // while the player has not entered the tokens that they have out of the list or entered done
                cardPanel.removePanel();
            }

            if (!questioningResult.equals("")) {
                break;
            }
        }
        ui.getLayers().remove(cardPanel);
        if (questioningResult.equals("")) { // if nobody has any of the tokens
            questioningResult = "Nobody had the character, weapon or the room!"; // create response to current player questioning
            log.addEntryWithoutReply(currentPlayer, question);
        }

        ui.getInfo().clear(); // clear the info panel
        ui.getInfo().addText("Pass the game to " + currentPlayer.getPlayerName() + " and press ENTER to continue"); // prompt
        String input = ui.getCmd().getCommand(); // read input

        ui.getInfo().addText(questioningResult + "\nEnter \"done\" to end your turn or \"notes\" to look at your notes."); // give current player results from questioning and possible actions
    }

    public static void main(String[] args) throws IOException {
        Cluelessdo game = new Cluelessdo();
        Player currentPlayer;
        game.tokenPanel.repaint();
        game.ui.setVisible(true);

        //game.enterPlayers();
        game.waitForPlayersInput();
        game.sortPlayers();
        CardGenerator.generate(game.publicCards);
        game.dealCards();

        /** List all cards for debugging purposes */
        System.out.println("Envelope: " + game.envelope.getLocation() + ", " + game.envelope.getMurderer() + ", " + game.envelope.getWeapon());
        for (Player player: game.players){
            System.out.print(player.getPlayerName() + ": ");
            for (Card card: player.getCards()){
                System.out.print(card + ", ");
            }
            System.out.println();
        }
        System.out.print("Leftover: ");
        for (Card card: game.publicCards){
            System.out.print(card + ", ");
        }
        System.out.println();
        /** Debugging end */

        while (game.isRunning()){
            currentPlayer = game.playerIterator.next();
            if (currentPlayer.isPlaying()) {
                if (game.numberOfPlayersPlaying == 1) {
                    game.ui.getInfo().addText("Congratulations " + currentPlayer.getPlayerName() + ", you have won!");
                    VictoryPanel victoryPanel = new VictoryPanel(game.ui.getBoard(), currentPlayer);
                    game.ui.getLayers().add(victoryPanel, Integer.valueOf(15));
                    game.ui.getInfo().addText("Press ENTER to exit game");
                    String command = game.ui.getCmd().getCommand();
                    System.exit(0);
                } else {
                    game.playTurn(currentPlayer);
                    game.ui.getInfo().clear();
                }
            }
        }
    }
}
