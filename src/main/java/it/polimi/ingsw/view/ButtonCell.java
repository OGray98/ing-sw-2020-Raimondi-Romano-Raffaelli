package it.polimi.ingsw.view;

import it.polimi.ingsw.model.board.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ButtonCell extends JButton {

    private final Position position;

    public ButtonCell(int x, int y, int width, int height, ActionListener actionListener) {
        super();
        this.position = new Position(x, y);
        this.setOpaque(false);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setPreferredSize(new Dimension(width, height));
        this.setLayout(new BorderLayout());
        JLabel labelEmpty = new JLabel();
        labelEmpty.setOpaque(false);
        this.add(labelEmpty, BorderLayout.CENTER);
        this.addActionListener(actionListener);
    }

    public Position getPosition() {
        return position;
    }
}
