package it.polimi.ingsw.view;


import it.polimi.ingsw.model.player.PlayerIndex;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Map;

public class ChoosingPlayerDialog extends GameDialog{

    private JRadioButton player0;
    private JRadioButton player1;
    private JRadioButton player2;

    public ChoosingPlayerDialog(JFrame frame, boolean isThreePlayerGame, Map<PlayerIndex, String> nickNames, ActionListener listenerSelect){
        super(frame,"Choosing first player");
        Font font = new Font("Impatto", Font.PLAIN, 18);
        JLabel labelGround = new JLabel("");
        Image imageGround = new ImageIcon("src/main/resources/Odyssey-Olympus.png").getImage().getScaledInstance(520,312,Image.SCALE_DEFAULT);
        labelGround.setIcon(new ImageIcon(imageGround));
        JLabel labelSel = new JLabel("SELECT FIRST PLAYER: ");
        labelSel.setFont(font);
        labelSel.setBounds(170,30,240,20);
        ButtonGroup g = new ButtonGroup();
        player0 = new JRadioButton(nickNames.get(PlayerIndex.PLAYER0));
        player0.setOpaque(false);
        player0.setFont(font);
        player1 = new JRadioButton(nickNames.get(PlayerIndex.PLAYER1));
        player1.setOpaque(false);
        player1.setFont(font);
        player2 = new JRadioButton(nickNames.get(PlayerIndex.PLAYER2));
        player2.setOpaque(false);
        player2.setFont(font);
        JButton buttonSend = new JButton("SEND");
        buttonSend.setBounds(210,150,70,40);
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
        buttonSend.addActionListener(listenerSelect);
        labelGround.add(labelSel);
        labelGround.add(player0);
        labelGround.add(player1);
        labelGround.add(player2);
        labelGround.add(buttonSend);
        add(labelGround);
    }

    public PlayerIndex getSelectedPlayer(){
        if(player0.isSelected()){
            return PlayerIndex.PLAYER0;
        }
        if(player1.isSelected()){
            return PlayerIndex.PLAYER1;
        }
        if(player2.isSelected()){
            return PlayerIndex.PLAYER2;
        }
        return null;
    }
}
