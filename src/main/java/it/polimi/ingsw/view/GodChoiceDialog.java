package it.polimi.ingsw.view;

import it.polimi.ingsw.Client.ClientView;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import java.awt.*;

public class GodChoiceDialog extends JDialog {

    private JPanel panel = new JPanel();
    private JScrollPane scrollPane;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    //private ClientView view;

    public GodChoiceDialog(JFrame frame, List<JLabel> godsToShow){
        super(frame, "God choice");
        setPreferredSize(new Dimension(screenSize.width/2,screenSize.height/2));
        setResizable(false);
        setLocation(screenSize.width/4, screenSize.height/4);

        //this.view = view;
        GridBagLayout godLayout = new GridBagLayout();
        GridBagConstraints lim = new GridBagConstraints();
        lim.ipadx = 50;
        lim.ipady = 100;

        panel.setLayout(godLayout);

        for(JLabel l : godsToShow){
            panel.setBackground(Color.DARK_GRAY);
            godLayout.setConstraints(l, lim);
            panel.add(l);
        }

        scrollPane = new JScrollPane(panel);
        add(scrollPane, BorderLayout.CENTER);

        pack();
        setMinimumSize(new Dimension(300,30));
        setVisible(true);
    }
}
