package it.polimi.ingsw.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class GodIconDialog extends GameDialog implements ActionListener {

    private final ImageContainer imageContainer = new ImageContainer();
    private Map<String,String> godProfile;

    public GodIconDialog(JFrame frame,String godName){
        super(frame,"GOD PROFILE");
        Font font = new Font("Impatto", Font.PLAIN, 30);
        Font font1 = new Font("Impatto", Font.PLAIN, 10);
        JLabel labelGround = new JLabel("");
        Image imageGround = new ImageIcon("src/main/resources/Odyssey_UI_Backdrop.png").getImage().getScaledInstance(frame.getWidth()/2,frame.getHeight()/2,Image.SCALE_DEFAULT);
        labelGround.setIcon(new ImageIcon(imageGround));
        JLabel labelGod = new JLabel("");
        Image imageGod = imageContainer.getGodimage(godName).getScaledInstance(150,310,Image.SCALE_DEFAULT);
        labelGod.setIcon(new ImageIcon(imageGod));
        labelGod.setBounds(2,2,150,310);
        labelGround.add(labelGod);
        createMap();
        JLabel labelGodName = new JLabel("NAME: " + godName.toUpperCase());
        labelGodName.setBounds(170,10,600,50);
        labelGodName.setFont(font);
        labelGodName.setForeground(Color.WHITE);
        String power = getPower(godName);
        String[] powerSplit = power.split(",");
        String firstPart = powerSplit[0];
        String secondPart = powerSplit[1];
        JLabel labelPower = new JLabel("POWER: " );
        labelPower.setBounds(170,90,500,50);
        labelPower.setFont(font);
        labelPower.setForeground(Color.WHITE);
        JLabel labelFirst = new JLabel(firstPart);
        JLabel labelSecond = new JLabel(secondPart);
        labelFirst.setBounds(170,120,500,50);
        labelSecond.setBounds(170,140,500,50);
        labelSecond.setFont(font1);
        labelSecond.setForeground(Color.WHITE);
        labelFirst.setForeground(Color.WHITE);
        labelFirst.setFont(font1);
        JButton buttonClose = new JButton("CLOSE");
        buttonClose.setBounds(260,250,100,50);
        buttonClose.setForeground(Color.BLUE);
        buttonClose.addActionListener(this);
        labelGround.add(buttonClose);
        labelGround.add(labelSecond);
        labelGround.add(labelFirst);
        labelGround.add(labelGodName);
        labelGround.add(labelPower);
        add(labelGround);
    }

    private void createMap(){
        godProfile = new HashMap<>();
        godProfile.put("Apollo","Your Worker may move into an opponent Worker’s, space by forcing their Worker to the space yours just vacated.");
        godProfile.put("Artemis","Your Worker may move one additional time, but not back to its initial space.");
        godProfile.put("Athena","If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.");
        godProfile.put("Atlas","Your Worker may build, a dome at any level.");
        godProfile.put("Demeter","Your Worker may build one additional time, but not on the same space.");
        godProfile.put("Hephaestus","Your Worker may build one additional, block (not dome) on top of your first block.");
        godProfile.put("Minotaur","Our Worker may move into an opponent Worker’s space if their Worker, can be forced one space straight backwards to an unoccupied space.");
        godProfile.put("Pan","You also win if your Worker, moves down two or more levels.");
        godProfile.put("Prometheus","If your Worker does not move up, it may build both before and after moving.");
    }

    private String getPower(String nameGod){
        return godProfile.get(nameGod);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
    }
}
