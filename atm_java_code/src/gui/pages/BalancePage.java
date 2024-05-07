package gui.pages;

import gui.buttons.*;
import gui.dialogs.CheckBalanceDialog;
import gui.dialogs.ReceiptDialog;

public class BalancePage extends ServerCommPage {
    public static final String KEY = "BALANCEPAGE";
    private final ReceiptDialog receiptDialog;
    public BalancePage() {
        super();
        receiptDialog = new ReceiptDialog(getPage());

        serverCommDialog = new CheckBalanceDialog(receiptDialog);

        page.add(titlePanel("Saldo checken"));
        page.add(new StopTransactionButton().getButton());
        page.add(new BackButton(ChoicePage.KEY).getButton());
        page.add(new MainPageButton().getButton());
        page.add(serverCommDialog.getDisplayText());
    }
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        receiptDialog.setVisible(false);
    }
}
