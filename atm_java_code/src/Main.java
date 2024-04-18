import com.fazecast.jSerialComm.SerialPortInvalidPortException;
import gui.*;
import serial.ArduinoHandler;
import serial.ArduinoSerial;
import server.GetInfo;

import javax.swing.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GUI.makeGUI();
//        try {
//            arduinoSerial = new ArduinoSerial();
//        } catch (SerialPortInvalidPortException e) {
//            System.out.println("Serial port doesn't exist");
//        }
//        Scanner scanner = new Scanner(System.in);
//        if (arduinoSerial != null) {
//            while (true) {
//                byte[] q = scanner.nextLine().getBytes();
//                arduinoSerial.sendSerial(q);
//                System.out.println("size: " + q.length);
//            }
//        }
    }
}