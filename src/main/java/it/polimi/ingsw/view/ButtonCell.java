package it.polimi.ingsw.view;

import it.polimi.ingsw.model.board.Position;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonCell extends JButton implements ActionListener {

    private final Position position;

    public ButtonCell(int x, int y) {
        super();
        this.position = new Position(x, y);
        //setOpaque(false);
        //setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //setLayout(new BorderLayout());
        this.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("[" + this.position.row + "][" + this.position.col + "]");
    }

}
