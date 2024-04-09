package serial;

public abstract class InputHandler {
    private static char keyPress = '/';
    private static String rfid = "";
    private static volatile boolean isNewData;
    protected static void handleInput(byte[] input) {
        switch ((char) input[0]) {
            case 'N':
                keyPress = (char) input[1];
                isNewData = true;
                break;
            case 'R':
                rfid = "";
                for (int i = 1; i < input.length; i++) {
                    rfid += (char) input[i];
                }
                isNewData = true;
            default:
        }
    }

    public static void clearRfid() {
        rfid = "";
    }
    public static String getRfid() {
        return rfid;
    }

    public static char getKeyPress() {
        return keyPress;
    }

    public static void setDataNew(boolean isNewData) {
        InputHandler.isNewData = isNewData;
    }
    public static boolean isNewData() {
        return isNewData;
    }
}
