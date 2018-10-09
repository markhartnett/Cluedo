import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/*
 * The class IntroScreen displays an animated, interactive screen at the start of the game which allows the players to
 * enter their names and select their characters
 *
 * 16310943 James Byrne
 * 16314763 Jakub Gajewski
 * 16305706 Mark Hartnett
 */

public class IntroScreen extends JPanel {
    private ImageData logo, friends, gunther, whoKilledGunther;
    private Button playButton;
    private PlayerSelectionWindow window;
    private boolean active = true;
    private Timer timer;
    private CircularlyLinkedList<Player> playerList;
    private TokenController tokenPanel;

    /** Loads and adds all assets to the screen */
    public IntroScreen(Board board, TokenController tokenPanel, CircularlyLinkedList<Player> playerList){
        super();
        setOpaque(false);
        setLayout(null);
        setBounds(0, 0, board.getXBoard(), board.getYBoard());
        friends = new ImageData("images/intro/friends.jpg", new Point(0, 0));
        logo = new ImageData("images/intro/logo.png", new Point(0, 201));
        gunther = new ImageData("images/intro/gunther.png", new Point(-111, 367));
        whoKilledGunther = new ImageData("images/intro/who killed gunther.PNG", new Point(301, 321));
        window = new PlayerSelectionWindow();

        playButton = new Button(new Point(getWidth() / 2, 490), "images/buttons/play button.png");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playButtonClick();
            }
        });
        this.playerList = playerList;
        this.tokenPanel = tokenPanel;
        add(playButton);
        add(window);

        timer = new Timer(20, new AnimationTimerListener());
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if (active) {
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(friends.image, friends.position.x, friends.position.y,  this);
            g2.drawImage(logo.image, logo.position.x, logo.position.y, getWidth(), getHeight(), this);
            float opacity = 0.5f;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            g2.drawImage(gunther.image, gunther.position.x, gunther.position.y, this);
            opacity = 1f;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            g2.drawImage(whoKilledGunther.image, whoKilledGunther.position.x, whoKilledGunther.position.y, this);
        }
    }

    /** Action preformed when the first play button is clicked */
    private void playButtonClick(){
        remove(playButton);
        repaint();
        timer.start();
    }

    private void removeScreen(){
        timer = new Timer(20, new ScreenRemovalListener());
        timer.start();
    }

    /** Action listener animates the chancging of shape of the screen and the display of the player selection window */
    private class AnimationTimerListener implements ActionListener{
        private int movementIncrement = 1;

        public void actionPerformed(ActionEvent e){
            if (movementIncrement <= 35) {
                gunther.position.x = (int) Easing.easeInQuad(movementIncrement, -111, -getWidth(), 35);
                whoKilledGunther.position.x = (int) Easing.easeInQuad(movementIncrement, 301, getWidth(), 35);
                whoKilledGunther.position.y = (int) Easing.easeInQuad(movementIncrement, 321, Math.tan(-3.18) * getHeight(), 35);
                logo.position.y = (int) Easing.easeInOutQuad(movementIncrement, 201, -70, 35);
            }
            if (movementIncrement >= 25){
                window.setLocation(window.getX(), (int) Easing.easeOutQuad(movementIncrement - 25, 615, -(window.windowImage.getHeight() + 30), 35));
            }
            movementIncrement++;
            repaint();
            if (movementIncrement > 60)
                timer.stop();
        }
    }

    /** Action listener animates the removal of the screen */
    private class ScreenRemovalListener implements ActionListener{
        private int movementIncrement = 1;

        public void actionPerformed(ActionEvent e){
            setLocation(getX(), (int) Easing.easeInQuad(movementIncrement++, 0, -getHeight(), 35));
            if (movementIncrement > 35) {
                timer.stop();
                synchronized (playerList){
                    playerList.notify();
                }
            }
        }
    }

    private class ImageData{
        private BufferedImage image;
        private Point position;

        public ImageData(String path, Point position){
            try {
                image = ImageIO.read(getClass().getResource(path));
                this.position = position;
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    /*
     * The class PlayerSelectionWindow implements the window which allows players to enter their names and select their characters
     */
    private class PlayerSelectionWindow extends JComponent{
        private BufferedImage windowImage;
        private final HashMap<CharacterNames, BufferedImage> characterImages = new HashMap<>();
        private int playerCount = 0;
        private JLabel title = new JLabel("Select player " + (playerCount + 1));
        private String playersString = "<html>Players added: </html>";
        private JLabel players = new JLabel(playersString);
        private JTextField playerNameField = new JTextField();
        private CharacterNames cardDisplayingName = CharacterNames.CHANDLER;    // Stores the name of the character currently displayed on the screen
        private BufferedImage cardDisplayingImage, cardBackImage;
        private Button playButton, addButton, leftButton, rightButton;
        private ArrayList<CharacterNames> characterNames = new ArrayList<>();   // Stores the names of characters which were not seleted yet

        public PlayerSelectionWindow(){
            try {
                /* Load the window image, all character cards and the back of the card */
                windowImage = ImageIO.read(getClass().getResource("images/intro/input_window.png"));
                characterImages.put(CharacterNames.CHANDLER, ImageIO.read(getClass().getResource("images/cards/chandler_card.png")));
                characterImages.put(CharacterNames.MONICA, ImageIO.read(getClass().getResource("images/cards/monica_card.png")));
                characterImages.put(CharacterNames.ROSS, ImageIO.read(getClass().getResource("images/cards/ross_card.png")));
                characterImages.put(CharacterNames.PHOEBE, ImageIO.read(getClass().getResource("images/cards/phoebe_card.png")));
                characterImages.put(CharacterNames.JOEY, ImageIO.read(getClass().getResource("images/cards/joey_card.png")));
                characterImages.put(CharacterNames.RACHEL, ImageIO.read(getClass().getResource("images/cards/rachel_card.png")));
                cardBackImage = ImageIO.read(getClass().getResource("images/cards/card_back.png"));
            }
            catch (IOException e){
                e.printStackTrace();
            }
            characterNames.addAll(Arrays.asList(CharacterNames.values()));
            cardDisplayingImage = characterImages.get(cardDisplayingName);

            /* Set up all components of the window */

            setBounds((592 / 2) - (windowImage.getWidth() / 2), 615, windowImage.getWidth(), windowImage.getHeight());
            title.setBounds((getWidth() / 3) - 90, 15, 180, 30);
            title.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 20));
            title.setHorizontalAlignment(SwingConstants.CENTER);
            title.setVerticalAlignment(SwingConstants.CENTER);
            add(title);

            players.setBounds((getWidth() / 3) * 2, 20, 140, getHeight() - 40);
            players.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 14));
            players.setVerticalAlignment(SwingConstants.TOP);
            add(players);

            playerNameField.setBounds((getWidth() / 3) - 100, getHeight() - 40, 200, 20);
            add(playerNameField);
            playerNameField.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addButtonAction();
                    repaint();
                }
            });

            leftButton = new Button(new Point((getWidth() / 3) - 110, getHeight() / 2), "images/buttons/left button.png");
            rightButton = new Button(new Point((getWidth() / 3) + 110, getHeight() / 2), "images/buttons/right button.png");
            playButton = new Button(new Point(getWidth() - (getWidth() / 5), getHeight() - 35), "images/buttons/add player button.png");
            addButton = new Button(new Point(getWidth() - (getWidth() / 5), getHeight() - 85), "images/buttons/play button.png");

            leftButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    leftButtonAction();
                    repaint();
                }
            });
            rightButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    rightButtonAction();
                    repaint();
                }
            });
            playButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addButtonAction();
                    repaint();
                }
            });
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    playButtonAction();
                }
            });

            add(leftButton);
            add(rightButton);
            add(playButton);

            setVisible(true);
        }

        public Dimension getPreferredSize(){
            return new Dimension(windowImage.getWidth(), windowImage.getHeight());
        }
        public Dimension getMinimumSize(){
            return new Dimension(windowImage.getWidth(), windowImage.getHeight());
        }
        public Dimension getMaximumSize(){
            return new Dimension(windowImage.getWidth(), windowImage.getHeight());
        }

        /* Changes currently displayed character to the previous one */
        private void leftButtonAction(){
            if (characterNames.indexOf(cardDisplayingName) == 0)
                cardDisplayingName = characterNames.get(characterNames.size() - 1);
            else
                cardDisplayingName = characterNames.get(characterNames.indexOf(cardDisplayingName) - 1);
            cardDisplayingImage = characterImages.get(cardDisplayingName);
        }

        /* Changes currently displayed character to the next one */
        private void rightButtonAction(){
            if (characterNames.indexOf(cardDisplayingName) == characterNames.size() - 1)
                cardDisplayingName = characterNames.get(0);
            else
                cardDisplayingName = characterNames.get(characterNames.indexOf(cardDisplayingName) + 1);
            cardDisplayingImage = characterImages.get(cardDisplayingName);
        }

        /* Takes information currently displayed on the screen and creates a player using this information */
        private void addButtonAction(){
            playerList.addLast(new Player(playerNameField.getText(), tokenPanel.getPlayerToken(cardDisplayingName)));
            playerNameField.setText("");
            playersString = playersString.substring(0, playersString.length() - 7);
            playersString += "<br/>" + playerList.getLast().getPlayerToken() + ": " + playerList.getLast().getPlayerName() + "</html>";
            players.setText(playersString);

            rightButtonAction();
            characterNames.remove(playerList.getLast().getPlayerToken().getName());
            if (playerList.getSize() < 6)
                title.setText("Select player " + (playerList.getSize() + 1));
            else {
                title.setText("Player list full");
                cardDisplayingImage = cardBackImage;
                remove(leftButton);
                remove(rightButton);
            }

            if (playerList.getSize() >= 2)
                add(addButton);
            if (playerList.getSize() >= 6)
                remove(playButton);
        }

        /* Removes the screen and starts the game using the currently added players */
        private void playButtonAction(){
            cardDisplayingImage = cardBackImage;

            title.setText("Let's play!");
            removeScreen();
        }

        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            g2.drawImage(windowImage, 0, 0, this);
            g2.drawImage(cardDisplayingImage.getScaledInstance(140, 196, Image.SCALE_DEFAULT), (getWidth() / 3) - (140 / 2), (getHeight() / 2) - (196 / 2), this);


        }
    }
}
