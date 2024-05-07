package gui.dialogs.prosessors;

import serial.InputHandler;

import javax.swing.*;

public class AmountProcessor {
    private volatile char keypress;
    private final JLabel display;
    private static final String TEXT = "Voer hoeveelheid in: ";
    private int amount;
    private static volatile boolean going;
    public AmountProcessor(JLabel display) {
        this.display = display;

        going = true;

        Thread keyConsumer = new Thread(new RunnableKeyConsumer());
        Thread keyProducer = new Thread(new RunnableKeyProducer());

        keyConsumer.start();
        keyProducer.start();

        try {
            keyConsumer.join();
            keyProducer.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            System.out.println(amount);
        }
    }
    public int getAmount() {
        return amount;
    }
    public static void stopKeypad() {
        going = false;
    }
    private void keyConsume() throws InterruptedException {
        String amount = "";
        while (true) {
            synchronized (this) {
                if (!going) throw new InterruptedException();
                display.setText(TEXT + amount);
                wait();
                if (keypress != 'K') {
                    amount = processInput(amount, keypress);
                    System.out.println("TransactionDialog: " + amount);
                }
                else {
                    if (amount.isEmpty()) continue;
                    throw new InterruptedException(amount);
                }
            }
        }
    }

    private void keyProduce() throws InterruptedException {
        InputHandler.setDataNew(false);
        while (true) {
            synchronized (this) {
                if (!going) {
                    notify();
                    throw new InterruptedException();
                }
                wait(10);
                if (InputHandler.isNewData()) {
                    keypress = InputHandler.getKeyPress();
                    InputHandler.setDataNew(false);
                    notify();
                }
            }
        }
    }

    private String processInput(String amount, char input) {
        switch (input) {
            case '/':
                return amount;
            case 'D':
                if (!amount.isEmpty()) return amount.substring(0,amount.length() - 1);
            case 'C':
                return "";
            default:
                if (amount.length() < 3) amount += input;
                return amount;
        }
    }

    private class RunnableKeyConsumer implements Runnable {
        @Override
        public void run() {
            try {
                keyConsume();
            } catch (InterruptedException e) {
                System.out.println("KeyConsumer stopped.");
                going = false;
                try {
                    amount = Integer.parseInt(e.getMessage());
                } catch (NumberFormatException ignored) {
                    System.out.println("Couldn't parse at AmountProcessor");
                }
            }
        }
    }
    private class RunnableKeyProducer implements Runnable {
        @Override
        public void run() {
            try {
                keyProduce();
            } catch (InterruptedException ignored) {}
            finally {
                System.out.println("KeyProducer stopped.");
            }
        }
    }
}
