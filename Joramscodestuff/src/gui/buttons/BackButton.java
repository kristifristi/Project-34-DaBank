package gui.buttons;

import gui.*;

import javax.swing.*;
import java.awt.*;

public class BackButton extends BaseButton{
    public BackButton(String keyPrevious) {
        super();
        button.setLocation(RIGHT,Y_POS(1));
        button.setText("Stap terug");
        button.setIcon(new ImageIcon("back.png"));
        button.setHorizontalTextPosition(SwingConstants.LEFT);
        button.setIconTextGap(20);
        button.addActionListener(e -> GUI.gotoPage(keyPrevious));
    }
}
