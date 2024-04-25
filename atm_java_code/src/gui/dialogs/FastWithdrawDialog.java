package gui.dialogs;

import server.GetInfo;

import java.io.IOException;

public class FastWithdrawDialog extends ServerCommDialog {
    @Override
    protected void comm(String rfid, String code) {
        try {
            GetInfo.post("http://145.24.223.74:8100/api/withdraw",
                    "{\"target\": \"im00imdb0123456789\",\"uid\": \"FFFFFFFF\",\"pincode\":" + code + ",\"amount\": 70}");
        } catch (IOException e) {
            System.out.println("Pinrequest went wrong");
        }
        if (GetInfo.getStatus() == 200) {
            System.out.println("OK");
        }
        else System.out.println("NO GO");
    }
}
