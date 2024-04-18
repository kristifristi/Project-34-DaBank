package gui.pages;

import gui.BasePage;
import gui.GUI;
import gui.pages.HomePage;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Eindscherm extends BasePage {
    public static final String KEY = "EINDSCHERM";

    public Eindscherm() {
        super();

        // Set layout to null to manually position components
        page.setLayout(null);

        // Add a progress bar
        JProgressBar progressBar = new JProgressBar(0, 100); // Progress from 0 to 100
        int progressBarWidth = 400; // Set the width of the progress bar
        int progressBarHeight = 40; // Set the height of the progress bar
        int xPosition = (GUI_WIDTH - progressBarWidth) / 2; // Calculate the x-position to center the progress bar
        int yPosition = (GUI_HEIGHT - progressBarHeight) / 2 + 380; // Calculate the y-position to center the progress bar lower on the screen
        progressBar.setBounds(xPosition, yPosition, progressBarWidth, progressBarHeight); // Set position and size
        progressBar.setStringPainted(true); // Show percentage text
        progressBar.setForeground(Color.RED); // Set the foreground color of the progress bar
        progressBar.setBackground(Color.WHITE); // Set the background color of the progress bar
        progressBar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Add border to the progress bar
        progressBar.setFont(new Font("Arial", Font.BOLD, 20)); // Set font for the percentage text
        page.add(progressBar);

        // Schedule navigation to HomePage after 10 seconds
        /*Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int count = 0;

            @Override
            public void run() {
                count++;
                progressBar.setValue(count * 10); // Increment progress by 10% every second
                if (count == 10) {
                    GUI.gotoPage(HomePage.KEY);
                    timer.cancel(); // Stop the timer after navigation
                }
            }
        }, 600, 600); // Start after 1 second, repeat every 1 second*/
    }
}