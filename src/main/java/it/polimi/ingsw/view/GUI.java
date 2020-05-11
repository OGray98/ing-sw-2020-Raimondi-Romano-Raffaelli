package it.polimi.ingsw.view;

import it.polimi.ingsw.observer.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

public class GUI extends JFrame{

    private JLabel label;
    private JLabel labelWorker;
    private JLabel labelWorker2;
    private JLabel labelTerminal;
    private JPanel panel1;
    private JLabel labelGod;
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

        labelWorker = new JLabel(new ImageIcon(this.getClass().getResource("/icon_player.png")));
        labelWorker.setPreferredSize(new Dimension(5,5));

        labelWorker2 = new JLabel(new ImageIcon(this.getClass().getResource("/icon_player.png")));
        labelWorker2.setPreferredSize(new Dimension(5,5));

        labelTerminal = new JLabel("Terminal");
        Image imageTerminal = new ImageIcon(this.getClass().getResource("/bg_panelEdgeLeft.png")).getImage().getScaledInstance(350,800,Image.SCALE_DEFAULT);
        labelTerminal.setIcon(new ImageIcon(imageTerminal));

        labelGod = new JLabel("God");
        Image imageGod = new ImageIcon(this.getClass().getResource("/bg_panelEdgeRight.png")).getImage().getScaledInstance(350,800,Image.SCALE_DEFAULT);
        labelGod.setIcon(new ImageIcon(imageGod));


        panel1 = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                g.drawImage(image, 0 ,0,null);
                g.drawImage(imageTerminal,0,0,null);
                g.drawImage(imageGod,1050,0,null);
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
                buttonMatrix[i][j].setMargin(new Insets(0,0,0,0));
                buttonMatrix[i][j].setPreferredSize(new Dimension(20,20));
                buttonMatrix[i][j].setLayout(new BorderLayout());
                layout.setConstraints(buttonMatrix[i][j], lim);
                panel1.add(buttonMatrix[i][j]);
            }
        }

        buttonMatrix[0][3].add(labelWorker2,BorderLayout.CENTER);
        buttonMatrix[2][2].add(labelWorker,BorderLayout.CENTER);


        add(panel1,BorderLayout.CENTER);





        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
}
