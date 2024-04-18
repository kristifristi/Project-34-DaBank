package gui.pages;

import gui.buttons.BackButton;
import gui.buttons.MainPageButton;
import gui.buttons.StopTransactionButton;

public class FastWithdrawPage extends ServerCommPage {
    public static final String KEY = "FASTWITHDRAWPAGE";
    public FastWithdrawPage() {
        super();

        page.add(titlePanel("Snel geld opnemen"));
        page.add(new StopTransactionButton().getButton());
        page.add(new BackButton(WithdrawPage.KEY).getButton());
        page.add(new MainPageButton().getButton());
    }
}
