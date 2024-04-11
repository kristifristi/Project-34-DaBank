package gui.pages;

import gui.*;
import gui.buttons.*;
import com.fazecast.jSerialComm.SerialPortInvalidPortException;
import serial.ArduinoSerial;

import javax.swing.*;
import java.awt.*;

public class BalancePage extends BasePage{
    public static final String KEY = "BALANCEPAGE";
    private static String displayText;
    private static final JPanel display = new JPanel();;
    private static final JLabel text = new JLabel(displayText);
    public BalancePage() {
        page.setSize(GUI_WIDTH,GUI_HEIGHT);

        display.setBounds(GUI_WIDTH / 2 - 250,GUI_HEIGHT / 2 - 50,500,100);
        displayText = "Input: ";
        text.setText(displayText);
        text.setFont(new Font(Font.SANS_SERIF,Font.BOLD,25));
        display.add(text);
        text.setForeground(Color.BLACK);
        display.setBackground(Color.LIGHT_GRAY);
        page.add(display);

        page.add(new StopTransactionButton().getButton());
        page.add(new BackButton(ChoicePage.KEY).getButton());
        page.add(new MainPageButton().getButton());
    }
}
