package gui.dialogs;

import gui.BaseDialog;
import serial.InputHandler;
import server.GetInfo;

import javax.swing.Timer;
import java.io.IOException;

public class TransactionDialog extends BaseDialog {
    private String rfid = "";
    private String code = "";
    private final Timer checkRfid;
    private final Timer checkKeypad;
    private String text;
    public TransactionDialog() {
        super((GUI_WIDTH/2-250),GUI_HEIGHT/2-100,500,200);
        checkRfid = new Timer(100, e -> rfidAction());
        checkRfid.stop();
        checkRfid.setRepeats(false);
        checkKeypad = new Timer(10, e -> keypadAction());
        checkKeypad.stop();
        checkKeypad.setRepeats(false);
    }
    public void startTransaction() {
        //getDisplayText().setText("Scan uw pinpas");
        //InputHandler.clearRfid();
        //checkRfid.restart();
        getCode();
    }
    public void stopTransaction() {
        checkRfid.stop();
        checkKeypad.stop();
        rfid = "";
        code = "";
    }
    private void getCode() {
        text = "Voer uw pincode in: ";
        code = "";
        getDisplayText().setText(text);
        InputHandler.setDataNew(false);
        checkKeypad.restart();
    }
    private void rfidAction() {
        if (InputHandler.isNewData()) {
            if (InputHandler.getRfid().length() > 7) {
                rfid = InputHandler.getRfid();
                InputHandler.setDataNew(false);
                checkRfid.stop();
                getCode();
            }
            else {
                checkRfid.restart();
                System.out.println("Ping");
            }
        }
        else checkRfid.restart();
    }
    private void keypadAction() {
        if (InputHandler.isNewData()) {
            if (InputHandler.getKeyPress() != 'K') {
                code = processInput(InputHandler.getKeyPress());
                System.out.println("TransactionDialog: " + code);
                InputHandler.setDataNew(false);
                checkKeypad.restart();
                text = "Voer uw pincode in: ";
                for (int i = 0; i < code.length(); i++) text += '*';
                getDisplayText().setText(text);
            }
            else {
                if (code.length() < 4) {
                    checkKeypad.restart();
                    return;
                }
                System.out.println("TransactionDialog finished: " + code);
                checkKeypad.stop();
                String db = "";
                try {
                    db = GetInfo.getData("http://145.24.223.74:8100/noob/api/saldo?IBAN=5", "{\"pin\":" + code + ",\"uid\":\"9999\"}");
                } catch (IOException e) {
                    System.out.println("POTATOES");
                }
                getDisplayText().setText(db);
            }
        }
        else checkKeypad.restart();
    }
    private String processInput(char input) {
        switch (input) {
            case '/': return code;
            case 'D': if (!code.isEmpty()) {
                return code.substring(0,code.length() - 1);
            }
            case 'C': return "";
            default: if (code.length() < 6) {
                code += input;
            }
            return code;
        }
    }
}
