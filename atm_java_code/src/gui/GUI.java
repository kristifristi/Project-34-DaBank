package gui;

import gui.pages.BalancePage;
import gui.pages.ChoicePage;
import gui.pages.FastWithdrawPage;
import gui.pages.HomePage;
import serial.ArduinoSerial;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public abstract class GUI {
    private static JFrame frame;
    protected static ImageIcon bankImg;
    private static final HashMap<String, BasePage> pages = new HashMap<>();
    protected static int width;
    protected static int height;
    private static Timer timeOut = new Timer(120000, e -> timeoutAction());
    private static ArduinoSerial arduino = new ArduinoSerial();

    public static void makeGUI() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        width = tk.getScreenSize().width;
        height = tk.getScreenSize().height;

        try {
            bankImg = new ImageIcon("DABANKCOLLOR.png");
        }catch (Exception e) {
            System.out.println("Image not found");
        }

        pages.put(HomePage.KEY,new HomePage());
        pages.put(ChoicePage.KEY,new ChoicePage());
        pages.put(BalancePage.KEY,new BalancePage());
        pages.put(FastWithdrawPage.KEY,new FastWithdrawPage());

        frame = new JFrame("TestingInProgress");
        frame.setIconImage(bankImg.getImage());
        for (BasePage page : pages.values()) {
            frame.add(page.getPage());
            page.setVisible(false);
        }
        pages.get(HomePage.KEY).setVisible(true);
        frame.getContentPane().setBackground(new Color(205,14,14));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        System.out.println(pages.size());
        timeOut.setRepeats(false);
    }
    private static void timeoutAction() {
        gotoPage(HomePage.KEY);
    }
    public static void gotoPage(String key) {
        if (key.equals(HomePage.KEY)) {
            timeOut.stop();
        }
        else {
            timeOut.restart();
        }
        for (BasePage page : pages.values()) {
            page.setVisible(false);
        }
        pages.get(key).setVisible(true);
    }
}
