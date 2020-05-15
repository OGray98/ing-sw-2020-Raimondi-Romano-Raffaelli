package it.polimi.ingsw.view;

import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.ActionMessage;
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
import java.util.ArrayList;

public class GUI extends ClientView {

    private JLabel label;
    private ButtonCircle labelWorker;
    private ButtonCircle labelWorker2;
    private JLabel labelTerminal;
    private JLabel labelPlayer;
    private JPanel panel1;
    private JLabel labelGod;
    private static final Dimension FRAME_DIM = new Dimension(1400,820);
    private static final int ROW_NUM = 5;
    private JButton[][] buttonMatrix = new JButton[ROW_NUM][ROW_NUM];
    private JFrame frame;



    public GUI(){
    }

    public void initGUI(){
        frame = new JFrame("Santorini");

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
                JLabel labelEmpty = new JLabel("");
                labelEmpty.setOpaque(false);
                buttonMatrix[i][j].add(labelEmpty,BorderLayout.CENTER);
                layout.setConstraints(buttonMatrix[i][j], lim);
                panel1.add(buttonMatrix[i][j]);

            }
        }
        panel1.setOpaque(false);



        labelBorderGod = getIconGodProfile( "src/main/resources/_0000s_0004_god_and_hero_cards_0052_Prometheus.png");
        labelGod.add(labelBorderGod);



        JTextArea textArea = new JTextArea(50, 60);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        for (int i = 0; i < 75; i++) {
            textArea.append("Hello world!");
        }
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.GREEN);
        JScrollPane terminal = new JScrollPane(textArea);
        terminal.setBackground(Color.BLACK);
        terminal.setBounds(60,170,250,280);

        labelTerminal.add(terminal);


        ButtonCircle buttonMove;
        ButtonCircle buttonBuild;
        ButtonCircle buttonPower;
        ButtonCircle buttonEndTurn;
        ButtonCircle buttonTutorial;
        ButtonCircle buttonMenu;
        JButton buttonExit = new JButton();

        //Image of button
        Image imageMove = new ImageIcon(this.getClass().getResource("/castorpollux_bothMove.png")).getImage().getScaledInstance(90,90,Image.SCALE_DEFAULT);
        Image imageBuild = new ImageIcon(this.getClass().getResource("/castorpollux_bothBuild.png")).getImage().getScaledInstance(90,90,Image.SCALE_DEFAULT);
        Image imagePower = new ImageIcon(this.getClass().getResource("/heropower_active_small.png")).getImage().getScaledInstance(90,90,Image.SCALE_DEFAULT);
        Image imageEndTurn = new ImageIcon(this.getClass().getResource("/Chronus_ClockFace.png")).getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT);
        Image imageTutorial = new ImageIcon(this.getClass().getResource("/TutorialOn.png")).getImage().getScaledInstance(90,90,Image.SCALE_DEFAULT);
        Image imageMenu = new ImageIcon(this.getClass().getResource("/menu_button.png")).getImage().getScaledInstance(95,95,Image.SCALE_DEFAULT);
        Image imageExit = new ImageIcon(this.getClass().getResource("/Exit.png")).getImage().getScaledInstance(60,50,Image.SCALE_DEFAULT);
        //Creating button
        buttonMove = new ButtonCircle(new ImageIcon(imageMove),Color.WHITE);
        buttonBuild = new ButtonCircle(new ImageIcon(imageBuild),Color.WHITE);
        buttonPower = new ButtonCircle(new ImageIcon(imagePower),Color.WHITE);
        buttonEndTurn = new ButtonCircle(new ImageIcon(imageEndTurn),Color.WHITE);
        buttonTutorial = new ButtonCircle(new ImageIcon(imageTutorial),Color.WHITE);
        buttonMenu = new ButtonCircle(new ImageIcon(imageMenu),Color.WHITE);
        buttonExit.setIcon(new ImageIcon(imageExit));
        buttonExit.setOpaque(false);


        //Position of button
        buttonMove.setBounds(50,470,90,90);
        buttonBuild.setBounds(180,470,90,90);
        buttonPower.setBounds(50,365,95,95);
        buttonEndTurn.setBounds(180,365,90,90);
        buttonTutorial.setBounds(60,470,92,92);
        buttonMenu.setBounds(200,470,85,85);
        buttonExit.setBounds(285,5,55,50);



        //Adding button to lateral panel
        labelGod.add(buttonMove);
        labelGod.add(buttonBuild);
        labelGod.add(buttonPower);
        labelGod.add(buttonEndTurn);
        labelTerminal.add(buttonTutorial);
        labelTerminal.add(buttonMenu);
        labelGod.add(buttonExit);


        label.add(labelGod,BorderLayout.EAST);
        label.add(labelTerminal,BorderLayout.WEST);
        label.add(panel1,BorderLayout.CENTER);
        frame.add(label);



        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private static ButtonCircle getPlayerIcon(PlayerIndex playerIndex){
        ButtonCircle buttonPlayer = null;
        if(playerIndex.equals(PlayerIndex.PLAYER0)){
            Image imagePlayer = new ImageIcon(("src/main/resources/TalusToken.png")).getImage().getScaledInstance(80,80,Image.SCALE_DEFAULT);
            buttonPlayer = new ButtonCircle(new ImageIcon(imagePlayer),Color.RED);
            buttonPlayer.setBounds(17, 15, 83, 83);
        }else if(playerIndex.equals(PlayerIndex.PLAYER1)){
            Image imagePlayer = new ImageIcon(("src/main/resources/TalusToken.png")).getImage().getScaledInstance(80,80,Image.SCALE_DEFAULT);
            buttonPlayer = new ButtonCircle(new ImageIcon(imagePlayer),Color.BLUE);
            buttonPlayer.setBounds(17, 15, 83, 83);
        }else if(playerIndex.equals(PlayerIndex.PLAYER2)){
            Image imagePlayer = new ImageIcon(("src/main/resources/TalusToken.png")).getImage().getScaledInstance(80,80,Image.SCALE_DEFAULT);
            buttonPlayer = new ButtonCircle(new ImageIcon(imagePlayer),Color.MAGENTA);
            buttonPlayer.setBounds(17, 15, 83, 83);
        }

        return buttonPlayer;

    }

    private static JLabel getIconGodProfile(String fileImg){
        JLabel labelBorderGod = new JLabel("");
        Image imageBorderGod = new ImageIcon(("src/main/resources/clp_frame_gold.png")).getImage().getScaledInstance(150,230,Image.SCALE_DEFAULT);
        labelBorderGod.setIcon(new ImageIcon(imageBorderGod));
        labelBorderGod.setBounds(90,140,150,230);
        JButton buttonGod = new JButton();
        Image God = new ImageIcon((fileImg)).getImage().getScaledInstance(90,170,Image.SCALE_DEFAULT);
        buttonGod.setIcon(new ImageIcon(God));
        buttonGod.setBounds(31,35,90,160);
        buttonGod.setOpaque(false);
        labelBorderGod.add(buttonGod);
        return labelBorderGod;
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

        ButtonCircle labelWorker = getPlayerIcon(message.getClient());
        ButtonCircle labelWorker2 = getPlayerIcon(message.getClient());

        JLabel labelButton = (JLabel) buttonMatrix[pos1.row][pos1.col].getComponent(buttonMatrix[pos1.row][pos1.col].getComponentCount() - 1);
        JLabel labelButton1 = (JLabel) buttonMatrix[pos2.row][pos2.col].getComponent(buttonMatrix[pos2.row][pos2.col].getComponentCount() - 1);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                labelButton.add(labelWorker);
                labelButton1.add(labelWorker2);
            }
        });
        //TODO: aggiungere differenziazione per playerindex!
    }

    @Override
    public void updateMoveWorker(MoveMessage message){
        Position oldPos = message.getWorkerPosition();
        Position newPos = message.getMovePosition();

        ButtonCircle labelWorker = getPlayerIcon(message.getClient());
        //labelWorker.setLayout(new BorderLayout());


        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //label on newCell
                JLabel l;
                l = (JLabel) buttonMatrix[newPos.row][newPos.col].getComponent(buttonMatrix[newPos.row][newPos.col].getComponentCount() - 1);
                int labelNumNewPos = l.getComponentCount();
                if (labelNumNewPos != 0) {
                    //Tower level 1
                    l = (JLabel) l.getComponent(l.getComponentCount() - 1);
                    if (l.getComponentCount() != 0) {
                        //Tower level 2
                        l = (JLabel) l.getComponent(l.getComponentCount() - 1);
                        if (l.getComponentCount() != 0) {
                            //Tower level 3
                            l = (JLabel) l.getComponent(l.getComponentCount() - 1);
                        }
                    }
                }
                //Label on old cell
                JLabel labelInit;
                labelInit = (JLabel) buttonMatrix[oldPos.row][oldPos.col].getComponent(buttonMatrix[oldPos.row][oldPos.col].getComponentCount() - 1);
                if (labelInit.getComponent(labelInit.getComponentCount() - 1) instanceof ButtonCircle) {
                    labelInit.remove(labelInit.getComponentCount() - 1);
                } else {
                    //Tower level 1
                    JLabel labelIcon = (JLabel) labelInit.getComponent(labelInit.getComponentCount() - 1);
                    if (labelIcon.getComponent(labelIcon.getComponentCount() - 1) instanceof ButtonCircle) {
                        labelIcon.remove(labelIcon.getComponentCount() - 1);
                    } else {
                        //Tower level 2
                        JLabel labelIcon2;
                        labelIcon2 = (JLabel) labelIcon.getComponent(labelIcon.getComponentCount() - 1);
                        if (labelIcon2.getComponent(labelIcon2.getComponentCount() - 1) instanceof ButtonCircle) {
                            labelIcon2.remove(labelIcon2.getComponentCount() - 1);
                        } else {
                            //Tower level 3
                            JLabel labelIcon3;
                            labelIcon3 = (JLabel) labelIcon2.getComponent(labelIcon2.getComponentCount() - 1);
                            if (labelIcon3.getComponent(labelIcon3.getComponentCount() - 1) instanceof ButtonCircle) {
                                labelIcon3.remove(labelIcon3.getComponentCount() - 1);
                            }
                        }
                    }
                }
                l.add(labelWorker);
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

                switch (level){  //TODO: è giusto il level?
                    case 1:
                        buildLabel = new JLabel(new ImageIcon(this.getClass().getResource("/bg_panelEdgeRight.png")));
                        JLabel labelButton = (JLabel) buttonMatrix[buildPos.row][buildPos.col].getComponent(buttonMatrix[buildPos.row][buildPos.col].getComponentCount() - 1);
                        buildLabel.setBounds(0,0,120,120);
                        labelButton.add(buildLabel);
                        break;
                    case 2:
                        buildLabel = new JLabel();//Missed the icon
                        buildLabel.setLayout(new BorderLayout());
                        JLabel buildCorrect = (JLabel) buttonMatrix[buildPos.row][buildPos.col].getComponent(buttonMatrix[buildPos.row][buildPos.col].getComponentCount() - 1);
                        buildCorrect.add(buildLabel,BorderLayout.CENTER);
                        break;
                    case 3:
                        buildLabel = new JLabel();//Missed icon
                        buildLabel.setLayout(new BorderLayout());
                        JLabel buildCorrect1 = (JLabel)buttonMatrix[buildPos.row][buildPos.col].getComponent(buttonMatrix[buildPos.row][buildPos.col].getComponentCount() - 1);
                        JLabel buildFinal = (JLabel) buildCorrect1.getComponent(buildCorrect1.getComponentCount() - 1);
                        buildFinal.add(buildLabel,BorderLayout.CENTER);
                        break;
                    case 4:
                        buildLabel = new JLabel();//Missed icon
                        buildLabel.setLayout(new BorderLayout());
                        JLabel build1 = (JLabel) buttonMatrix[buildPos.row][buildPos.col].getComponent(buttonMatrix[buildPos.row][buildPos.col].getComponentCount() - 1);
                        JLabel build2 = (JLabel) build1.getComponent(build1.getComponentCount() - 1);
                        JLabel build3 = (JLabel) build2.getComponent(build2.getComponentCount() - 1);
                        build3.add(buildLabel,BorderLayout.CENTER);
                        break;
                    default:
                        //error
                }
            }
        });
    }

    @Override
    public void updateActionView(ActionMessage message){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                for(Position p : message.getPossiblePosition()){

                    JLabel labelIndicator = new JLabel("");
                    Image imageIndicator = new ImageIcon(this.getClass().getResource("/playermoveindicator_blue.png")).getImage().getScaledInstance(117,117,Image.SCALE_DEFAULT);
                    labelIndicator.setIcon(new ImageIcon(imageIndicator));
                    labelIndicator.setBounds(0,-8,120,130);
                    JLabel labelButton = (JLabel) buttonMatrix[p.row][p.col].getComponent(buttonMatrix[p.row][p.col].getComponentCount() - 1);
                    labelButton.add(labelIndicator);
                }
            }
        });
    }






}