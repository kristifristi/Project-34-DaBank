package gui.dialogs;

import gui.GUI;
import gui.dialogs.prosessors.AmountProcessor;
import gui.dialogs.prosessors.PinProcessor;
import gui.dialogs.prosessors.RfidProcessor;
import gui.pages.HomePage;
import server.GetInfo;

import java.io.IOException;

public class FastWithdrawDialog extends ServerCommDialog {
    protected void comm(String rfid, String code, int amount) {
        if (amount > 1) {
            String db = "";
            try {
                db = GetInfo.post("http://145.24.223.74:8100/api/withdraw",
                        "{\"target\": \"im00imdb0123456789\",\"uid\": \"" + rfid + "\",\"pincode\":" + code + ",\"amount\": " + amount + "}");
            } catch (IOException e) {
                System.out.println("Pinrequest went wrong");
            }
            if (GetInfo.getStatus() == 200) {
                getDisplayText().setText("Transactie succes");
                System.out.println("OK");
            }
            else if (GetInfo.getStatus() == 412) {
                System.out.println("No balance");
                getDisplayText().setText("Onvoldoende saldo");
            }
            else {
                System.out.println("NO GO");
                GUI.gotoPage(HomePage.KEY); // TODO
            }
        }
    }
    @Override
    protected void startUp() {

        // amount
        int amount;
        AmountProcessor amountProcessor = new AmountProcessor(getDisplayText());
        amount = amountProcessor.getAmount();
        amount = round(amount);
        if (amount < 1) {
            System.out.println("Couldn't get amount.");
            getDisplayText().setText("Bedrag is te klein");
            return;
        }
        getDisplayText().setText("Afgerond: " + amount);
        System.out.println(amount);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("Couldn't buy you time");
        }

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
        comm(rfid, pin, amount);
    }
    private static int round(int amount) {
        if (amount % 5 < 3) amount -= (amount % 5);
        else amount += 5 - (amount % 5);
        return amount;
    }
}
