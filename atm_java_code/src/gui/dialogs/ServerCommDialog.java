package gui.dialogs;

import com.google.gson.Gson;
import gui.BaseDialog;
import gui.dialogs.prosessors.AmountProcessor;
import gui.dialogs.prosessors.PinProcessor;
import gui.dialogs.prosessors.RfidProcessor;
import server.BankingData;
import server.GetInfo;

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
    protected void handleServerResponseNotOK(String db) {
        int status = GetInfo.getStatus();
        switch (status) {
            case 400:
                getDisplayText().setText("<html>Er ging iets fout.<br>Excuses voor het ongemak.</html>");
                break;
            case 401:
                getDisplayText().setText("<html>Foute pincode<br>Pogingen resterend:"
                    + getAttempts(db) + "</html>");
                break;
            case 403:
                getDisplayText().setText("Bankaccount geblokkeerd.");
                break;
            case 404:
                getDisplayText().setText("<html>Bank of rekening<br>bestaat niet.</html>");
                break;
            case 412:
                getDisplayText().setText("Onvoldoende saldo");
                break;
            case 500:
                getDisplayText().setText("<html>Servers niet beschikbaar<br/>Excuses voor het ongemak</html>");
                break;
            default:
                System.out.println("A new responsecode just dropped!");
        }
    }
    protected int getAttempts(String json) {
        Gson gson = new Gson();
        BankingData a = gson.fromJson(json, BankingData.class);
        return a.getAttempts_remaining();
    }
}
