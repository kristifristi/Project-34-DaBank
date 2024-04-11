package gui.buttons;

import gui.BaseButton;
import gui.GUI;
import gui.pages.HomePage;

import javax.swing.*;
import java.awt.*;

public class StopTransactionButton extends BaseButton {
    public StopTransactionButton() {
        super();
        button.setLocation(LEFT,Y_POS(1));
        button.setText("Stop transactie");
        button.setIcon(new ImageIcon("escape.png"));
        button.setHorizontalTextPosition(SwingConstants.RIGHT);
        button.setIconTextGap(20);
        button.addActionListener(e -> GUI.gotoPage(HomePage.KEY));
    }
}
