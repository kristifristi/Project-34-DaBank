package gui.dialogs.prosessors;

import serial.InputHandler;

import javax.swing.*;

public class RfidProcessor {
	private volatile String rfid;
	private final JLabel display;
	private static final String TEXT = "Scan uw pinpas.";
	private static volatile boolean going;
	public RfidProcessor(JLabel display) {
		this.display = display;

		going = true;

		Thread keyConsumer = new Thread(new RunnableRfidConsumer());
		Thread keyProducer = new Thread(new RunnableRfidProducer());

		keyConsumer.start();
		keyProducer.start();

		try {
			keyConsumer.join();
			keyProducer.join();
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
			System.out.println(this.rfid);
		}
	}
	public String getRfid() {
		return rfid;
	}
	public static void stopRfidScanner() {
		going = false;
	}
	private void rfidConsume() throws InterruptedException {
		while (true) {
			synchronized (this) {
				if (!going) throw new InterruptedException();
				display.setText(TEXT);
				wait();
				if (!going) throw new InterruptedException();
				if (rfid.contains("a")) { //TODO
					System.out.println("TransactionDialog: " + rfid);
					going = false;
					throw new InterruptedException(rfid);
				}
			}
		}
	}

	private void rfidProduce() throws InterruptedException {
		InputHandler.setDataNew(false);
		while (true) {
			synchronized (this) {
				if (!going) {
					notify();
					throw new InterruptedException();
				}
				wait(100);
				if (InputHandler.isNewData()) {
					rfid = InputHandler.getRfid();
					InputHandler.setDataNew(false);
					notify();
				}
			}
		}
	}

	private class RunnableRfidConsumer implements Runnable {
		@Override
		public void run() {
			try {
				rfidConsume();
			} catch (InterruptedException e) {
				going = false;
				rfid = e.getMessage();
			}
		}
	}
	private class RunnableRfidProducer implements Runnable {
		@Override
		public void run() {
			try {
				rfidProduce();
			} catch (InterruptedException ignored) {}
		}
	}
}
