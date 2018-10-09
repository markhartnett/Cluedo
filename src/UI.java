import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/*
16310943 James Byrne
16314763 Jakub Gajewski
16305706 Mark Hartnett
 */

public class UI extends JFrame implements MouseListener {
    private final JPanel boardPanel;
    private JLayeredPane layers;
    private Board board;
    private InfoPanel info;
    private CmdPanel cmd;
    private HotkeysPanel hotkeysPanel;
    private KeyboardListener listener;

    // UI constructor
    UI() {
        super("Cluelessdo"); // constructor of super class, parameter is the title of the frame

        boardPanel = new JPanel(new BorderLayout());  // Panel which contains the JLayeredPane
        layers = new JLayeredPane();    // Layered container for board and token drawing panel
        board = new Board(); // board component
        info = new InfoPanel("Welcome to Cluelessdo! Can you solve the mystery of the murder of Gunther?\n\n" +
                "At any time during the game, click on the board window to use key commands or click on the command line below to type commands.\n" +
                "Once the game starts, see the hotkeys dropdown box for all hotkeys."); // info panel
        cmd = new CmdPanel(); // command pannel
        hotkeysPanel = new HotkeysPanel(board);
        listener = new KeyboardListener(cmd);
        boardPanel.setFocusable(true);
        boardPanel.addMouseListener(this);
        boardPanel.addKeyListener(listener);
        board.add(hotkeysPanel);

        info.setPreferredSize(new Dimension(400, 100)); // set the preferred size of the info panel

        boardPanel.setSize(board.getXBoard(), board.getYBoard());
        boardPanel.add(layers);
        board.setBounds(0, 0, board.getXBoard(), board.getYBoard());

        layers.add(board, Integer.valueOf(1));

        setSize(board.getXBoard() + 410, board.getYBoard() + 60); // set size of the UI

        setResizable(false); // make frame non resizeable
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // set the close operation to exit
        setLayout(new BorderLayout()); // set the layout to border layout

        add(boardPanel, BorderLayout.CENTER); // add the board to the center of the frame
        add(info, BorderLayout.EAST); // add the info panel to the east of the screen
        add(cmd, BorderLayout.PAGE_END); // add the command panel to the bottom of the screen
    }

    public Board getBoard() {
        return board;
    }

    public JLayeredPane getLayers() {
        return layers;
    }

    public InfoPanel getInfo() {
        return info;
    }

    public CmdPanel getCmd() {
        return cmd;
    }

    public void mouseClicked(MouseEvent e){
        boardPanel.requestFocus();
    }

    public void mouseExited(MouseEvent e){
        /** Do nothing */
    }

    public void mouseReleased(MouseEvent e){
        /** Do nothing */
    }

    public void mousePressed(MouseEvent e){
        /** Do nothing */
    }

    public void mouseEntered(MouseEvent e){
        /** Do nothing */
    }
}
