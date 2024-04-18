package gui.dialogs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gui.GUI;
import server.BankingData;
import server.GetInfo;

import java.io.IOException;

public class CheckBalanceDialog extends ServerCommDialog{
    @Override
    protected void comm(String rfid, String code) {
        String db = "";
        try {
            db = GetInfo.post("http://145.24.223.74:8100/api/accountinfo",
                    "{\"target\": \"im00imdb0123456789\",\"pincode\":" + code + ",\"uid\": \"FFFFFFFF\"}");
        } catch (IOException e) {
            System.out.println("POTATOES");
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

    }
    private String toString(String json) {
        Gson gson = new Gson();
        BankingData a = gson.fromJson(json, BankingData.class);
        return "<html>Naam: " + a.getFirstname() + ' ' + a.getLastname()
                + "<br>Saldo: " + a.getBalance() + "<html/>";
    }
}
