import java.awt.event.*;

/*
 * The class KeyboardListener allows the user to issue game commands by pressing keys on the keyboard. The functionality
 * works by passing text commands into the command panel depending on which key has been pressed.
 *
 * 16310943 James Byrne
 * 16314763 Jakub Gajewski
 * 16305706 Mark Hartnett
 */

public class KeyboardListener implements KeyListener{
    private CmdPanel cmdPanel;

    public KeyboardListener(CmdPanel cmdPanel){
        this.cmdPanel = cmdPanel;
    }

    /** Checks which key has been pressed and passes the corresponding text command to the command panel */
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        String command = null;
            switch (key) {
                case KeyEvent.VK_UP:
                    command = "u";
                    break;
                case KeyEvent.VK_DOWN:
                    command = "d";
                    break;
                case KeyEvent.VK_LEFT:
                    command = "l";
                    break;
                case KeyEvent.VK_RIGHT:
                    command = "r";
                    break;
                case KeyEvent.VK_R:
                    command = "roll";
                    break;
                case KeyEvent.VK_D:
                    command = "done";
                    break;
                case KeyEvent.VK_P:
                    command = "passage";
                    break;
                case KeyEvent.VK_N:
                    command = "notes";
                    break;
                case KeyEvent.VK_L:
                    command = "log";
                    break;
                case KeyEvent.VK_Q:
                    command = "question";
                    break;
                case KeyEvent.VK_A:
                    command = "accuse";
                    break;
                case KeyEvent.VK_1:
                    command = "1";
                    break;
                case KeyEvent.VK_2:
                    command = "2";
                    break;
                case KeyEvent.VK_3:
                    command = "3";
                    break;
                case KeyEvent.VK_4:
                    command = "4";
                    break;
                case KeyEvent.VK_ENTER:
                    command = "";
                    break;
                default:
        }
        if (command != null)
            cmdPanel.addCommand(command);
    }

    public void keyReleased(KeyEvent e){
        /** Do nothing */
    }

    public void keyTyped(KeyEvent e){
        /** Do nothing */
    }
}
