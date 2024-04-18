package gui;

import javax.swing.*;
import java.awt.*;

public abstract class BasePage {
    protected static final int GUI_WIDTH = GUI.width;
    protected static final int GUI_HEIGHT = GUI.height;
    protected static final ImageIcon bankImg = GUI.bankImg;
    protected final JLabel page;
    public BasePage() {
        page = new JLabel(bankImg);
        page.setSize(GUI_WIDTH,GUI_HEIGHT);
    }
    public JLabel getPage() {
        return page;
    }
    public void setVisible(boolean visible) {
        page.setVisible(visible);
    }

    protected static JButton createMenuButton(int x, int y) {
        JButton button = new JButton();
        button.setSize(500,200);
        button.setLocation(x,y);
        button.setFocusable(false);
        button.setFont(new Font(Font.SANS_SERIF,Font.BOLD,24));
        button.setForeground(Color.BLACK);
        button.setBackground(Color.LIGHT_GRAY);
        button.setBorderPainted(false);
        return button;
    }
    protected static JLabel titlePanel(String text) {
        JLabel title = new JLabel(text);
        title.setFont(new Font(Font.SANS_SERIF,Font.BOLD,32));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setForeground(Color.BLACK);
        title.setBounds(GUI_WIDTH / 2 - 300,20,600,100);
        title.setOpaque(true);
        title.setBackground(new Color(215, 170, 35));
        return title;
    }
}
