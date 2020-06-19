package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.utils.NicknameMessage;
import it.polimi.ingsw.utils.TypeMatchMessage;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog that appears when the jar start and the player write his nickname and if he is the first that join the lobby he choose
 * how much players can join the game
 */
public class WelcomeDialog extends GameDialog {

    private String n;

    public WelcomeDialog(JFrame frame,ClientView clientView){
        super(frame, "Welcome");
        Font font = new Font("Impatto", Font.PLAIN, 18);
        JLabel labelGround = new JLabel("");
        JLabel labelConnected = new JLabel("YOU ARE CONNECTED!!");
        labelConnected.setFont(font);
        labelConnected.setForeground(Color.BLUE);
        labelConnected.setBounds(165,30,300,20);
        Image imageGround = new ImageIcon(this.getClass().getResource("/title_water.png")).getImage().getScaledInstance(520, 315, Image.SCALE_DEFAULT);
        labelGround.setIcon(new ImageIcon(imageGround));
        JTextField text = new JTextField("Insert name and press play", 20);

        JRadioButton box2Player = new JRadioButton("2-PLAYERS");
        JRadioButton box3Player = new JRadioButton("3-PLAYERS");
        ButtonGroup g = new ButtonGroup();
        if (clientView.getPlayer().equals(PlayerIndex.PLAYER0)) {
            box3Player.setVisible(true);
            box2Player.setVisible(true);
        } else {
            box3Player.setVisible(false);
            box2Player.setVisible(false);
        }
        box3Player.setForeground(Color.BLACK);
        box3Player.setFont(font);
        box2Player.setFont(font);
        box2Player.setForeground(Color.BLACK);
        box3Player.setBounds(200, 110, 200, 20);
        box2Player.setBounds(200, 90, 200, 20);
        box2Player.setOpaque(false);
        box3Player.setOpaque(false);
        text.setVisible(true);
        text.setBounds(150, 60, 240, 20);
        Image imagePlay = new ImageIcon(this.getClass().getResource("/button-play-down.png")).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);

        ButtonCircle buttonSend = new ButtonCircle(
                new ImageIcon(imagePlay),
                Color.WHITE,true,
                e -> {
                    if (box2Player.isSelected()){
                        clientView.showMessage("Waiting for others players");
                        clientView.handleMessage(new TypeMatchMessage(clientView.getPlayer(), false));
                    }
                    else if (box3Player.isSelected()){
                        clientView.showMessage("Waiting for others players");
                        clientView.handleMessage(new TypeMatchMessage(clientView.getPlayer(), true));}
                    clientView.handleMessage(new NicknameMessage(clientView.getPlayer(), text.getText()));
                    dispose();
                }

        );

        buttonSend.setBounds(210, 150, 95, 88);
        g.add(box2Player);
        g.add(box3Player);
        labelGround.add(labelConnected);
        labelGround.add(buttonSend);
        labelGround.add(box3Player);
        labelGround.add(box2Player);
        labelGround.add(text);
        add(labelGround);
    }


}
