import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/*
16310943 James Byrne
16314763 Jakub Gajewski
16305706 Mark Hartnett
 */

/*
 * The TokenController class is an extension of the JPanel class with added functionality to store and
 * render shapes/images using Swing.
 *
 * The class contains 2 array lists, playerTokens and weaponTokens, used to store Character and Weapon objects.
 * Images are stored in an instance of the TokenController class, though this may change in the future.
 */

public class TokenController extends JPanel implements ActionListener {
    /** ArrayLists used to store Character and Weapon objects */
    private ArrayList<Character> playerTokens;
    private ArrayList<Weapon> weaponTokens;

    /** BufferedImage objects used to store images of weapons in the game */
    private BufferedImage pistol, wrench, pipe, rope, dagger, candlestick,
            chandler, ross, joey, monica, phoebe, rachel;

    /** Variables used for animation */
    private Timer timer;
    private boolean timerRunning = false;
    private Token movingToken;
    private Tile sourceTile;

    private int animationCounter;
    private double dx, dy;

    /**
     * Constructor of the class TokenController. Initialises the token ArrayLists and attempts to load images.
     * If image is not found, an error message is printed to the console.
     */
    public TokenController(Map map,Board board) throws IOException {
        super(null);
        playerTokens = new ArrayList<>();
        weaponTokens = new ArrayList<>();

        setOpaque(false);

        setBounds(0, 0, board.getXBoard(), board.getYBoard());

        addWeaponToken(new Weapon(map.getTile(11, 11), WeaponTypes.PISTOL));
        addWeaponToken(new Weapon(map.getTile(12, 11), WeaponTypes.ROPE));
        addWeaponToken(new Weapon(map.getTile(13, 11), WeaponTypes.CANDLESTICK));
        addWeaponToken(new Weapon(map.getTile(11, 12), WeaponTypes.PIPE));
        addWeaponToken(new Weapon(map.getTile(12, 12), WeaponTypes.WRENCH));
        addWeaponToken(new Weapon(map.getTile(13, 12), WeaponTypes.DAGGER));

        addPlayerToken(new Character(map.getTile(14, 0), CharacterNames.CHANDLER));
        addPlayerToken(new Character(map.getTile(23, 6), CharacterNames.RACHEL));
        addPlayerToken(new Character(map.getTile(9, 0), CharacterNames.MONICA));
        addPlayerToken(new Character(map.getTile(0, 17), CharacterNames.JOEY));
        addPlayerToken(new Character(map.getTile(23, 19), CharacterNames.ROSS));
        addPlayerToken(new Character(map.getTile(7, 24), CharacterNames.PHOEBE));

        readImages();
    }

    public ArrayList<Character> getPlayerTokens() {
        return playerTokens;
    }

    public ArrayList<Weapon> getWeaponTokens() {
        return weaponTokens;
    }

    /**
     * Methods to add a Character or Weapon object to the corresponding list.
     */
    public void addPlayerToken(Character token){
        playerTokens.add(token);
    }

    public void addWeaponToken(Weapon token){
        weaponTokens.add(token);
    }

    /**
     * Methods to find and return a Character or Weapon object by its type enum.
     */
    public Character getPlayerToken(CharacterNames name) {
        for (Character tmp: playerTokens){
            if (tmp.getName() == name)
                return tmp;
        }
        return null;
    }

    public Weapon getWeaponToken(WeaponTypes type){
        for (Weapon tmp: weaponTokens){
            if (tmp.getType() == type)
                return tmp;
        }
        return null;
    }

    /**
     * Methods to find and return a Character or Weapon by its type.
     */

    /**
     * Attempts to read images to be used to represent tokens.
     */
    private void readImages() throws IOException {
        chandler = ImageIO.read(getClass().getResource(("images/green.png")));
        ross = ImageIO.read(getClass().getResource(("images/plum.png")));
        joey = ImageIO.read(getClass().getResource(("images/mustard.png")));
        monica = ImageIO.read(getClass().getResource(("images/white.png")));
        phoebe = ImageIO.read(getClass().getResource(("images/scarlett.png")));
        rachel = ImageIO.read(getClass().getResource(("images/peacock.png")));

        pistol = ImageIO.read(getClass().getResource(("images/pistol.png")));
        wrench = ImageIO.read(getClass().getResource(("images/wrench.png")));
        pipe = ImageIO.read(getClass().getResource(("images/pipe.png")));
        candlestick = ImageIO.read(getClass().getResource(("images/candlestick.png")));
        rope = ImageIO.read(getClass().getResource(("images/rope.png")));
        dagger = ImageIO.read(getClass().getResource(("images/dagger.png")));
    }

    public void animateMovement(Token token, Tile source){
        movingToken = token;
        sourceTile = source;

        dx = movingToken.getCurrentTile().getXCoordinate() - sourceTile.getXCoordinate();
        dy = movingToken.getCurrentTile().getYCoordinate() - sourceTile.getYCoordinate();

        timer = new Timer(20, this);
        timer.setInitialDelay(0);
        timerRunning = true;
        animationCounter = 1;
        timer.start();
        do{

        }while (timer.isRunning());
    }

    @Override
    public void actionPerformed(ActionEvent e){
        movingToken.setPosition(new Point(
                (int)Easing.easeInOutQuad(animationCounter, sourceTile.getXCoordinate(), dx, 10),
                (int)Easing.easeInOutQuad(animationCounter, sourceTile.getYCoordinate(), dy, 10)
        ));
        animationCounter++;
        repaint();
        if (animationCounter > 10) {
            timer.stop();
            timerRunning = false;
        }
    }

    /**
     * Draws all character and weapon tokens using loaded images or predetermined shapes.
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        /** Set drawing colour according to character name */
        for (Character tmp: playerTokens){
            Point point = tmp.getPosition();
            switch (tmp.getName()){
                case PHOEBE:
                    g2.drawImage(phoebe, point.x - (phoebe.getWidth() / 2), point.y - (phoebe.getHeight() / 2), this); break;
                case JOEY:
                    g2.drawImage(joey, point.x - (joey.getWidth() / 2), point.y - (joey.getHeight() / 2), this); break;
                case RACHEL:
                    g2.drawImage(rachel, point.x - (rachel.getWidth() / 2), point.y - (rachel.getHeight() / 2), this); break;
                case MONICA:
                    g2.drawImage(monica, point.x - (monica.getWidth() / 2), point.y - (monica.getHeight() / 2), this); break;
                case CHANDLER:
                    g2.drawImage(chandler, point.x - (chandler.getWidth() / 2), point.y - (chandler.getHeight() / 2), this); break;
                case ROSS:
                    g2.drawImage(ross, point.x - (ross.getWidth() / 2), point.y - (chandler.getHeight() / 2), this); break;
            }
        }

        /** Draw specified image on the game board according to weapon type */
        for (Weapon tmp: weaponTokens){
            Point point = tmp.getPosition();
            switch (tmp.getType()){
                case PISTOL:
                    g2.drawImage(pistol, point.x - (pistol.getWidth() / 2), point.y - (pistol.getHeight() / 2), this); break;
                case WRENCH:
                    g2.drawImage(wrench, point.x - (wrench.getWidth() / 2), point.y - (wrench.getHeight() / 2), this); break;
                case PIPE:
                    g2.drawImage(pipe, point.x - (pipe.getWidth() / 2), point.y - (pipe.getHeight() / 2), this); break;
                case DAGGER:
                    g2.drawImage(dagger, point.x - (dagger.getWidth() / 2), point.y - (dagger.getHeight() / 2), this); break;
                case CANDLESTICK:
                    g2.drawImage(candlestick, point.x - (candlestick.getWidth() / 2), point.y - (candlestick.getHeight() / 2), this); break;
                case ROPE:
                    g2.drawImage(rope, point.x - (rope.getWidth() / 2), point.y - (rope.getHeight() / 2), this); break;
            }
        }
    }
}
