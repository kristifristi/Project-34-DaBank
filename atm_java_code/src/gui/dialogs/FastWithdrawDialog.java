package gui.dialogs;

import gui.GUI;
import gui.dialogs.prosessors.PinProcessor;
import gui.dialogs.prosessors.RfidProcessor;
import gui.pages.HomePage;
import server.GetInfo;

import java.io.IOException;

public class FastWithdrawDialog extends ServerCommDialog {
    protected void comm(String rfid, String code) {
        try {
            GetInfo.post("http://145.24.223.74:8100/api/withdraw",
                    "{\"target\": \"im00imdb0123456789\",\"uid\": \"FFFFFFFF\",\"pincode\":" + code + ",\"amount\": -69999}");
        } catch (IOException e) {
            System.out.println("Pinrequest went wrong");
        }
        if (GetInfo.getStatus() == 200) {
            System.out.println("OK");
        }
        else {
            System.out.println("NO GO");
            GUI.gotoPage(HomePage.KEY); // TODO
        }
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
