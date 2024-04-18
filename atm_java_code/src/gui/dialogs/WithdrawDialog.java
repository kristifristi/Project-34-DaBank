package gui.dialogs;

import server.GetInfo;

import java.io.IOException;

public class WithdrawDialog extends ServerCommDialog{
    @Override
    protected void comm(String rfid, String code) {
        // TODO
//        String db = "";
//        try {
//            db = GetInfo.getData("http://145.24.223.74:8100/noob/api/saldo?IBAN=5", "{\"pin\":" + code + ",\"uid\":\"9999\"}");
//        } catch (IOException e) {
//            System.out.println("POTATOES");
//        }
//        getDisplayText().setText(db);
    }
}
