package gui.dialogs;

import gui.BaseDialog;
import gui.dialogs.prosessors.PinProcessor;
import gui.dialogs.prosessors.RfidProcessor;

public abstract class ServerCommDialog extends BaseDialog {
    public ServerCommDialog() {
        super((GUI_WIDTH/2-250),GUI_HEIGHT/2-100,500,200);
    }
    public void startTransaction() {
        Thread keypad = new Thread(new CreateDialog());
        keypad.start();
	}
    public void stopTransaction() {
        RfidProcessor.stopRfidScanner();
        PinProcessor.stopKeypad();
    }
    protected abstract void startUp();

    protected class CreateDialog implements Runnable {
        @Override
        public void run() {
            startUp();
        }
    }
}
