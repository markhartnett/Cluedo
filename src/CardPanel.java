import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

/*
 * The class CardPanel implements panels which display clickable cards. These are shown during questions and accusations.
 * The panel can show all characters, all weapons, all rooms or cards mentioned in a question along with a button which
 * allows a player to notify the game that they have none of these cards.
 *
 * 16310943 James Byrne
 * 16314763 Jakub Gajewski
 * 16305706 Mark Hartnett
 */

public class CardPanel extends JComponent implements ActionListener{
    private final HashMap<Button, String> listOfCards = new HashMap<>();
    private CmdPanel cmdPanel;
    private Timer timer;

    /** Creates a CardPanel with either all character, weapon or room cards */
    public CardPanel(Board board, CmdPanel cmdPanel, String type){
        this.cmdPanel = cmdPanel;
        setBounds(board.getWidth(), -board.getHeight(), board.getWidth(), board.getHeight());
        /* Load required assets depending on the type of panel to be shown */
        switch (type){
            case "characters":
                Image chandler, ross, phoebe, monica, joey, rachel;
                try{
                    /* Load all character cards */
                    chandler = ImageIO.read(getClass().getResource("images/cards/chandler_card.png"));
                    ross = ImageIO.read(getClass().getResource("images/cards/ross_card.png"));
                    phoebe = ImageIO.read(getClass().getResource("images/cards/phoebe_card.png"));
                    monica = ImageIO.read(getClass().getResource("images/cards/monica_card.png"));
                    joey = ImageIO.read(getClass().getResource("images/cards/joey_card.png"));
                    rachel = ImageIO.read(getClass().getResource("images/cards/rachel_card.png"));

                    /* Scale all cards */
                    chandler = chandler.getScaledInstance((int)(chandler.getWidth(this) * 0.75), (int)(chandler.getHeight(this) * 0.75), Image.SCALE_DEFAULT);
                    ross = ross.getScaledInstance((int)(ross.getWidth(this) * 0.75), (int)(ross.getHeight(this) * 0.75), Image.SCALE_DEFAULT);
                    phoebe = phoebe.getScaledInstance((int)(phoebe.getWidth(this) * 0.75), (int)(phoebe.getHeight(this) * 0.75), Image.SCALE_DEFAULT);
                    monica = monica.getScaledInstance((int)(monica.getWidth(this) * 0.75), (int)(monica.getHeight(this) * 0.75), Image.SCALE_DEFAULT);
                    joey = joey.getScaledInstance((int)(joey.getWidth(this) * 0.75), (int)(joey.getHeight(this) * 0.75), Image.SCALE_DEFAULT);
                    rachel = rachel.getScaledInstance((int)(rachel.getWidth(this) * 0.75), (int)(rachel.getHeight(this) * 0.75), Image.SCALE_DEFAULT);

                    /* Create buttons with the cards as images */
                    listOfCards.put(new Button(new Point(getWidth() / 4 - 30, getHeight() / 4 - 30), chandler), "chandler");
                    listOfCards.put(new Button(new Point(getWidth() / 2, getHeight() / 4 - 30), ross), "ross");
                    listOfCards.put(new Button(new Point(getWidth() - (getWidth() / 4) + 30, getHeight() / 4 - 30), phoebe), "phoebe");
                    listOfCards.put(new Button(new Point(getWidth() / 4 - 30, getHeight() / 2), monica), "monica");
                    listOfCards.put(new Button(new Point(getWidth() / 2, getHeight() / 2), joey), "joey");
                    listOfCards.put(new Button(new Point(getWidth() - (getWidth() / 4) + 30, getHeight() / 2), rachel), "rachel");
                }
                catch (IOException e){
                    e.printStackTrace();
                }

                for (Button button: listOfCards.keySet()){
                    button.addActionListener(this);
                    add(button);
                }
                break;
            case "weapons":
                Image pistol, dagger, pipe, rope, candlestick, wrench;
                try{
                    /* Load all weapon cards */
                    pistol = ImageIO.read(getClass().getResource("images/cards/pistol_card.png"));
                    dagger = ImageIO.read(getClass().getResource("images/cards/dagger_card.png"));
                    pipe = ImageIO.read(getClass().getResource("images/cards/pipe_card.png"));
                    rope = ImageIO.read(getClass().getResource("images/cards/rope_card.png"));
                    candlestick = ImageIO.read(getClass().getResource("images/cards/candlestick_card.png"));
                    wrench = ImageIO.read(getClass().getResource("images/cards/wrench_card.png"));

                    /* Scale all cards */
                    pistol = pistol.getScaledInstance((int)(pistol.getWidth(this) * 0.75), (int)(pistol.getHeight(this) * 0.75), Image.SCALE_DEFAULT);
                    dagger = dagger.getScaledInstance((int)(dagger.getWidth(this) * 0.75), (int)(dagger.getHeight(this) * 0.75), Image.SCALE_DEFAULT);
                    pipe = pipe.getScaledInstance((int)(pipe.getWidth(this) * 0.75), (int)(pipe.getHeight(this) * 0.75), Image.SCALE_DEFAULT);
                    rope = rope.getScaledInstance((int)(rope.getWidth(this) * 0.75), (int)(rope.getHeight(this) * 0.75), Image.SCALE_DEFAULT);
                    candlestick = candlestick.getScaledInstance((int)(candlestick.getWidth(this) * 0.75), (int)(candlestick.getHeight(this) * 0.75), Image.SCALE_DEFAULT);
                    wrench = wrench.getScaledInstance((int)(wrench.getWidth(this) * 0.75), (int)(wrench.getHeight(this) * 0.75), Image.SCALE_DEFAULT);

                    /* Create buttons with the cards as images */
                    listOfCards.put(new Button(new Point(getWidth() / 4 - 30, getHeight() / 4 - 30), pistol), "pistol");
                    listOfCards.put(new Button(new Point(getWidth() / 2, getHeight() / 4 - 30), dagger), "dagger");
                    listOfCards.put(new Button(new Point(getWidth() - (getWidth() / 4) + 30, getHeight() / 4 - 30), pipe), "pipe");
                    listOfCards.put(new Button(new Point(getWidth() / 4 - 30, getHeight() / 2), rope), "rope");
                    listOfCards.put(new Button(new Point(getWidth() / 2, getHeight() / 2), candlestick), "candlestick");
                    listOfCards.put(new Button(new Point(getWidth() - (getWidth() / 4) + 30, getHeight() / 2), wrench), "wrench");
                }
                catch (IOException e){
                    e.printStackTrace();
                }

                for (Button button: listOfCards.keySet()){
                    button.addActionListener(this);
                    add(button);
                }
                break;
            case "rooms":
                Image alesandros, centralperk, gellerhouse, j_kitchen, j_livingroom, mc_kitchen, mc_livingroom, p_apartment, r_office;
                try{
                    /* Load all room cards */
                    alesandros = ImageIO.read(getClass().getResource("images/cards/alesandros_card.png"));
                    centralperk = ImageIO.read(getClass().getResource("images/cards/centralperk_card.png"));
                    gellerhouse = ImageIO.read(getClass().getResource("images/cards/gellerhouse_card.png"));
                    j_kitchen = ImageIO.read(getClass().getResource("images/cards/j_kitchen_card.png"));
                    j_livingroom = ImageIO.read(getClass().getResource("images/cards/j_livingroom_card.png"));
                    mc_kitchen = ImageIO.read(getClass().getResource("images/cards/mc_kitchen_card.png"));
                    mc_livingroom = ImageIO.read(getClass().getResource("images/cards/mc_livingroom_card.png"));
                    p_apartment = ImageIO.read(getClass().getResource("images/cards/p_apartment_card.png"));
                    r_office = ImageIO.read(getClass().getResource("images/cards/r_office_card.png"));

                    /* Scale all cards */
                    alesandros = alesandros.getScaledInstance((int)(alesandros.getWidth(this) * 0.75), (int)(alesandros.getHeight(this) * 0.75), Image.SCALE_DEFAULT);
                    gellerhouse = gellerhouse.getScaledInstance((int)(gellerhouse.getWidth(this) * 0.75), (int)(gellerhouse.getHeight(this) * 0.75), Image.SCALE_DEFAULT);
                    j_kitchen = j_kitchen.getScaledInstance((int)(j_kitchen.getWidth(this) * 0.75), (int)(j_kitchen.getHeight(this) * 0.75), Image.SCALE_DEFAULT);
                    j_livingroom = j_livingroom.getScaledInstance((int)(j_livingroom.getWidth(this) * 0.75), (int)(j_livingroom.getHeight(this) * 0.75), Image.SCALE_DEFAULT);
                    mc_kitchen = mc_kitchen.getScaledInstance((int)(mc_kitchen.getWidth(this) * 0.75), (int)(mc_kitchen.getHeight(this) * 0.75), Image.SCALE_DEFAULT);
                    centralperk = centralperk.getScaledInstance((int)(centralperk.getWidth(this) * 0.75), (int)(centralperk.getHeight(this) * 0.75), Image.SCALE_DEFAULT);
                    mc_livingroom = mc_livingroom.getScaledInstance((int)(mc_livingroom.getWidth(this) * 0.75), (int)(mc_livingroom.getHeight(this) * 0.75), Image.SCALE_DEFAULT);
                    p_apartment = p_apartment.getScaledInstance((int)(p_apartment.getWidth(this) * 0.75), (int)(p_apartment.getHeight(this) * 0.75), Image.SCALE_DEFAULT);
                    r_office = r_office.getScaledInstance((int)(r_office.getWidth(this) * 0.75), (int)(r_office.getHeight(this) * 0.75), Image.SCALE_DEFAULT);

                    /* Create cards with the buttons as cards */
                    listOfCards.put(new Button(new Point(getWidth() / 4 - 30, getHeight() / 4 - 30), alesandros), "alesandros");
                    listOfCards.put(new Button(new Point(getWidth() / 2, getHeight() / 4 - 30), centralperk), "centralperk");
                    listOfCards.put(new Button(new Point(getWidth() - (getWidth() / 4) + 30, getHeight() / 4 - 30), gellerhouse), "gellerhouse");
                    listOfCards.put(new Button(new Point(getWidth() / 4 - 30, getHeight() / 2), j_kitchen), "j_kitchen");
                    listOfCards.put(new Button(new Point(getWidth() / 2, getHeight() / 2), j_livingroom), "j_livingroom");
                    listOfCards.put(new Button(new Point(getWidth() - (getWidth() / 4) + 30, getHeight() / 2), mc_kitchen), "mc_kitchen");
                    listOfCards.put(new Button(new Point(getWidth() / 4 - 30, getHeight() - (getHeight() / 4) + 30), mc_livingroom), "mc_livingroom");
                    listOfCards.put(new Button(new Point(getWidth() / 2, getHeight() - (getHeight() / 4) + 30), p_apartment), "p_apartment");
                    listOfCards.put(new Button(new Point(getWidth() - (getWidth() / 4) + 30, getHeight() - (getHeight() / 4) + 30), r_office), "r_office");
                }
                catch (IOException e){
                    e.printStackTrace();
                }
                for (Button button: listOfCards.keySet()){
                    button.addActionListener(this);
                    add(button);
                }
                break;
            default:
                break;
        }
    }

    /** Creates CardPanel with cards named in a question and a "none of the above" button */
    public CardPanel(Board board, CmdPanel cmdPanel, Question question){
        this.cmdPanel = cmdPanel;
        setBounds(board.getWidth(), -board.getHeight(), board.getWidth(), board.getHeight());
        String weapon = question.getWeapon().getEnumName().toLowerCase();
        String location = question.getLocation().getEnumName().toLowerCase();
        String murderer = question.getMurderer().getEnumName().toLowerCase();

        Image weaponImage, locationImage, murdererImage;
        /* Load and scale cards and create all buttons */
        try{
            weaponImage = ImageIO.read(getClass().getResource("images/cards/" + weapon + "_card.png"));
            locationImage = ImageIO.read(getClass().getResource("images/cards/" + location + "_card.png"));
            murdererImage = ImageIO.read(getClass().getResource("images/cards/" + murderer + "_card.png"));

            weaponImage = weaponImage.getScaledInstance((int)(weaponImage.getWidth(this) * 0.75), (int)(weaponImage.getHeight(this) * 0.75), Image.SCALE_DEFAULT);
            locationImage = locationImage.getScaledInstance((int)(locationImage.getWidth(this) * 0.75), (int)(locationImage.getHeight(this) * 0.75), Image.SCALE_DEFAULT);
            murdererImage = murdererImage.getScaledInstance((int)(murdererImage.getWidth(this) * 0.75), (int)(murdererImage.getHeight(this) * 0.75), Image.SCALE_DEFAULT);

            listOfCards.put(new Button(new Point(getWidth() / 4, getHeight() / 2), weaponImage), weapon);
            listOfCards.put(new Button(new Point(getWidth() / 2, getHeight() / 2), locationImage), location);
            listOfCards.put(new Button(new Point(getWidth() - (getWidth() / 4), getHeight() / 2), murdererImage), murderer);
            listOfCards.put(new Button(new Point(getWidth() / 2, getHeight() - (getHeight() / 4)), "images/buttons/none of the above button.png"), "done");
        }
        catch (IOException e){
            e.printStackTrace();
        }

        for (Button button: listOfCards.keySet()){
            button.addActionListener(this);
            add(button);
        }
    }

    /** Animate the displaying of the panel on the screen */
    public void showPanel(){
        timer = new Timer(20, new PanelShowAnimation());
        timer.start();
    }

    /** Animate the removal of the panel off the screen */
    public void removePanel(){
        do{
        } while (timer.isRunning());
        timer = new Timer(20, new PanelRemoveAnimation());
        timer.start();
        do{
        } while (timer.isRunning());
    }

    /** Action listener used in each card button, adds corresponding card name or "done" to the command panel */
    public void actionPerformed(ActionEvent e){
        Button source = (Button)e.getSource();
        cmdPanel.addCommand(listOfCards.get(source));
    }

    private class PanelShowAnimation implements ActionListener{
        private int animationCounter = 0;

        public void actionPerformed(ActionEvent e){
            setLocation(0, (int)Easing.easeOutQuad(animationCounter, -getHeight(), getHeight(), 30));

            if (++animationCounter > 30)
                timer.stop();
        }
    }

    private class PanelRemoveAnimation implements ActionListener{
        private int animationCounter = 0;

        public void actionPerformed(ActionEvent e){
            setLocation(0, (int)Easing.easeOutQuad(animationCounter, 0, -getHeight(), 30));

            if (++animationCounter > 30)
                timer.stop();
        }
    }
}
