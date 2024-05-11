package gui.dialogs;


import gui.dialogs.prosessors.CustomBillsProcessor;
import gui.dialogs.prosessors.PinProcessor;
import server.GetInfo;

import java.io.IOException;
import java.util.Arrays;

public class CustomWithdrawDialog extends ServerCommDialog {
    public CustomWithdrawDialog() {
        super(300);
    }
    protected void comm(String rfid, String code, int amount) {
        if (amount > 1) {
            String db = "";
            try {
                db = GetInfo.post("http://145.24.223.74:8100/api/withdraw",
                        "{\"target\": \"im00imdb0123456789\",\"uid\": \"FFFFFFFF\",\"pincode\":" + code + ",\"amount\": " + amount + "}");
            } catch (IOException e) {
                System.out.println("Pinrequest went wrong");
            }
            if (GetInfo.getStatus() == 200) {
                getDisplayText().setText("Transactie succes");
                System.out.println("OK");
            }
            else handleServerResponseNotOK(db);
        }
    }
    @Override
    protected void startUp() {

        // amount
        int[] amounts;
        CustomBillsProcessor customBillsProcessor = new CustomBillsProcessor(getDisplayText());
        amounts = customBillsProcessor.getAmounts();
        if (!Arrays.stream(amounts).allMatch(i -> i == 0)) {
			System.out.println(Arrays.toString(amounts));
		} else {
            System.out.println("Couldn't get amount.");
            return;
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
        comm(rfid, pin, getTotal(amounts));
    }
    private int getTotal(int[] amounts) {
        int total = 0;
        for (int amount : amounts) {
            total += amount;
        }
        return total;
    }
}
