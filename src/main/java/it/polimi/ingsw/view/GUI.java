package it.polimi.ingsw.view;

import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.BuildViewMessage;
import it.polimi.ingsw.utils.MoveMessage;
import it.polimi.ingsw.utils.PutWorkerMessage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;

public class GUI extends ClientView {

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
    }

    public void initGUI(){
        JFrame frame = new JFrame("Santorini");

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints lim = new GridBagConstraints();
        lim.ipadx = 100;
        lim.ipady = 100;


        frame.setLayout(new BorderLayout());
        frame.setPreferredSize(FRAME_DIM);
        frame.setResizable(false);

        label = new JLabel("");
        Image image = new ImageIcon(this.getClass().getResource("/SantoriniBoard.png")).getImage().getScaledInstance(1400,800,Image.SCALE_DEFAULT);
        label.setIcon(new ImageIcon(image));
        label.setLayout(new BorderLayout());



        labelWorker = new JLabel(new ImageIcon(this.getClass().getResource("/icon_player.png")));
        labelWorker.setPreferredSize(new Dimension(5,5));

        labelWorker2 = new JLabel(new ImageIcon(this.getClass().getResource("/icon_player.png")));
        labelWorker2.setPreferredSize(new Dimension(5,5));

        labelTerminal = new JLabel("");
        Image imageTerminal = new ImageIcon(this.getClass().getResource("/bg_panelEdgeLeft.png")).getImage().getScaledInstance(350,800,Image.SCALE_DEFAULT);
        labelTerminal.setIcon(new ImageIcon(imageTerminal));


        labelGod = new JLabel("");
        Image imageGod = new ImageIcon(this.getClass().getResource("/bg_panelEdgeRight.png")).getImage().getScaledInstance(350,800,Image.SCALE_DEFAULT);
        labelGod.setIcon(new ImageIcon(imageGod));


        JLabel labelBorderGod = new JLabel("");
        Image imageBorderGod = new ImageIcon(this.getClass().getResource("/clp_frame_gold.png")).getImage().getScaledInstance(150,230,Image.SCALE_DEFAULT);
        labelBorderGod.setIcon(new ImageIcon(imageBorderGod));
        labelBorderGod.setBounds(90,140,150,230);
        labelGod.add(labelBorderGod);





        panel1 = new JPanel();
        panel1.setLayout(layout);
        for(int i = 0; i < 5; i++){
            lim.gridy = i;
            for(int j = 0; j < 5; j++){
                lim.gridx = j;
                buttonMatrix[i][j] = new JButton("" + i + j);
                buttonMatrix[i][j].setOpaque(false);
                buttonMatrix[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                buttonMatrix[i][j].setPreferredSize(new Dimension(18,19));
                buttonMatrix[i][j].setLayout(new BorderLayout());
                layout.setConstraints(buttonMatrix[i][j], lim);
                panel1.add(buttonMatrix[i][j]);

            }
        }


        buttonMatrix[0][3].add(labelWorker2,BorderLayout.CENTER);
        buttonMatrix[2][2].add(labelWorker,BorderLayout.CENTER);


        panel1.setOpaque(false);


        ButtonCircle buttonMove;
        ButtonCircle buttonBuild;
        ButtonCircle buttonPower;
        ButtonCircle buttonEndTurn;
        ButtonCircle buttonTutorial;
        ButtonCircle buttonMenu;

        JButton buttonGod;
        JButton buttonExit = new JButton();
        buttonGod = getIconGodProfile( "src/main/resources/_0000s_0004_god_and_hero_cards_0052_Prometheus.png");
        labelBorderGod.add(buttonGod);



        JTextArea textArea = new JTextArea(50, 60);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        for (int i = 0; i < 150; i++) {
            textArea.append("Hello world!");
        }
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.GREEN);
        JScrollPane terminal = new JScrollPane(textArea);
        terminal.setBackground(Color.BLACK);
        terminal.setBounds(60,170,250,280);

        labelTerminal.add(terminal);


        try{
            BufferedImage imageMove = ImageIO.read(new File("src/main/resources/castorpollux_bothMove.png"));
            BufferedImage imageBuild = ImageIO.read(new File("src/main/resources/castorpollux_bothBuild.png"));
            BufferedImage imagePower = ImageIO.read(new File("src/main/resources/heropower_active_small.png"));
            BufferedImage imageEndTurn = ImageIO.read(new File("src/main/resources/Chronus_ClockFace.png"));
            BufferedImage imageTutorial = ImageIO.read(new File("src/main/resources/TutorialOn.png"));
            BufferedImage imageMenu = ImageIO.read(new File("src/main/resources/menu_button.png"));
            BufferedImage imageExit = ImageIO.read(new File("src/main/resources/Exit.png"));



            //Resized image
            BufferedImage scaledExit = getScaledImage(imageExit,60,50);
            BufferedImage scaledMenu = getScaledImage(imageMenu,95,95);
            BufferedImage scaledTutorial = getScaledImage(imageTutorial,90,90);
            BufferedImage scaledEndTurn = getScaledImage(imageEndTurn,100,100);
            BufferedImage scaledPower = getScaledImage(imagePower,90,90);
            BufferedImage scaledBuild = getScaledImage(imageBuild,90,90);
            BufferedImage scaledImage = getScaledImage(imageMove,90,90);

            //Setting icon button
            buttonExit.setIcon(new ImageIcon(scaledExit));
            buttonMenu = new ButtonCircle(new ImageIcon(scaledMenu));
            buttonTutorial = new ButtonCircle(new ImageIcon(scaledTutorial));
            buttonEndTurn = new ButtonCircle(new ImageIcon(scaledEndTurn));
            buttonPower = new ButtonCircle(new ImageIcon(scaledPower));
            buttonBuild = new ButtonCircle(new ImageIcon(scaledBuild));
            buttonMove = new ButtonCircle(new ImageIcon(scaledImage));

            //Setting dimension button
            buttonExit.setBounds(285,5,55,50);
            buttonMenu.setBounds(200,470,85,85);
            buttonTutorial.setBounds(60,470,92,92);
            buttonEndTurn.setBounds(180,365,90,90);
            buttonPower.setBounds(50,365,95,95);
            buttonBuild.setBounds(180,470,90,90);
            buttonMove.setBounds(50,470,90,90);
            buttonExit.setOpaque(false);

            //Add button in label
            labelGod.add(buttonExit);
            labelTerminal.add(buttonMenu);
            labelTerminal.add(buttonTutorial);
            labelGod.add(buttonEndTurn);
            labelGod.add(buttonPower);
            labelGod.add(buttonMove);
            labelGod.add(buttonBuild);
            frame.validate();
        }catch (IOException e){
            e.printStackTrace();
        }


        label.add(labelGod,BorderLayout.EAST);
        label.add(labelTerminal,BorderLayout.WEST);
        label.add(panel1,BorderLayout.CENTER);
        frame.add(label);



        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private static JButton getIconGodProfile(String fileImg){
        JButton buttonGod = new JButton();
        Image God = new ImageIcon((fileImg)).getImage().getScaledInstance(90,170,Image.SCALE_DEFAULT);
        buttonGod.setIcon(new ImageIcon(God));
        buttonGod.setBounds(31,35,90,160);
        buttonGod.setOpaque(false);
        return buttonGod;
    }

    private static BufferedImage getScaledImage(BufferedImage originalImage,int max_width,int max_height) {

        int MAX_IMG_WIDTH = max_width;
        int MAX_IMG_HEIGHT = max_height;

        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        Dimension originalDimension = new Dimension(originalImage.getWidth(),
                originalImage.getHeight());
        Dimension boundaryDimension = new Dimension(MAX_IMG_WIDTH,
                MAX_IMG_HEIGHT);
        Dimension scalingDimension = getScaledDimension(originalDimension,
                boundaryDimension);

        width = (int) scalingDimension.getWidth();
        height = (int) scalingDimension.getHeight();

        BufferedImage resizedImage = new BufferedImage(width, height,
                originalImage.getType());
        Graphics2D g = resizedImage.createGraphics();

        g.drawImage(originalImage, 0, 0, width, height, null);

        return resizedImage;

    }

    public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {

        int original_width = imgSize.width;
        int original_height = imgSize.height;
        int bound_width = boundary.width;
        int bound_height = boundary.height;
        int new_width = original_width;
        int new_height = original_height;


        if (original_width > bound_width) {

            new_width = bound_width;
            new_height = (new_width * original_height) / original_width;
        }


        if (new_height > bound_height) {

            new_height = bound_height;
            new_width = (new_height * original_width) / original_height;
        }

        return new Dimension(new_width, new_height);
    }
    @Override
    public void updatePutWorker(PutWorkerMessage message){
        Position pos1 = message.getPositionOne();
        Position pos2 = message.getPositionTwo();

        JLabel labelWorker = new JLabel(new ImageIcon(this.getClass().getResource("/icon_player.png")));
        labelWorker.setPreferredSize(new Dimension(5,5));
        JLabel labelWorker2 = new JLabel(new ImageIcon(this.getClass().getResource("/icon_player.png")));
        labelWorker2.setPreferredSize(new Dimension(5,5));

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                buttonMatrix[pos1.row][pos1.col].add(labelWorker, BorderLayout.CENTER);
                buttonMatrix[pos2.row][pos2.col].add(labelWorker2, BorderLayout.CENTER);
            }
        });
        //TODO: aggiungere differenziazione per playerindex!
    }

    @Override
    public void updateMoveWorker(MoveMessage message){
        Position oldPos = message.getWorkerPosition();
        Position newPos = message.getMovePosition();

        JLabel labelWorker = new JLabel(new ImageIcon(this.getClass().getResource("/icon_player.png")));
        labelWorker.setPreferredSize(new Dimension(5,5));

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                int labelNum = buttonMatrix[oldPos.row][oldPos.col].getComponentCount();
                JLabel l = (JLabel) buttonMatrix[oldPos.row][oldPos.col].getComponent(labelNum-1);

                buttonMatrix[oldPos.row][oldPos.col].remove(labelNum - 1);
                buttonMatrix[oldPos.row][oldPos.col].revalidate();
                buttonMatrix[oldPos.row][oldPos.col].repaint();
                l.add(labelWorker, BorderLayout.CENTER);
                buttonMatrix[newPos.row][newPos.col].add(l);
            }
        });
    }

    @Override
    public void updateBuild(BuildViewMessage message){
        Position buildPos = message.getBuildPosition();
        int level = message.getLevel();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //TODO: nei vari casi inserire le immagini dei diversi livelli! cambiare l'immagine nel caso 1

                JLabel buildLabel;

                switch (level){
                    case 1:
                        buildLabel = new JLabel(new ImageIcon(this.getClass().getResource("/bg_panelEdgeRight.png")));
                        buttonMatrix[buildPos.row][buildPos.col].add(buildLabel, BorderLayout.CENTER);
                        break;
                    case 2:
                        buildLabel = new JLabel();
                        //buttonMatrix[buildPos.row][buildPos.col].add(buildLabel);
                        break;
                    case 3:
                        buildLabel = new JLabel();
                        //buttonMatrix[buildPos.row][buildPos.col].add(buildLabel);
                        break;
                    case 4:
                        buildLabel = new JLabel();
                        //buttonMatrix[buildPos.row][buildPos.col].add(buildLabel);
                        break;
                    default:
                        //error
                }
            }
        });
    }
}
