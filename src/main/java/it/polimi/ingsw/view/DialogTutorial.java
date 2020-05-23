package it.polimi.ingsw.view;

import javax.swing.*;
import java.awt.*;

public class DialogTutorial extends GameDialog{

    private ImageContainer imageContainer = new ImageContainer();

    public DialogTutorial(JFrame frame, String title){
        super(frame,title);
        Font font = new Font("Impatto", Font.PLAIN, 13);
        JLabel labelGround = new JLabel("");
        Image imageGround = new ImageIcon("src/main/resources/Odyssey-Olympus.png").getImage().getScaledInstance(frame.getWidth()/2,frame.getHeight()/2,Image.SCALE_DEFAULT);
        labelGround.setIcon(new ImageIcon(imageGround));
        setResizable(false);
        Image imageEndTur = imageContainer.getButtonImage("buttonEndTurn").getScaledInstance(70,70,Image.SCALE_DEFAULT);
        Image imagePow = imageContainer.getButtonImage("buttonPower").getScaledInstance(70,70,Image.SCALE_DEFAULT);
        Image imageEx = imageContainer.getButtonImage("buttonMenu").getScaledInstance(70,70,Image.SCALE_DEFAULT);
        LabelCircle buttonEnd = new LabelCircle(new ImageIcon(imageEndTur),Color.WHITE);
        LabelCircle buttonPow = new LabelCircle(new ImageIcon(imagePow),Color.WHITE);
        LabelCircle buttonEx = new LabelCircle(new ImageIcon(imageEx),Color.WHITE);
        buttonEnd.setBounds(3,80,70,70);
        buttonPow.setBounds(3,3,70,70);
        buttonEx.setBounds(3,160,70,70);
        JLabel labelEx = new JLabel("Exit button : click on it to quit the game.");
        labelEx.setForeground(Color.BLACK);
        labelEx.setFont(font);
        JLabel labelEnd = new JLabel("End Turn button : click on it when you want to end your turn.");
        labelEnd.setForeground(Color.BLACK);
        labelEnd.setFont(font);
        JLabel labelPower = new JLabel("Use power button : click on it when you want to active your power,");
        labelPower.setForeground(Color.BLACK);
        labelPower.setFont(font);
        JLabel labelPower1 = new JLabel("remember each god have different power and different");
        labelPower1.setForeground(Color.BLACK);
        labelPower1.setFont(font);
        JLabel labelPower2 = new JLabel("state where active his power.");
        labelPower2.setForeground(Color.BLACK);
        labelPower2.setFont(font);
        JLabel labelInstruction = new JLabel("STATE: SELECT WORKER -> SELECT CELL -> MOVE -> SELECT CELL -> BUILD");
        labelInstruction.setForeground(Color.BLUE);
        labelInstruction.setFont(font);
        JLabel labelInstruction1 = new JLabel("POWER STATE: it depends of which god the player choose.");
        labelInstruction1.setForeground(Color.BLACK);
        labelInstruction1.setFont(font);
        labelInstruction1.setBounds(10,250,600,50);
        labelInstruction.setBounds(10,230,600,50);
        labelEx.setBounds(80,160,600,50);
        labelEnd.setBounds(80,85,600,50);
        labelPower.setBounds(80,-3,600,50);
        labelPower1.setBounds(80,10,600,50);
        labelPower2.setBounds(80,24,600,50);
        labelGround.add(labelPower2);
        labelGround.add(labelPower1);
        labelGround.add(labelPower);
        labelGround.add(labelEnd);
        labelGround.add(labelEx);
        labelGround.add(labelInstruction);
        labelGround.add(labelInstruction1);
        labelGround.add(buttonEx);
        labelGround.add(buttonEnd);
        labelGround.add(buttonPow);
        add(labelGround);
        setVisible(true);
    }
}
