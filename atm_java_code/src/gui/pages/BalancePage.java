package gui.pages;

import gui.buttons.*;
import gui.dialogs.CheckBalanceDialog;

public class BalancePage extends ServerCommPage {
    public static final String KEY = "BALANCEPAGE";
    public BalancePage() {
        super();

        serverCommDialog = new CheckBalanceDialog();

        page.add(titlePanel("Saldo checken"));
        page.add(new StopTransactionButton().getButton());
        page.add(new BackButton(ChoicePage.KEY).getButton());
        page.add(new MainPageButton().getButton());
        page.add(serverCommDialog.getDisplayText());
    }
}
