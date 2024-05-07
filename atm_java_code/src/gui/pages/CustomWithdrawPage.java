package gui.pages;

import gui.BasePage;
import gui.buttons.BackButton;
import gui.buttons.FastWithdrawButton;
import gui.buttons.MainPageButton;
import gui.buttons.StopTransactionButton;
import gui.dialogs.CustomWithdrawDialog;
import gui.dialogs.FastWithdrawDialog;

public class CustomWithdrawPage extends ServerCommPage {
    public static final String KEY = "CUSTOMWITHDRAWPAGE"; // Correct key for WithdrawPage
    public CustomWithdrawPage() {
        super();

        serverCommDialog = new CustomWithdrawDialog();

        page.add(titlePanel("Snel geld opnemen"));
        page.add(new StopTransactionButton().getButton());
        page.add(new BackButton(WithdrawPage.KEY).getButton());
        page.add(new MainPageButton().getButton());
        page.add(serverCommDialog.getDisplayText());
    }
}