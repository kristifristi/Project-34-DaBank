package gui.pages;

import gui.BasePage;
import gui.dialogs.ServerCommDialog;

public abstract class ServerCommPage extends BasePage {
    protected ServerCommDialog serverCommDialog; // must be initialised in subclasses!
    public ServerCommPage() {
        super();
    }
    @Override
    public void setVisible(boolean visible) {
        page.setVisible(visible);
        if (serverCommDialog != null) {
            if (visible) {
                serverCommDialog.startTransaction();
            } else {
                serverCommDialog.stopTransaction();
            }
        }
    }
}
