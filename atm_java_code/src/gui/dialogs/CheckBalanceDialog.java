package gui.dialogs;

import com.google.gson.Gson;
import gui.dialogs.prosessors.PinProcessor;
import gui.dialogs.prosessors.RfidProcessor;
import server.BankingData;
import server.GetInfo;

import java.io.IOException;

public class CheckBalanceDialog extends ServerCommDialog{
    private final ReceiptDialog receiptDialog;
    public CheckBalanceDialog(ReceiptDialog receiptDialog) {
        super();
        this.receiptDialog = receiptDialog;
    }

    protected void comm(String rfid, String code) {
        String db = "";
        try {
            db = GetInfo.post("http://145.24.223.74:8100/api/accountinfo",
                    "{\"target\": \"im00imdb0123456789\",\"pincode\":" + code + ",\"uid\": \"" + rfid + "\"}");
        } catch (IOException e) {
            System.out.println("Balance check went wrong");
        }
        if (GetInfo.getStatus() >= 500) {
            System.out.println("server error");
            getDisplayText().setText("<html>Servers niet beschikbaar<br/>Excuses voor het ongemak</html>");
        }
        else if (GetInfo.getStatus() >= 400) {
            System.out.println("client error");
            getDisplayText().setText("<html>Er ging iets fout<br/>Excuses voor het ongemak</html>");
        }
        else if (GetInfo.getStatus() == 200) {
            getDisplayText().setText(toString(db));
        }
        receiptDialog.setVisible(true);
    }
    private String toString(String json) {
        Gson gson = new Gson();
        BankingData a = gson.fromJson(json, BankingData.class);
        return "<html>Naam: " + a.getFirstname() + ' ' + a.getLastname()
                + "<br>Saldo: " + a.getBalance() + "</html>";
    }
    @Override
    protected void startUp() {

        // rfid reader
        String rfid = "FFFFFFFF";
//        RfidProcessor rfidProcessor = new RfidProcessor(getDisplayText());
//        rfid = rfidProcessor.getRfid();
//        if (rfid != null) System.out.println(rfid);
//        else {
//            System.out.println("Could not get rfid.");
//            return;
//        }

        // pincode
        String pin;
        PinProcessor pinProcessor = new PinProcessor(getDisplayText());
        pin = pinProcessor.getPinCode();
        if (pin != null) System.out.println(pin);
        else {
            System.out.println("Could not get pin.");
            return;
        }
        comm(rfid, pin);
    }
}
