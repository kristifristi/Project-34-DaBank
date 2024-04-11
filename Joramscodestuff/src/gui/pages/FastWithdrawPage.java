package gui.pages;

import gui.BasePage;
import gui.buttons.BackButton;
import gui.buttons.MainPageButton;
import gui.buttons.StopTransactionButton;
import gui.popups.TransactionDialog;

public class FastWithdrawPage extends BasePage {
    public static final String KEY = "FASTWITHDRAWPAGE";
    private final TransactionDialog transactionDialog;
    public FastWithdrawPage() {
        super();

        transactionDialog = new TransactionDialog();

        page.add(new StopTransactionButton().getButton());
        page.add(new BackButton(ChoicePage.KEY).getButton());
        page.add(new MainPageButton().getButton());
        page.add(transactionDialog.getDisplayText());
    }

    @Override
    public void setVisible(boolean visible) {
        page.setVisible(visible);
        if (visible) {
            transactionDialog.startTransaction();
        }
        else {
            transactionDialog.stopTransaction();
        }
    }
}
