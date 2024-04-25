package gui.dialogs;

import gui.BaseDialog;
import gui.dialogs.prosessors.KeypadProcessor;
import gui.dialogs.prosessors.RfidProcessor;

public abstract class ServerCommDialog extends BaseDialog {
    private String rfid = "";
    private String pin = "";
    public ServerCommDialog() {
        super((GUI_WIDTH/2-250),GUI_HEIGHT/2-100,500,200);
    }
    public void startTransaction() {
        Thread keypad = new Thread(new CreateDialog());
        keypad.start();
	}
    public void stopTransaction() {
        RfidProcessor.stopRfidScanner();
        KeypadProcessor.stopKeypad();
    }
    protected abstract void comm(String rfid, String code);

    protected class CreateDialog implements Runnable {
        @Override
        public void run() {

            // rfid reader
//            RfidProcessor rfidProcessor = new RfidProcessor(getDisplayText());
//            rfid = rfidProcessor.getRfid();
//            if (rfid != null) System.out.println(rfid);
//            else {
//                System.out.println("Could not get rfid.");
//                return;
//            }

            // keypad
            KeypadProcessor keypadProcessor = new KeypadProcessor(getDisplayText());
            pin = keypadProcessor.getPinCode();
            if (pin != null) System.out.println(pin);
            else {
                System.out.println("Could not get pin.");
                return;
            }
            comm(rfid, pin);
        }
    }
}
