package gui.pages;

import gui.BaseButton;
import gui.BasePage;
import gui.buttons.BackButton;
import gui.buttons.FastWithdrawButton;
import gui.buttons.MainPageButton;
import gui.buttons.StopTransactionButton;
import gui.dialogs.WithdrawDialog;

public class WithdrawPage extends BasePage {
    public static final String KEY = "WITHDRAWPAGE"; // Correct key for WithdrawPage
    public WithdrawPage() {
        super();

        page.add(titlePanel("Geld opnemen"));
        page.add(new StopTransactionButton().getButton());
        page.add(new BackButton(ChoicePage.KEY).getButton());
        page.add(new MainPageButton().getButton());
        page.add(new FastWithdrawButton().getButton());

    }
}