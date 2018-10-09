import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/*
 * 16310943 James Byrne
 * 16314763 Jakub Gajewski
 * 16305706 Mark Hartnett
 *
 * The DicePanel class simulates the throwing of dice by drawing images of dice on a JPanel and cycling through
 * random numbers.
 */

public class DicePanel extends JPanel implements Runnable, ActionListener {
    /*
     * Class Die stores position and toss result for an individual die.
     */
    private class Die{
        private int xPosition, yPosition, number, lastRolledNumber;
        private double theta;
        private double rotationAngle;
        private int movementDistance = 28 + (random.nextInt(9) - 4);

        public Die(){
        }
    }

    private BufferedImage[] diceImages = new BufferedImage[6];
    private final int NUMBER_OF_DICE = 2;
    private Die[] dice;
    private Thread thread;
    private Timer timer;
    private final Random random = new Random();
    private boolean rolling = false;    // If true, dice should be drawn
    private boolean waiting = false;    // If true, dice are idle and waiting for timeout or user action
    private boolean running = false;    // If true, a thread in this class is running
    private int movementIncrement;      // Used to increment movement step when moving dice off the screen
    private final String imagePath = "images/dice/dice";
    private final ArrayList<Integer> totalDiceNumber = new ArrayList<>();

    public DicePanel(Board board) throws IOException{
        super();
        dice = new Die[NUMBER_OF_DICE];
        for (int i = 0; i < NUMBER_OF_DICE; i++)
            dice[i] = new Die();

        setOpaque(false);
        setBounds(0, 0, board.getXBoard(), board.getYBoard());

        for (int i = 0; i < 6; i++){
            diceImages[i] = ImageIO.read(getClass().getResource(imagePath + (i + 1) + ".png"));
        }
    }

    public void rollDice(){
        rolling = true;
        waiting = false;

        /** Get the general starting position and direction which the dice should follow. Random deviations will be added */
        Point startingPosition = getRandomStartingPosition();
        double theta = getStartingDirection(startingPosition.x, startingPosition.y);

        /** Set each individual die's position and randomly deviated angle */
        dice[0].xPosition = startingPosition.x - (int)(45 * Math.cos(theta + (Math.PI/2)));
        dice[0].yPosition = startingPosition.y - (int)(45 * Math.sin(theta + (Math.PI/2)));
        dice[0].theta = theta - ThreadLocalRandom.current().nextDouble(0, Math.PI / 12);
        dice[1].xPosition = startingPosition.x + (int)(45 * Math.cos(theta + (Math.PI/2)));
        dice[1].yPosition = startingPosition.y + (int)(45 * Math.sin(theta + (Math.PI/2)));
        dice[1].theta = theta + ThreadLocalRandom.current().nextDouble(0, Math.PI / 12);

        /** Main dice roll loop */
        for (int i = 0; i < 15; i++){
            /** Generate random dice numbers */
            for (int j = 0; j < NUMBER_OF_DICE; j++) {
                dice[j].number = random.nextInt(6) + 1;
                if (dice[j].number == dice[j].lastRolledNumber) {
                    switch (dice[j].number) {
                        case 1:
                            dice[j].number = random.nextInt(5) + 2;
                            break;
                        case 6:
                            dice[j].number = random.nextInt(5) + 1;
                            break;
                        default:
                            dice[j].number = random.nextBoolean() ? random.nextInt(dice[j].number - 1) + 1 : random.nextInt(6 - dice[j].number) + 1;
                    }
                }
                dice[j].lastRolledNumber = dice[j].number;
            }

            /** Set new positions for dice on the board */
            dice[0].xPosition += (int) (dice[0].movementDistance * Math.cos(dice[0].theta));
            dice[0].yPosition += (int) (dice[0].movementDistance * Math.sin(dice[0].theta));
            dice[1].xPosition += (int) (dice[1].movementDistance * Math.cos(dice[1].theta));
            dice[1].yPosition += (int) (dice[1].movementDistance * Math.sin(dice[1].theta));

            repaint();
            try{
                Thread.sleep(30 + (i * 10));
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        int totalDice = 0;
        for (int i = 0; i < NUMBER_OF_DICE; i++){
            totalDice += dice[i].number;
        }
        synchronized (totalDiceNumber){
            totalDiceNumber.add(totalDice);
            totalDiceNumber.notify();
        }
        waiting = true;
        synchronized (this) {
            try {
                wait(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        moveDiceOffScreen();
        do{

        } while (timer.isRunning());
        synchronized (this){
            notify();
        }
    }

    /**
     * Generate a random point from which the dice will be thrown. The point will always
     * lie on the circumference of a rectangle which is 70 pixels away from the edge of
     * the board.
     */
    private Point getRandomStartingPosition(){
        int x, y;
        boolean xFirst = random.nextBoolean();  // Decide whether to generate the x coordinate first or second
        if (xFirst){
            x = random.nextInt(getWidth() - 140) + 70;
            y = random.nextBoolean() ? 70 : getHeight() - 70;
        }
        else{
            y = random.nextInt(getHeight() - 140) + 70;
            x = random.nextBoolean() ? 70 : getWidth() - 70;
        }
        return new Point(x, y);
    }

    /**
     * Generate an angle which will generate a line from a given point to the centre of
     * the board, with a deviation of +/- 15 degrees
     */
    private double getStartingDirection(int x, int y){
        int centreX = getWidth() / 2;
        int centreY = getHeight() / 2;
        double theta = Math.atan2(centreY - y, centreX - x);
        if (random.nextBoolean())
            theta += ThreadLocalRandom.current().nextDouble(0, Math.PI / 12);
        else
            theta -= ThreadLocalRandom.current().nextDouble(0, Math.PI / 12);
        return theta;
    }

    public int getTotalDiceNumber(){
        int totalDice;
        synchronized (totalDiceNumber){
            while (totalDiceNumber.isEmpty()) {
                try {
                    totalDiceNumber.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            totalDice = totalDiceNumber.remove(0);
        }
        return totalDice;
    }

    private void moveDiceOffScreen(){
        movementIncrement = 1;
        timer = new Timer(20, this);
        timer.setInitialDelay(0);
        timer.start();
    }

    @Override
    public void run(){
        running = true;
        rollDice();
        running = false;
    }

    public void start(){
        thread = new Thread(this, "Dice roll thread");
        thread.start();
    }

    public boolean isRunning(){
        return running;
    }

    public void waitToFinish(){
        if (isRunning()){
            synchronized (this) {
                notify();
                try{
                    wait();
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;

        if (rolling) {
            BufferedImage[] diceToDraw = new BufferedImage[NUMBER_OF_DICE];
            for (int i = 0; i < NUMBER_OF_DICE; i++) {
                diceToDraw[i] = diceImages[dice[i].number - 1];
                if (!waiting) {
                    dice[i].rotationAngle = Math.PI * 2 * random.nextDouble();
                }
                AffineTransform transform = AffineTransform.getRotateInstance(dice[i].rotationAngle, dice[i].xPosition, dice[i].yPosition);
                g2.setTransform(transform);
                g2.drawImage(diceToDraw[i], dice[i].xPosition - (diceToDraw[i].getWidth() / 2), dice[i].yPosition - (diceToDraw[i].getHeight() / 2), this);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        for (int i = 0; i < NUMBER_OF_DICE; i++){
            dice[i].xPosition += 13 * (movementIncrement / 2);
        }
        movementIncrement++;
        repaint();
        if (dice[0].xPosition > getWidth() + 100 && dice[1].xPosition > getWidth() + 100) {
            timer.stop();
            waiting = false;
            rolling = false;
        }
    }
}
