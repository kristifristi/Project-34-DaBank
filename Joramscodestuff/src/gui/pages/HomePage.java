package gui.pages;

import gui.*;

import javax.swing.*;
import java.awt.*;

public class HomePage extends BasePage{
    public static final String KEY = "HOMEPAGE";
    public HomePage() {
        super();

        JPanel display = new JPanel();
        display.setBounds(0,(GUI_HEIGHT-100), GUI_WIDTH,50);
        display.setOpaque(false);
        JLabel text = new JLabel("Klik waar dan ook om verder te gaan");
        text.setFont(new Font(Font.SANS_SERIF,Font.BOLD,30));
        display.add(text);
        text.setForeground(Color.BLACK);
        page.add(display);

        JButton start = new JButton();
        start.setSize(GUI_WIDTH, GUI_HEIGHT);
        start.setOpaque(false);
        start.setFocusable(false);
        start.setContentAreaFilled(false);
        start.setBorderPainted(false);
        start.addActionListener(e -> GUI.gotoPage(ChoicePage.KEY));
        page.add(start);
    }
}
