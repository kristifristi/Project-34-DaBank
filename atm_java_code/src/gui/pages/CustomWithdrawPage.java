package gui.pages;

import gui.buttons.BackButton;
import gui.buttons.MainPageButton;
import gui.buttons.StopTransactionButton;
import gui.dialogs.CustomWithdrawDialog;

public class CustomWithdrawPage extends ServerCommPage {
    public static final String KEY = "CUSTOMWITHDRAWPAGE"; // Correct key for WithdrawPage
    public CustomWithdrawPage() {
        super();

        serverCommDialog = new CustomWithdrawDialog();

        page.add(titlePanel("Zelf briefjes kiezen"));
        page.add(new StopTransactionButton().getButton());
        page.add(new BackButton(WithdrawPage.KEY).getButton());
        page.add(new MainPageButton().getButton());
        page.add(serverCommDialog.getDisplayText());
    }
}