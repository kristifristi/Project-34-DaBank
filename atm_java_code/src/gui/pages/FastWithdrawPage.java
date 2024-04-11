package gui.pages;

import gui.BasePage;
import gui.buttons.BackButton;
import gui.buttons.MainPageButton;
import gui.buttons.StopTransactionButton;
import gui.dialogs.TransactionDialog;

public class FastWithdrawPage extends TransactionPage {
    public static final String KEY = "FASTWITHDRAWPAGE";
    public FastWithdrawPage() {
        super();

        page.add(new StopTransactionButton().getButton());
        page.add(new BackButton(ChoicePage.KEY).getButton());
        page.add(new MainPageButton().getButton());
    }
}
