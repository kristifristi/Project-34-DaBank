package gui.dialogs;

import server.GetInfo;

import java.io.IOException;

public class CheckBalanceDialog extends ServerCommDialog{
    @Override
    protected void comm(String rfid, String code) {
        String db = "";
        try {
            db = GetInfo.get("http://145.24.223.74:8100/api/noob/accountinfo?iban=im00imdb0123456789&uid=FFFFFFFF&pin=" + code);
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
        json = json.replace("{", "");
        json = json.replace("}", "");
        json = json.replace("\"", "");
        if (json.contains("firstname") && json.contains("lastname")) {
            json = json.replace("firstname:","");
            json = json.replace(",lastname:"," ");
        }
        json = json.replace(":",": ");
        json = json.replace(",","<br>");
        return "<html>" + json + "</html>";
    }
}
