package gui.dialogs;

import gui.BaseButton;
import gui.BaseDialog;
import gui.GUI;
import gui.pages.HomePage;

import javax.swing.*;
import java.awt.*;

public class ReceiptDialog extends BaseDialog {
    private final YNButtons buttons;
    public ReceiptDialog(JLabel page) {
        super((GUI_WIDTH/2-250), GUI_HEIGHT/2+300, 500, 100);
        this.buttons = new YNButtons();

        getDisplayText().setText("Wilt u een bonnetje?");
        getDisplayText().setVisible(false);

        page.add(getDisplayText());
        page.add(buttons.yesButton);
        page.add(buttons.noButton);
    }
    public void setVisible(boolean visible) {
        getDisplayText().setVisible(visible);
        buttons.yesButton.setVisible(visible);
        buttons.noButton.setVisible(visible);
    }

    private static class YNButtons {
        protected final JButton yesButton;
        protected final JButton noButton;
        protected YNButtons() {
            yesButton = new JButton();
            yesButton.setBounds(BaseButton.RIGHT,BaseButton.Y_POS(2),500,200);
            yesButton.setFocusable(false);
            yesButton.setFont(STD_FONT);
            yesButton.setForeground(Color.BLACK);
            yesButton.setBackground(Color.LIGHT_GRAY);
            yesButton.setBorderPainted(false);
            yesButton.setVisible(false);
            yesButton.setText("Ja");
            yesButton.addActionListener(e -> GUI.gotoPage(HomePage.KEY)); // TODO

            noButton = new JButton();
            noButton.setBounds(BaseButton.LEFT,BaseButton.Y_POS(2),500,200);
            noButton.setFocusable(false);
            noButton.setFont(STD_FONT);
            noButton.setForeground(Color.BLACK);
            noButton.setBackground(Color.LIGHT_GRAY);
            noButton.setBorderPainted(false);
            noButton.setVisible(false);
            noButton.setText("Nee");
            noButton.addActionListener(e -> GUI.gotoPage(HomePage.KEY)); // TODO
        }
    }

}
