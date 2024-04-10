package gui.pages;

import gui.*;
import gui.buttons.*;
import com.fazecast.jSerialComm.SerialPortInvalidPortException;
import gui.dialogs.TransactionDialog;
import serial.ArduinoSerial;

import javax.swing.*;
import java.awt.*;

public class BalancePage extends TransactionPage{
    public static final String KEY = "BALANCEPAGE";
    public BalancePage() {
        super();

        page.add(new StopTransactionButton().getButton());
        page.add(new BackButton(ChoicePage.KEY).getButton());
        page.add(new MainPageButton().getButton());
    }
}
