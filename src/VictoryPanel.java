import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * The class VictoryPanel displays confetti and plays music when a player wins the game
 *
 * 16310943 James Byrne
 * 16314763 Jakub Gajewski
 * 16305706 Mark Hartnett
 */

public class VictoryPanel extends JComponent {
    private ImageIcon confetti;
    private JLabel confettiLabel;
    private Button exitButton;
    private JLabel nameLabel;
    private JLabel titleLabel;

    public VictoryPanel(Board board, Player player){
        setBounds(board.getBounds());
        exitButton = new Button(new Point(getWidth() / 2, getHeight() - (getHeight() / 4)), "images/buttons/exit button.png");
        confetti = new ImageIcon(getClass().getResource("images/confetti.gif"));
        confettiLabel = new JLabel(confetti);
        confettiLabel.setBounds(
                (getWidth() / 2) - (confetti.getIconWidth() / 2), (getHeight() / 2) - (confetti.getIconHeight() / 2),
                confetti.getIconWidth(), confetti.getIconHeight());

        /* When exit button is pressed, it prompts the program to end */
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        /* Set up text labels' text, colors and size */
        nameLabel = new JLabel("And the winner is");
        nameLabel.setBounds(0, 50, getWidth(), 60);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setVerticalAlignment(SwingConstants.CENTER);
        nameLabel.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 50));
        nameLabel.setForeground(Color.RED);
        titleLabel = new JLabel(player.getPlayerName() + "!");
        titleLabel.setBounds(0, 110, getWidth(), 60);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 50));
        titleLabel.setForeground(Color.RED);

        add(exitButton);
        add(confettiLabel);
        add(nameLabel);
        add(titleLabel);
        setVisible(true);

        /* Play music */
        Audio party = new Audio(Sounds.PARTY);
        Audio win = new Audio((Sounds.INTRO));
    }
}
