package gui;

import javax.swing.*;
import java.awt.*;

public abstract class BaseDialog {
    protected static final Font STD_FONT = new Font(Font.SANS_SERIF,Font.BOLD,24);
    protected static final int GUI_WIDTH = GUI.width;
    protected static final int GUI_HEIGHT = GUI.height;
    protected final int popupWidth;
    protected final int popupHeight;
    private final JLabel displayText;
    public BaseDialog(int x, int y, int width, int height) {
        popupWidth = width;
        popupHeight = height;

        displayText = new JLabel();
        displayText.setFont(STD_FONT);
        displayText.setHorizontalAlignment(JLabel.CENTER);
        displayText.setForeground(Color.WHITE);
        displayText.setBounds(x,y,width,height);
        displayText.setOpaque(true);
        displayText.setBackground(Color.BLACK);
    }
    public JLabel getDisplayText() {
        return displayText;
    }
}
