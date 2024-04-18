package gui.buttons;

import gui.BaseButton;
import gui.GUI;
import gui.pages.WithdrawPage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WithdrawButton extends BaseButton {
    public WithdrawButton() {
        super();
        button.setLocation(LEFT, Y_POS(2));
        button.setText("Opnemen");
        button.addActionListener(e -> GUI.gotoPage(WithdrawPage.KEY));
    }
}