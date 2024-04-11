package gui;

import javax.swing.*;
import java.awt.*;

public abstract class BaseButton {
    protected static final Font STD_FONT = new Font(Font.SANS_SERIF,Font.BOLD,24);
    protected static final int GUI_WIDTH = GUI.width;
    protected static final int GUI_HEIGHT = GUI.height;
    public static final int LEFT = 20; // Left side on GUI
    public static final int RIGHT = GUI_WIDTH - 520; // Right side on GUI
    public static int Y_POS(int position) { // position is between 1 and 4, since there is a maximum of 4 buttons stacked.
        position = (position < 1) ? 1 : (position > 4) ? 4 : position; // Is between 1 and 4, else make it between 1 and 4
        return (GUI_HEIGHT / 4) * --position + 20;
    }

    protected JButton button;
    public BaseButton() {
        button = new JButton();
        button.setSize(500,200);
        button.setFocusable(false);
        button.setFont(STD_FONT);
        button.setForeground(Color.BLACK);
        button.setBackground(Color.LIGHT_GRAY);
        button.setBorderPainted(false);
    }
    public JButton getButton() {
        return button;
    }
}
