package serial;

import java.util.Timer;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortInvalidPortException;

public class ArduinoSerial {
    private final SerialPort serialPort;
    private ArduinoHandler arduinoHandler;
    public ArduinoSerial() throws SerialPortInvalidPortException {
        long timeStart = System.currentTimeMillis();

        serialPort = SerialPort.getCommPort("/dev/ttyUSB0");
        serialPort.setComPortParameters(2000000,8,1,0);
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 6);

        if(!serialPort.openPort()) {
            System.out.println("\nPort NOT available\n");
            return;
        }

        Runtime.getRuntime().addShutdownHook(new Thread(serialPort::closePort));

        arduinoHandler = new ArduinoHandler();

        serialPort.addDataListener(arduinoHandler);


        System.out.println("Listen: " + arduinoHandler.getListeningEvents());
    }
}
