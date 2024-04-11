package gui.pages;

import gui.BasePage;
import gui.dialogs.TransactionDialog;

public abstract class TransactionPage extends BasePage {
    protected final TransactionDialog transactionDialog;
    public TransactionPage() {
        super();
        transactionDialog = new TransactionDialog();
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
