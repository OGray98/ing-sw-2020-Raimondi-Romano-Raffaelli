package it.polimi.ingsw.view.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

public class ButtonCircle extends JButton {
    Shape shape = null;
    private final Color colorButton;

    private boolean clicked;

    public ButtonCircle(Icon icon, Color colorButton, boolean illuminated,ActionListener listener) {
        super(icon);
        this.colorButton = colorButton;
        this.setEnabled(illuminated);
        this.clicked = false;
        this.addActionListener(listener);
        resize();
    }

    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())){
            shape = new Ellipse2D.Float(0,0,getWidth(),getHeight());
        }
        return shape.contains(x,y);
    }


    /**
     * @param g element use to paint the button
     * this method draw a circle around the button
     */
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Color c1 = new Color(100,100,100);
        Color c2 = colorButton;
        Color b1 = getBackground();
        int red = b1.getRed();
        int gre = b1.getGreen();
        int blu = b1.getBlue();
        Color newC = new Color(red-30,gre-30,blu-30);
        if (getModel().isPressed()){
            Color ap = c2;
            c2 = c1;
            c1 = ap;
            b1 = newC;
        }

        GradientPaint gr = new GradientPaint(0+10,0+10,c2,getSize().width-10,getSize().height-10,c1);
        g2.setPaint(gr);
        g2.fillOval(0,0,getSize().width-1,getSize().height-1);
        g2.setColor(b1);
        g2.fillOval(0+4,0+4,getSize().width-8,getSize().height-8);

        super.paintComponent(g);
    }

    /**
     * use to set the correct size of border
     */
    private void resize() {
        Dimension d = getPreferredSize();
        d.width = d.height = Math.max(d.width, d.height);
        setPreferredSize(d);
        setContentAreaFilled(false);
        setBorderPainted(false);

    }

    public boolean isClicked() {
        return this.clicked;
    }

    public void click() {
        this.clicked = !this.clicked;
    }
}

