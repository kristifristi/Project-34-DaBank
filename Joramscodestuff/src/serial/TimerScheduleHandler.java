package serial;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.util.TimerTask;

public class TimerScheduleHandler extends TimerTask implements SerialPortDataListener {
    private final long timerStart;
    public TimerScheduleHandler(long timerStart) {
        this.timerStart = timerStart;
    }
    @Override
    public void run() {

        //System.out.println("Time elapsed: " + (System.currentTimeMillis() - this.timerStart) + " milliseconds");
    }

    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        if (serialPortEvent.getEventType() == SerialPort.LISTENING_EVENT_DATA_RECEIVED) {
            byte[] inputBuffer = serialPortEvent.getReceivedData();
            InputHandler.handleInput(inputBuffer);
        }
    }
}
