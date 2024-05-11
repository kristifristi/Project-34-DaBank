package gui.dialogs.prosessors;

import serial.InputHandler;

import javax.swing.*;

public class CustomBillsProcessor {
	private volatile char keypress;
	private final JLabel display;
	private static final String BILL5 = "<html>Briefjes van 5: ";
	private static final String BILL10 = "<br><br>Briefjes van 10: ";
	private static final String BILL20 = "<br><br>Briefjes van 20: ";
	private static final String BILL50 = "<br><br>Briefjes van 50: ";
	private static final String CAP = "</html>";
	private int[] amounts = new int[4];
	private static volatile boolean going;
	public CustomBillsProcessor(JLabel display) {
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
			System.out.println(amounts);
		}
	}
	public int[] getAmounts() {
		return amounts;
	}
	public static void stopKeypad() {
		going = false;
	}
	private char[] keyConsume() throws InterruptedException {
		char[] amounts = {'\u0000','\u0000','\u0000','\u0000'};
		while (true) {
			synchronized (this) {
				if (!going) throw new InterruptedException();
				display.setText(BILL5 + amounts[0] + BILL10 + amounts[1] + BILL20 + amounts[2] + BILL50 + amounts[3] + CAP);
				wait();
				if (keypress != 'K') {
					processInput(amounts, keypress);
				}
				else {
					if (amountFilled(amounts)) return amounts;
				}
			}
		}
	}
	private boolean amountFilled(char[] amount) {
		for (int i = 0; i < 4; i++) {
			if (amount[i] == '\u0000') {
				System.out.println("hit");
				return false;
			}
		}
		System.out.println("Missed");
		return true;
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
					System.out.println(keypress);
					notify();
				}
			}
		}
	}

	private void processInput(char[] amounts, char input) {
		switch (input) {
			case '/':
				break;
			case 'D':
				for (int i = 3; i > 0; i--) {
					if (amounts[i] != '\u0000') {
						amounts[i] = '\u0000';
						return;
					}
				}
				break;
			case 'C':
				for (int i = 0; i < 4; i++) {
					amounts[i] = '\u0000';
				}
				break;
			default:
				for (int i = 0; i < 4; i++) {
					if (amounts[i] == '\u0000') {
						amounts[i] = input;
						return;
					}
				}
		}
	}

	private class RunnableKeyConsumer implements Runnable {
		@Override
		public void run() {
			try {
				char[] amountChars = keyConsume();
				for (int i = 0; i < 4; i++) {
					amounts[i] = Character.getNumericValue(amountChars[i]);
				}
				stopKeypad();
			} catch (InterruptedException ignored) {}
		}
	}
	private class RunnableKeyProducer implements Runnable {
		@Override
		public void run() {
			try {
				keyProduce();
			} catch (InterruptedException ignored) {}
		}
	}
}
