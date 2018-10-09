import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

/*
 * The class Button implements a custom clickable JComponent used to create various buttons in the game
 *
 * 16310943 James Byrne
 * 16314763 Jakub Gajewski
 * 16305706 Mark Hartnett
 */

public class Button extends JComponent implements MouseListener {
    private Image image;
    private boolean mouseOver = false;
    private final ArrayList<ActionListener> listeners = new ArrayList<>();

    /** Creates button with specified location and image path. The position is specified at the centre of the button */
    public Button(Point position, String imagePath){
        try {
            image = ImageIO.read(getClass().getResource(imagePath));
        }
        catch (IOException e){
            e.printStackTrace();
        }
        setBounds(position.x - (image.getWidth(this) / 2) - 5, position.y - (image.getHeight(this) / 2) - 5, image.getWidth(this) + 10, image.getHeight(this) + 10);
        enableInputMethods(true);
        addMouseListener(this);
        setVisible(true);
    }

    /** Creates button with specified location and image object */
    public Button(Point position, Image image){
        this.image = image;
        setBounds(position.x - (image.getWidth(this) / 2) - 5, position.y - (image.getHeight(this) / 2) - 5, image.getWidth(this) + 10, image.getHeight(this) + 10);
        enableInputMethods(true);
        addMouseListener(this);
        setVisible(true);
    }

    public Dimension getPreferredSize(){
        return new Dimension(image.getWidth(this), image.getHeight(this));
    }
    public Dimension getMinimumSize(){
        return new Dimension(image.getWidth(this), image.getHeight(this));
    }
    public Dimension getMaximumSize(){
        return new Dimension(image.getWidth(this) + 10, image.getHeight(this) + 10);
    }

    public void mouseEntered(MouseEvent e){
        mouseOver = true;
        repaint();
    }

    public void mouseExited(MouseEvent e){
        mouseOver = false;
        repaint();
    }

    public void mousePressed(MouseEvent e){

    }

    public void mouseReleased(MouseEvent e){

    }

    public void mouseClicked(MouseEvent e){
        notifyListeners(e);
    }
    public void addActionListener(ActionListener l){
        listeners.add(l);
    }
    private void notifyListeners(MouseEvent e) {
        ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, new String(), e.getWhen(), e.getModifiers());
        synchronized(listeners) {
            for (int i = 0; i < listeners.size(); i++) {
                ActionListener tmp = listeners.get(i);
                tmp.actionPerformed(event);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (!mouseOver)
            g2.drawImage(image, 5, 5, this);
        else
            g2.drawImage(image.getScaledInstance(image.getWidth(this) + 10, image.getHeight(this) + 10, Image.SCALE_DEFAULT), 0, 0, this);
    }
}