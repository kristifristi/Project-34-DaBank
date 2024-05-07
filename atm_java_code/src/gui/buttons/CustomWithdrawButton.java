package gui.buttons;

import gui.BaseButton;
import gui.GUI;
import gui.pages.CustomWithdrawPage;
import gui.pages.FastWithdrawPage;

public class CustomWithdrawButton extends BaseButton {
    public CustomWithdrawButton() {
        super();
        button.setLocation(RIGHT,Y_POS(2));
        button.setText("Snel opnemen");
        button.addActionListener(e -> GUI.gotoPage(CustomWithdrawPage.KEY));
    }
}
