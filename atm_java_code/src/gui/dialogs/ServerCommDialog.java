package gui.dialogs;

import com.google.gson.Gson;
import gui.BaseDialog;
import gui.dialogs.prosessors.AmountProcessor;
import gui.dialogs.prosessors.PinProcessor;
import gui.dialogs.prosessors.RfidProcessor;
import server.BankingData;

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
        AmountProcessor.stopKeypad();
    }
    protected abstract void startUp();

    protected class CreateDialog implements Runnable {
        @Override
        public void run() {
            startUp();
        }
    }
    protected int getAttempts(String json) {
        Gson gson = new Gson();
        BankingData a = gson.fromJson(json, BankingData.class);
        return a.getAttempts_remaining();
    }
}
