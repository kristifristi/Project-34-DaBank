package gui.dialogs;

import com.google.gson.Gson;
import server.BankingData;
import server.GetInfo;

import java.io.IOException;

public class WithdrawDialog extends ServerCommDialog{
    private int amount;
    @Override
    protected void comm(String rfid, String code) {
        if (amount > 1) {
            String db = "";
            try {
                db = GetInfo.post("http://145.24.223.74:8100/api/withdraw",
                        "{\"target\": \"im00imdb0123456789\",\"uid\": \"FFFFFFFF\",\"pincode\":" + code + ",\"amount\": " + amount + "}");
            } catch (IOException e) {
                System.out.println("Pinrequest went wrong");
            }
            if (GetInfo.getStatus() >= 500) {
                System.out.println("server error");
                getDisplayText().setText("<html>Servers niet beschikbaar<br/>Excuses voor het ongemak</html>");
            } else if (GetInfo.getStatus() >= 400) {
                System.out.println("client error");
                getDisplayText().setText("<html>Er ging iets fout<br/>Excuses voor het ongemak</html>");
            } else if (GetInfo.getStatus() == 200) {
                getDisplayText().setText(toString(db));
            }
        }
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    private String toString(String json) {
        Gson gson = new Gson();
        BankingData a = gson.fromJson(json, BankingData.class);
        return "<html>Naam: " + a.getFirstname() + ' ' + a.getLastname()
                + "<br>Saldo: " + a.getBalance() + "<html/>";
    }
    public void startTransaction() {

    }
}
