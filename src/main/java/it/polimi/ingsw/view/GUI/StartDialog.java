package it.polimi.ingsw.view.GUI;

import javax.swing.*;

public class StartDialog extends GameDialog {

    public static final Integer LENGTH_IP_ADDRESS = 15;

    private JTextField ipTextField = new JTextField(LENGTH_IP_ADDRESS);
    private JLabel insertIpLabel = new JLabel("Insert server IP:");

    public StartDialog(JFrame frame) {
        super(frame, "Select start information");


    }
}
