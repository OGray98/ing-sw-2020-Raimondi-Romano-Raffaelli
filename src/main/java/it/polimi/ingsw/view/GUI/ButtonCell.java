package it.polimi.ingsw.view.GUI;
import it.polimi.ingsw.model.board.Position;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Class used to create a JButton used to represents a cell in the board
 * */
public class ButtonCell extends JButton {
    private final Position position;
    public ButtonCell(int x, int y, int width, int height, ActionListener actionListener) {

        super();
        this.position = new Position(x, y);
        this.setOpaque(false);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setPreferredSize(new Dimension(width, height));
        this.setLayout(new BorderLayout());
        this.addActionListener(actionListener);
    }
    public Position getPosition() {
        return position;
    }
}
