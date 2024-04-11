package gui.buttons;

import gui.BaseButton;
import gui.GUI;
import gui.pages.ChoicePage;
import gui.pages.FastWithdrawPage;

import javax.swing.*;
import java.awt.*;

public class FastWithdrawButton extends BaseButton {
    public FastWithdrawButton() {
        super();
        button.setLocation(LEFT,Y_POS(2));
        button.setText("Snel opnemen");
        button.addActionListener(e -> GUI.gotoPage(FastWithdrawPage.KEY));
    }
}
