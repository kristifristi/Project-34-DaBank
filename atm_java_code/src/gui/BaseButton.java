package gui;

import javax.swing.*;
import java.awt.*;

public abstract class BaseButton {
    protected static final Font STD_FONT = new Font(Font.SANS_SERIF,Font.BOLD,24);
    protected static final int GUI_WIDTH = GUI.width;
    protected static final int GUI_HEIGHT = GUI.height;
    public static final int LEFT = 20; // Left side on GUI
    public static final int RIGHT = GUI_WIDTH - 520; // Right side on GUI
    protected JButton button;
    public static int Y_POS(int position) { // position is between 1 and 3, since there is a maximum of 4 buttons stacked.
        if (position <= 1) return 20; // top
        else if (position >= 3) return GUI_HEIGHT - 220; // bottom
        else return GUI_HEIGHT / 2 - 100; // middle
    }
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
