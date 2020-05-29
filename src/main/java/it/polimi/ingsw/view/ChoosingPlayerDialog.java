package it.polimi.ingsw.view;

import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.utils.GodLikeChooseFirstPlayerMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChoosingPlayerDialog extends GameDialog{

    public ChoosingPlayerDialog(JFrame frame, boolean isThreePlayerGame, ClientView clientView){
        super(frame,"Choosing first player");
        Font font = new Font("Impatto", Font.PLAIN, 18);
        JLabel labelGround = new JLabel("");
        Image imageGround = new ImageIcon("src/main/resources/Odyssey-Olympus.png").getImage().getScaledInstance(520,312,Image.SCALE_DEFAULT);
        labelGround.setIcon(new ImageIcon(imageGround));
        JLabel labelSel = new JLabel("SELECT FIRST PLAYER: ");
        labelSel.setFont(font);
        labelSel.setBounds(170,30,240,20);
        ButtonGroup g = new ButtonGroup();
        JRadioButton player0 = new JRadioButton("nick1");
        player0.setOpaque(false);
        player0.setFont(font);
        JRadioButton player1 = new JRadioButton("nick2");
        player1.setOpaque(false);
        player1.setFont(font);
        JRadioButton player2 = new JRadioButton("nick3");
        player2.setOpaque(false);
        player2.setFont(font);
        if(isThreePlayerGame){
            player0.setVisible(true);
            player1.setVisible(true);
            player2.setVisible(true);
        }else{
            player0.setVisible(true);
            player1.setVisible(true);
            player2.setVisible(false);
        }
        player0.setBounds(215,60,200,20);
        player1.setBounds(215,80,200,20);
        player2.setBounds(215,100,200,20);
        g.add(player0);
        g.add(player1);
        g.add(player2);

        player0.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(player0.isSelected()){
                    clientView.handleMessage(new GodLikeChooseFirstPlayerMessage(clientView.getPlayer(), PlayerIndex.PLAYER0));
                }
            }
        });

        player1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(player1.isSelected()){
                    clientView.handleMessage(new GodLikeChooseFirstPlayerMessage(clientView.getPlayer(), PlayerIndex.PLAYER1));
                }
            }
        });

        player2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(player2.isSelected()){
                    clientView.handleMessage(new GodLikeChooseFirstPlayerMessage(clientView.getPlayer(), PlayerIndex.PLAYER2));
                }
            }
        });

        labelGround.add(labelSel);
        labelGround.add(player0);
        labelGround.add(player1);
        labelGround.add(player2);
        add(labelGround);


    }
}
