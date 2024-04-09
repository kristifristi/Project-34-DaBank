package gui.buttons;

import gui.*;
import gui.pages.ChoicePage;

import javax.swing.*;
import java.awt.*;

public class MainPageButton extends BaseButton{
    public MainPageButton() {
        super();
        button.setLocation(RIGHT,Y_POS(4));
        button.setText("Hoofdmenu");
        button.setIcon(new ImageIcon("home.png"));
        button.setHorizontalTextPosition(SwingConstants.LEFT);
        button.setIconTextGap(20);
        button.addActionListener(e -> GUI.gotoPage(ChoicePage.KEY));
    }
}
