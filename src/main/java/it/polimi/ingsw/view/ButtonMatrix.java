package it.polimi.ingsw.view;

import it.polimi.ingsw.model.board.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonMatrix extends JButton implements ActionListener {

    private final int i;
    private final int j;

    public ButtonMatrix(int i,int j){
        super("" + i + j);
        this.i = i;
        this.j = j;
        setOpaque(false);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setLayout(new BorderLayout());
        this.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e){
        ButtonMatrix buttonClicked = (ButtonMatrix)e.getSource();
        Position newPos = new Position(buttonClicked.i,buttonClicked.j);
        //Crea corretamente newPos....
    }

}
