import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/*
 * The class EnvelopePanel displays an animation of the murder envelope being inspected. The panel draws the 3 cards which
 * represent the player's guess and displays the murder envelope and removes the three cards. If the guess fails, the
 * cards are placed back into the envelope
 *
 * 16310943 James Byrne
 * 16314763 Jakub Gajewski
 * 16305706 Mark Hartnett
 */

public class EnvelopePanel extends JComponent{
    private ImageData envelopeImage;
    private ImageData weaponImage;
    private ImageData locationImage;
    private ImageData murdererImage;
    private ImageData weaponGuess;
    private ImageData murdererGuess;
    private ImageData locationGuess;
    private Timer timer;

    /** Loads scaled instances of the cards representing the contents of the envelope and the player's guess */
    public EnvelopePanel(Board board, Envelope envelope, Accusation accusation){
        String weapon = envelope.getWeapon().getEnumName().toLowerCase();
        System.out.println(weapon);
        String location = envelope.getLocation().getEnumName().toLowerCase();
        System.out.println(location);
        String murderer = envelope.getMurderer().getEnumName().toLowerCase();
        System.out.println(murderer);
        setBounds(board.getBounds());
        envelopeImage = new ImageData("images/cards/murder envelope.png", new Point(getWidth() / 2, getHeight() / 2));
        weaponImage = new ImageData("images/cards/" + weapon + "_card.png", new Point(getWidth() / 2, getHeight() / 2));
        locationImage = new ImageData("images/cards/" + location + "_card.png", new Point(getWidth() / 2, getHeight() / 2));
        murdererImage = new ImageData("images/cards/" + murderer + "_card.png", new Point(getWidth() / 2, getHeight() / 2));
        this.weaponGuess = new ImageData("images/cards/" + WeaponTypes.getValue(accusation.weapon).toString().toLowerCase() + "_card.png", new Point(getWidth() / 2, getHeight() / 2));
        this.murdererGuess = new ImageData("images/cards/" + CharacterNames.getValue(accusation.suspect).toString().toLowerCase() + "_card.png", new Point(getWidth() / 2, getHeight() / 2));
        this.locationGuess = new ImageData("images/cards/" + RoomType.getValue(accusation.room).toString().toLowerCase() + "_card.png", new Point(getWidth() / 2, getHeight() / 2));

        envelopeImage.image = envelopeImage.image.getScaledInstance(
                (int)(envelopeImage.image.getWidth(this) * 0.75), (int)(envelopeImage.image.getHeight(this) * 0.75), Image.SCALE_DEFAULT);
        weaponImage.image = weaponImage.image.getScaledInstance(
                (int)(weaponImage.image.getWidth(this) * 0.75), (int)(weaponImage.image.getHeight(this) * 0.75), Image.SCALE_DEFAULT);
        locationImage.image = locationImage.image.getScaledInstance(
                (int)(locationImage.image.getWidth(this) * 0.75), (int)(locationImage.image.getHeight(this) * 0.75), Image.SCALE_DEFAULT);
        murdererImage.image = murdererImage.image.getScaledInstance(
                (int)(murdererImage.image.getWidth(this) * 0.75), (int)(murdererImage.image.getHeight(this) * 0.75), Image.SCALE_DEFAULT);
        this.weaponGuess.image = this.weaponGuess.image.getScaledInstance(
                (int)(this.weaponGuess.image.getWidth(this) * 0.75), (int)(this.weaponGuess.image.getHeight(this) * 0.75), Image.SCALE_DEFAULT);
        this.locationGuess.image = this.locationGuess.image.getScaledInstance(
                (int)(this.locationGuess.image.getWidth(this) * 0.75), (int)(this.locationGuess.image.getHeight(this) * 0.75), Image.SCALE_DEFAULT);
        this.murdererGuess.image = this.murdererGuess.image.getScaledInstance(
                (int)(this.murdererGuess.image.getWidth(this) * 0.75), (int)(this.murdererGuess.image.getHeight(this) * 0.75), Image.SCALE_DEFAULT);

        setVisible(true);
    }

    /** Animates the display of the murder envelope */
    public void displayEnvelope(){
        envelopeImage.position.x = (getWidth() / 2) - (envelopeImage.image.getWidth(this) / 2);
        locationImage.position.x = (EnvelopePanel.this.getWidth() / 2) - (locationImage.image.getWidth(this) / 2);

        weaponGuess.position.x = (EnvelopePanel.this.getWidth() / 2) - (weaponImage.image.getWidth(EnvelopePanel.this) / 2) - 150;
        locationGuess.position.x = (EnvelopePanel.this.getWidth() / 2) - (locationImage.image.getWidth(EnvelopePanel.this) / 2);
        murdererGuess.position.x = (EnvelopePanel.this.getWidth() / 2) - (murdererGuess.image.getWidth(EnvelopePanel.this) / 2) + 150;murdererGuess.position.x = (EnvelopePanel.this.getWidth() / 2) - (murdererGuess.image.getWidth(EnvelopePanel.this) / 2) + 150;
        timer = new Timer(20, new EnvelopeDisplayListener());
        timer.start();
        do{

        } while(timer.isRunning());
    }

    /** Animates the removal of the murder envelope off the screen */
    public void removeEnvelope(){
        timer = new Timer(20, new EnvelopeRemovalListener());
        timer.start();
        do{

        } while(timer.isRunning());
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (weaponImage.drawing)
            g2.drawImage(weaponImage.image, weaponImage.position.x, weaponImage.position.y, this);

        if (locationImage.drawing)
            g2.drawImage(locationImage.image, locationImage.position.x, locationImage.position.y, this);

        if (murdererImage.drawing)
            g2.drawImage(murdererImage.image, murdererImage.position.x, murdererImage.position.y, this);

        if (envelopeImage.drawing)
            g2.drawImage(envelopeImage.image, envelopeImage.position.x, envelopeImage.position.y, this);

        if (weaponGuess.drawing)
            g2.drawImage(weaponGuess.image, weaponGuess.position.x, weaponGuess.position.y, this);

        if (locationGuess.drawing)
            g2.drawImage(locationGuess.image, locationGuess.position.x, locationGuess.position.y, this);

        if (murdererGuess.drawing)
            g2.drawImage(murdererGuess.image, murdererGuess.position.x, murdererGuess.position.y, this);
    }

    private class EnvelopeDisplayListener implements ActionListener{
        private int animationCounter = 1;

        public void actionPerformed(ActionEvent e){
            if (animationCounter <= 40) {
                envelopeImage.drawing = true;
                envelopeImage.position.y = (int)Easing.easeOutQuad(animationCounter, EnvelopePanel.this.getHeight(), (-EnvelopePanel.this.getHeight() / 3) + 30, 40);

                weaponGuess.drawing = true;
                locationGuess.drawing = true;
                murdererGuess.drawing = true;
                weaponGuess.position.y = (int) Easing.easeOutQuad(animationCounter, -weaponGuess.image.getHeight(EnvelopePanel.this), weaponGuess.image.getHeight(EnvelopePanel.this) + 10, 40);
                locationGuess.position.y = (int) Easing.easeOutQuad(animationCounter, -locationGuess.image.getHeight(EnvelopePanel.this), locationGuess.image.getHeight(EnvelopePanel.this) + 10, 40);
                murdererGuess.position.y = (int) Easing.easeOutQuad(animationCounter, -murdererGuess.image.getHeight(EnvelopePanel.this), murdererGuess.image.getHeight(EnvelopePanel.this) + 10, 40);
            }
            if (animationCounter > 40 && animationCounter <= 80){
                weaponImage.drawing = true;
                weaponImage.position.x = (int)Easing.easeInCube(animationCounter - 40, (EnvelopePanel.this.getWidth() / 2) - (weaponImage.image.getWidth(EnvelopePanel.this) / 2), -150, 40);
                weaponImage.position.y = (int)Easing.easeOutQuad(animationCounter - 40, EnvelopePanel.this.getHeight() - (getHeight() / 3), -200, 40);
            }
            if (animationCounter > 80 && animationCounter <= 120){
                locationImage.drawing = true;
                locationImage.position.y = (int)Easing.easeOutQuad(animationCounter - 80, EnvelopePanel.this.getHeight() - (getHeight() / 3), -200, 40);
            }
            if (animationCounter > 120 && animationCounter <= 160){
                murdererImage.drawing = true;
                murdererImage.position.x = (int)Easing.easeInCube(animationCounter - 120, (EnvelopePanel.this.getWidth() / 2) - (murdererImage.image.getWidth(EnvelopePanel.this) / 2), 150, 40);
                murdererImage.position.y = (int)Easing.easeOutQuad(animationCounter - 120, (EnvelopePanel.this.getHeight() - (getHeight() / 3)), -200, 40);
            }
            repaint();
            animationCounter++;
            if (animationCounter > 160)
                timer.stop();
        }
    }

    private class EnvelopeRemovalListener implements ActionListener{
        private int animationCounter = 1;
        public void actionPerformed(ActionEvent e){
            if (animationCounter <= 40){
                weaponGuess.position.y = (int) Easing.easeInQuad(animationCounter, 10, -weaponGuess.image.getHeight(EnvelopePanel.this) - 10, 40);
                locationGuess.position.y = (int) Easing.easeInQuad(animationCounter, 10, -locationGuess.image.getHeight(EnvelopePanel.this) - 10, 40);
                murdererGuess.position.y = (int) Easing.easeInQuad(animationCounter, 10, -murdererGuess.image.getHeight(EnvelopePanel.this) - 10, 40);

                weaponImage.position.x = (int)Easing.easeOutCube(animationCounter, (EnvelopePanel.this.getWidth() / 2) - (weaponImage.image.getWidth(EnvelopePanel.this) / 2) - 150, 150,  40);
                weaponImage.position.y = (int)Easing.easeInQuad(animationCounter, EnvelopePanel.this.getHeight() - (getHeight() / 3) - 200, 200,  40);

                locationImage.position.y = (int)Easing.easeInQuad(animationCounter, EnvelopePanel.this.getHeight() - (getHeight() / 3) - 200, 200,  40);

                murdererImage.position.x = (int)Easing.easeOutCube(animationCounter, (EnvelopePanel.this.getWidth() / 2) - (murdererImage.image.getWidth(EnvelopePanel.this) / 2) + 150, -150,  40);
                murdererImage.position.y = (int)Easing.easeInQuad(animationCounter, (EnvelopePanel.this.getHeight() - (getHeight() / 3)) - 200, 200,  40);
            }

            if (animationCounter == 41){
                weaponGuess.drawing = false;
                locationGuess.drawing = false;
                murdererGuess.drawing = false;
                weaponImage.drawing = false;
                locationImage.drawing = false;
                murdererImage.drawing = false;
            }

            if (animationCounter > 40 && animationCounter <= 80){
                envelopeImage.position.y = (int)Easing.easeInQuad(animationCounter - 40, EnvelopePanel.this.getHeight() - (EnvelopePanel.this.getHeight() / 3) + 30, (EnvelopePanel.this.getHeight() / 3) + 30 , 40);
            }

            repaint();
            if (++animationCounter > 80){
                timer.stop();
            }
        }
    }

    private class ImageData{
        private Image image;
        private Point position;
        private boolean drawing = false;

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
}
