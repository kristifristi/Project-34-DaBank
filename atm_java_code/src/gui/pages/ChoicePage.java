package gui.pages;

import gui.*;
import gui.buttons.*;

import javax.swing.*;

public class ChoicePage extends BasePage{
    public static final String KEY = "CHOICEPAGE";
    public ChoicePage() {
        super();

        page.add(titlePanel("Keuzemenu"));
        page.add(new StopTransactionButton().getButton());
        page.add(new BackButton(HomePage.KEY).getButton());
        page.add(new WithdrawButton().getButton());
        page.add(new BalanceButton().getButton());
    }
}
