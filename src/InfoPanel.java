import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

/*
16310943 James Byrne
16314763 Jakub Gajewski
16305706 Mark Hartnett
 */

public class InfoPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int TEXT_AREA_HEIGHT = 38;
    private static final int CHARACTER_WIDTH = 60;
    private static final int FONT_SIZE = 12;

    private final JTextArea textArea;

    InfoPanel(String text) {
        textArea  = new JTextArea(TEXT_AREA_HEIGHT, CHARACTER_WIDTH);
        JScrollPane scrollPane = new JScrollPane(textArea);
        DefaultCaret caret = (DefaultCaret)textArea.getCaret();
        textArea.setEditable(false);
        textArea.setFont(new Font("monospaced", Font.PLAIN, FONT_SIZE));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setText(textArea.getText()+text);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        setLayout(new BorderLayout());
        setBorder(new JTextField().getBorder());
        add(scrollPane, BorderLayout.CENTER);
    }

    public void addText(String text) {
        textArea.setText(textArea.getText()+"\n"+text);
    }

    public void clear(){
        textArea.setText(null);
    }
}