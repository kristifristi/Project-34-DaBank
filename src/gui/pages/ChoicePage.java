package gui.pages;

import gui.*;
import gui.buttons.BackButton;
import gui.buttons.FastWithdrawButton;
import gui.buttons.StopTransactionButton;

import javax.swing.*;

public class ChoicePage extends BasePage{
    public static final String KEY = "CHOICEPAGE";
    public ChoicePage() {
        page.setSize(GUI_WIDTH,GUI_HEIGHT);

        page.add(new StopTransactionButton().getButton());
        page.add(new FastWithdrawButton().getButton());
        page.add(new BackButton(HomePage.KEY).getButton());

        JButton takeUp = createMenuButton(BaseButton.LEFT,BaseButton.Y_POS(3));
        takeUp.setText("Biljetten kiezen");
        takeUp.addActionListener(e -> System.out.println("clicked")); // placeholder, TODO
        page.add(takeUp);

        JButton showBankingInfo = createMenuButton(BaseButton.RIGHT,BaseButton.Y_POS(2));
        showBankingInfo.setText("Saldo Checken");
        showBankingInfo.addActionListener(e -> GUI.gotoPage(BalancePage.KEY)); // placeholder, TODO
        page.add(showBankingInfo);
    }
}
