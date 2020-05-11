package it.polimi.ingsw.view;

import it.polimi.ingsw.observer.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GUI extends JFrame{

    private JLabel label;
    private JPanel panel1;
    private static final Dimension FRAME_DIM = new Dimension(1400,820);
    private static final int ROW_NUM = 5;
    private JButton[][] buttonMatrix = new JButton[ROW_NUM][ROW_NUM];

    public GUI(){
        super("Santorini");
    }

    public void initGUI(){

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints lim = new GridBagConstraints();
        lim.ipadx = 100;
        lim.ipady = 100;

        setLayout(new BorderLayout());
        setPreferredSize(FRAME_DIM);
        setResizable(false);

        label = new JLabel("");
        Image image = new ImageIcon(this.getClass().getResource("/SantoriniBoard.png")).getImage().getScaledInstance(1400,800, Image.SCALE_DEFAULT);
        label.setIcon(new ImageIcon(image));

        panel1 = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                g.drawImage(image, 0 ,0,null);
            }
        };

        //this.getContentPane().add(label, BorderLayout.CENTER);

        panel1.setLayout(layout);
        for(int i = 0; i < 5; i++){
            lim.gridy = i;
            for(int j = 0; j < 5; j++){
                lim.gridx = j;
                buttonMatrix[i][j] = new JButton("" + i + j);
                buttonMatrix[i][j].setOpaque(false);
                buttonMatrix[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                layout.setConstraints(buttonMatrix[i][j], lim);
                panel1.add(buttonMatrix[i][j]);
            }
        }

        add(panel1, BorderLayout.CENTER);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
}
