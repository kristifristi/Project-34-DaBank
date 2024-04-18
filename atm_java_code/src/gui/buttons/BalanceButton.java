package gui.buttons;

import gui.BaseButton;
import gui.GUI;
import gui.pages.BalancePage;
import gui.pages.FastWithdrawPage;

public class BalanceButton extends BaseButton {
    public BalanceButton() {
        super();

        button.setLocation(BaseButton.RIGHT,BaseButton.Y_POS(2));
        button.setText("Saldo checken");
        button.addActionListener(e -> GUI.gotoPage(BalancePage.KEY));
    }
}
