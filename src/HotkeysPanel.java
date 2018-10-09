import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;

/*
 * The class HotkeysPanel draws a panel on the board which extends into view when moused over and is used to show all
 * keyboard commands implemented in the game.
 *
 * 16310943 James Byrne
 * 16314763 Jakub Gajewski
 * 16305706 Mark Hartnett
 */

public class HotkeysPanel extends JComponent implements MouseListener{
    private JLabel textLabel;
    private JLabel label;
    private Timer timer;

    public HotkeysPanel(Board board){
        setBounds(5, -(board.getHeight() / 3) + 20, board.getWidth() / 3 + 5, board.getHeight() / 3 + 5);
        label = new JLabel("Hotkeys");
        label.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 14));
        label.setBounds(5, getHeight() - 25, getWidth() - 10, 20);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);

        textLabel = new JLabel();
        textLabel.setBounds(5, 10, getWidth() - 10, getHeight() - 30);
        textLabel.setText("<html>R - roll dice<br/>Arrows - move token<br/>D - end turn<br/>1-4 - select room exit<br/>P - use secret passage<br/>N - show notes<br/>L - show log<br/>Q - question<br/>A - accusation</html>");
        textLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));

        add(label);
        add(textLabel);
        addMouseListener(this);
    }

    public void mouseEntered(MouseEvent e){
        if (timer != null)
            timer.stop();
        timer = new Timer(20, new PanelShow());
        timer.start();
    }

    public void mouseExited(MouseEvent e){
        if (timer != null)
            timer.stop();
        timer = new Timer(20, new PanelHide());
        timer.start();
    }

    public void mouseClicked(MouseEvent e){

    }

    public void mousePressed(MouseEvent e){

    }

    public void mouseReleased(MouseEvent e){

    }

    /** Action listener used to animate the extension of the panel */
    private class PanelShow implements ActionListener{
        int animationCounter = 0;
        @Override
        public void actionPerformed(ActionEvent e) {
            setBounds(5, (int)Easing.easeInQuad(animationCounter++, -getHeight() + 25, getHeight() - 30, 10), getWidth(), getHeight());
            repaint();
            if (animationCounter > 10)
                timer.stop();
        }
    }

    /** Action listener used to animate the hiding of the panel */
    private class PanelHide implements ActionListener{
        int animationCounter = 0;
        @Override
        public void actionPerformed(ActionEvent e) {
            setBounds(5, (int)Easing.easeInQuad(animationCounter++, -10, -getHeight() + 35, 10), getWidth(), getHeight());
            repaint();
            if (animationCounter > 10)
                timer.stop();
        }
    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.ORANGE);
        g2.fill(new Rectangle(2, 2, getWidth() - 4, getHeight() - 4));
        g2.setStroke(new BasicStroke(5));
        g2.setColor(Color.GRAY);
        g2.draw(new RoundRectangle2D.Float(2, 2, getWidth() - 4, getHeight() - 4, 5, 5));

        super.paintComponent(g);
    }
}
