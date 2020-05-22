package it.polimi.ingsw.view;

import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GUI extends ClientView {

    private JLabel label;
    private JLabel labelTerminal;
    private JPanel panel1;
    private JLabel labelGod;
    private static final Dimension FRAME_DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int FRAME_WIDTH = (FRAME_DIMENSION.width/2)*3/2;
    private static final int FRAME_HEIGHT = (FRAME_DIMENSION.height/2)*3/2;
    private static int labelGodWidth;
    private static int labelGodHeight;
    private static final int internalFrameWidth = getProportionWidth(1400,1400,FRAME_WIDTH);
    private static final int internalFrameEight = getProportionHeight(800,820,FRAME_HEIGHT);
    private static final int labelEmptyWidth = getProportionWidth(18,1400,internalFrameWidth);
    private static final int labelEmptyHeight = getProportionHeight(19,800,internalFrameEight);
    private static final int ROW_NUM = 5;
    private ButtonMatrix[][] buttonMatrix = new ButtonMatrix[ROW_NUM][ROW_NUM];
    private JFrame frame;

    private ImageContainer imageContainer;



    public GUI(){
    }

    public void initGUI(){
        frame = new JFrame("Santorini");
        frame.setLocation(FRAME_DIMENSION.width/8, FRAME_DIMENSION.height/8);

        imageContainer = new ImageContainer();

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints lim = new GridBagConstraints();
        lim.ipadx = getProportionWidth(102,1400,internalFrameWidth);
        lim.ipady = getProportionHeight(102,800,internalFrameEight);

        panel1 = new JPanel();
        panel1.setLayout(layout);
        for(int i = 0; i < 5; i++){
            lim.gridy = i;
            for(int j = 0; j < 5; j++){
                lim.gridx = j;
                buttonMatrix[i][j] = new ButtonMatrix(i,j);
                //buttonMatrix[i][j].setOpaque(false);
                //buttonMatrix[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                buttonMatrix[i][j].setPreferredSize(new Dimension(labelEmptyWidth,labelEmptyHeight));
                //buttonMatrix[i][j].setLayout(new BorderLayout());
                JLabel labelEmpty = new JLabel("");
                labelEmpty.setOpaque(false);
                buttonMatrix[i][j].add(labelEmpty,BorderLayout.CENTER);
                layout.setConstraints(buttonMatrix[i][j], lim);
                panel1.add(buttonMatrix[i][j]);

            }
        }

        panel1.setOpaque(false);

       /* final JComboBox<UIManager.LookAndFeelInfo> laf = new JComboBox<UIManager.LookAndFeelInfo>();
        UIManager.LookAndFeelInfo selected = null;
        for (UIManager.LookAndFeelInfo lafInfo : UIManager.getInstalledLookAndFeels()) {
            laf.addItem(lafInfo);
            if (lafInfo.getName().equals(UIManager.getLookAndFeel().getName())) {
                selected = lafInfo;
            }
        }
        laf.setSelectedItem(selected);
        laf.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value != null) {
                    UIManager.LookAndFeelInfo lafInfo = (UIManager.LookAndFeelInfo) value;
                    setText(lafInfo.getName());
                } else {
                    setText("");
                }
                return this;
            }
        });

        laf.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String className = laf.getModel().getElementAt(laf.getSelectedIndex()).getClassName();
                System.err.println("Changing to " + className);
                try {
                    UIManager.setLookAndFeel(className);
                    SwingUtilities.updateComponentTreeUI(frame.getRootPane());
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                } catch (InstantiationException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                } catch (UnsupportedLookAndFeelException e1) {
                    e1.printStackTrace();
                }
            }
        });*/

        frame.setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(FRAME_WIDTH,FRAME_HEIGHT));
        frame.setResizable(false);

        label = new JLabel("");
        Image image = new ImageIcon(this.getClass().getResource("/SantoriniBoard.png")).getImage().getScaledInstance(getProportionWidth(1400,1400,FRAME_WIDTH),getProportionHeight(800,820,FRAME_HEIGHT),Image.SCALE_DEFAULT);
        label.setIcon(new ImageIcon(image));
        label.setLayout(new BorderLayout());


        labelTerminal = new JLabel("");
        final int labelTerminalWidth = getProportionWidth(350,1400,internalFrameWidth);
        final int labelTerminalEight = getProportionHeight(800,800,internalFrameEight);
        Image imageTerminal = new ImageIcon(this.getClass().getResource("/bg_panelEdgeLeft.png")).getImage().getScaledInstance(labelTerminalWidth,labelTerminalEight,Image.SCALE_DEFAULT);
        labelTerminal.setIcon(new ImageIcon(imageTerminal));


        labelGod = new JLabel("");
        labelGodWidth = getProportionWidth(350,1400,internalFrameWidth);
        labelGodHeight = getProportionHeight(800,800,internalFrameEight);
        Image imageGod = new ImageIcon(this.getClass().getResource("/bg_panelEdgeRight.png")).getImage().getScaledInstance(labelGodWidth,labelGodHeight,Image.SCALE_DEFAULT);
        labelGod.setIcon(new ImageIcon(imageGod));




        //JLabel labelBorderGod;
        //labelBorderGod = getIconGodProfile( imageContainer.getGodimage("Apollo"));
        //labelGod.add(labelBorderGod);



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
        terminal.setBounds(getProportionWidth(60,350,labelTerminalWidth),getProportionHeight(170,800,labelTerminalEight),getProportionWidth(250,350,labelTerminalWidth),getProportionHeight(280,800,labelTerminalEight));

        labelTerminal.add(terminal);


        ButtonCircle buttonMove;
        ButtonCircle buttonBuild;
        ButtonCircle buttonPower;
        ButtonCircle buttonEndTurn;
        ButtonCircle buttonTutorial;
        ButtonCircle buttonMenu;
        JButton buttonExit = new JButton();

        //Image of button
        Image imageMove = imageContainer.getButtonImage("buttonMove").getScaledInstance(getProportionWidth(90,350,labelGodWidth),getProportionHeight(90,800,labelGodHeight),Image.SCALE_DEFAULT);
        Image imageBuild = imageContainer.getButtonImage("buttonBuild").getScaledInstance(getProportionWidth(90,350,labelGodWidth),getProportionHeight(90,800,labelGodHeight),Image.SCALE_DEFAULT);
        Image imagePower = imageContainer.getButtonImage("buttonPower").getScaledInstance(getProportionWidth(90,350,labelGodWidth),getProportionHeight(90,800,labelGodHeight),Image.SCALE_DEFAULT);
        Image imageEndTurn = imageContainer.getButtonImage("buttonEndTurn").getScaledInstance(getProportionWidth(100,350,labelGodWidth),getProportionHeight(100,800,labelGodHeight),Image.SCALE_DEFAULT);
        Image imageTutorial = imageContainer.getButtonImage("buttonTutorial").getScaledInstance(getProportionWidth(90,350,labelTerminalWidth),getProportionHeight(90,800,labelTerminalEight),Image.SCALE_DEFAULT);
        Image imageMenu = imageContainer.getButtonImage("buttonMenu").getScaledInstance(getProportionWidth(95,350,labelTerminalWidth),getProportionHeight(95,800,labelTerminalEight),Image.SCALE_DEFAULT);
        Image imageExit = imageContainer.getButtonImage("buttonExit").getScaledInstance(getProportionWidth(60,350,labelGodWidth),getProportionHeight(50,800,labelGodHeight),Image.SCALE_DEFAULT);

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
        buttonMove.setBounds(getProportionWidth(50,350,labelGodWidth),getProportionHeight(470,800,labelGodHeight),getProportionWidth(90,350,labelGodWidth),getProportionHeight(90,800,labelGodHeight));
        buttonBuild.setBounds(getProportionWidth(180,350,labelGodWidth),getProportionHeight(470,800,labelGodHeight),getProportionWidth(90,350,labelGodWidth),getProportionHeight(90,800,labelGodHeight));
        buttonPower.setBounds(getProportionWidth(50,350,labelGodWidth),getProportionHeight(365,800,labelGodHeight),getProportionWidth(95,350,labelGodWidth),getProportionHeight(95,800,labelGodHeight));
        buttonEndTurn.setBounds(getProportionWidth(180,350,labelGodWidth),getProportionHeight(365,800,labelGodHeight),getProportionWidth(90,350,labelGodWidth),getProportionHeight(90,800,labelGodHeight));
        buttonTutorial.setBounds(getProportionWidth(60,350,labelTerminalWidth),getProportionHeight(470,800,labelTerminalEight),getProportionWidth(92,350,labelTerminalWidth),getProportionHeight(92,800,labelTerminalEight));
        buttonMenu.setBounds(getProportionWidth(200,350,labelTerminalWidth),getProportionHeight(470,800,labelTerminalEight),getProportionWidth(85,350,labelTerminalWidth),getProportionHeight(85,800,labelTerminalEight));
        buttonExit.setBounds(getProportionWidth(285,5,labelGodWidth),getProportionHeight(90,800,labelGodHeight),getProportionWidth(50,350,labelGodWidth),getProportionHeight(55,800,labelGodHeight));


       /* buttonMove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateActionView();
            }
        });

        buttonBuild.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateActionView();
            }
        });*/

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
        frame.getContentPane().add(label);






        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private static int getProportionWidth(int dimensionWidth, int oldContainerDimensionWidth, int newContainerWidth){
        int result;
        result = dimensionWidth * (newContainerWidth);
        result = result/oldContainerDimensionWidth;
        return result;

    }

    private static int getProportionHeight(int dimensionHeight, int oldContainerDimensionHeight, int newContainerHeight){
        int res;
        res = dimensionHeight * (newContainerHeight);
        res = res/oldContainerDimensionHeight;
        return res;

    }

    private ButtonCircle getPlayerIcon(PlayerIndex playerIndex){
        ButtonCircle buttonPlayer = null;
        if(playerIndex.equals(PlayerIndex.PLAYER0)){
            Image imagePlayer = new ImageIcon(("src/main/resources/TalusToken.png")).getImage().getScaledInstance(getProportionWidth(50,18,labelEmptyWidth),getProportionHeight(50,19,labelEmptyHeight),Image.SCALE_DEFAULT);
            buttonPlayer = new ButtonCircle(new ImageIcon(imagePlayer),Color.RED);
            buttonPlayer.setBounds( getProportionWidth(37,18,labelEmptyWidth),getProportionHeight(34,19,labelEmptyHeight), getProportionWidth(53,18,labelEmptyWidth),getProportionHeight(53,19,labelEmptyHeight));
        }else if(playerIndex.equals(PlayerIndex.PLAYER1)){
            Image imagePlayer = new ImageIcon(("src/main/resources/TalusToken.png")).getImage().getScaledInstance(getProportionWidth(50,18,labelEmptyWidth),getProportionHeight(50,19,labelEmptyHeight),Image.SCALE_DEFAULT);
            buttonPlayer = new ButtonCircle(new ImageIcon(imagePlayer),Color.BLUE);
            buttonPlayer.setBounds( getProportionWidth(37,18,labelEmptyWidth),getProportionHeight(34,19,labelEmptyHeight), getProportionWidth(53,18,labelEmptyWidth),getProportionHeight(53,19,labelEmptyHeight));
        }else if(playerIndex.equals(PlayerIndex.PLAYER2)){
            Image imagePlayer = new ImageIcon(("src/main/resources/TalusToken.png")).getImage().getScaledInstance(getProportionWidth(50,18,labelEmptyWidth),getProportionHeight(50,19,labelEmptyHeight),Image.SCALE_DEFAULT);
            buttonPlayer = new ButtonCircle(new ImageIcon(imagePlayer),Color.CYAN);
            buttonPlayer.setBounds( getProportionWidth(37,18,labelEmptyWidth),getProportionHeight(34,19,labelEmptyHeight), getProportionWidth(53,18,labelEmptyWidth),getProportionHeight(53,19,labelEmptyHeight));
        }

        return buttonPlayer;

    }

    private static JLabel getIconGodProfile(Image godImage){

        JLabel labelBorderGod = new JLabel("");
        Image imageBorderGod = new ImageIcon(("src/main/resources/clp_frame_gold.png")).getImage().getScaledInstance(getProportionWidth(150,350,labelGodWidth),getProportionHeight(230,800,labelGodHeight),Image.SCALE_DEFAULT);
        labelBorderGod.setIcon(new ImageIcon(imageBorderGod));
        int labelBorderGodWidth = getProportionWidth(150,350,labelGodWidth);
        int labelBorderGodHeight = getProportionHeight(230,800,labelGodHeight);
        labelBorderGod.setBounds(getProportionWidth(90,350,labelGodWidth),getProportionHeight(140,800,labelGodHeight),labelBorderGodWidth,labelBorderGodHeight);
        JButton buttonGod = new JButton();
        Image God = godImage.getScaledInstance(getProportionWidth(90,150,labelBorderGodWidth),getProportionHeight(160,230,labelBorderGodHeight),Image.SCALE_DEFAULT);
        //Image God = new ImageIcon((fileImg)).getImage().getScaledInstance(getProportionWidth(90,150,labelBorderGodWidth),getProportionHeight(160,230,labelBorderGodHeight),Image.SCALE_DEFAULT);
        buttonGod.setIcon(new ImageIcon(God));
        buttonGod.setBounds(getProportionWidth(31,150,labelBorderGodWidth),getProportionHeight(31,230,labelBorderGodHeight),getProportionWidth(90,150,labelBorderGodWidth),getProportionHeight(170,230,labelBorderGodHeight));
        buttonGod.setOpaque(false);
        labelBorderGod.add(buttonGod);
        return labelBorderGod;
    }

   /* private static BufferedImage getScaledImage(BufferedImage originalImage,int max_width,int max_height) {

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

    }*/

   /* public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {

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
    }*/
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
                //Label on old cell
                int level = 0;
                JLabel labelInit;
                labelInit = (JLabel) buttonMatrix[oldPos.row][oldPos.col].getComponent(buttonMatrix[oldPos.row][oldPos.col].getComponentCount() - 1);
                if (labelInit.getComponent(labelInit.getComponentCount() - 1) instanceof ButtonCircle) {
                    labelInit.remove(labelInit.getComponentCount() - 1);
                } else {
                    //Tower level 1
                    level++;
                    labelInit = (JLabel) labelInit.getComponent(labelInit.getComponentCount() - 1);
                    if (labelInit.getComponent(labelInit.getComponentCount() - 1) instanceof ButtonCircle) {
                        labelInit.remove(labelInit.getComponentCount() - 1);
                    } else {
                        //Tower level 2
                        level++;
                        labelInit = (JLabel) labelInit.getComponent(labelInit.getComponentCount() - 1);
                        if (labelInit.getComponent(labelInit.getComponentCount() - 1) instanceof ButtonCircle) {
                            labelInit.remove(labelInit.getComponentCount() - 1);
                        } else {
                            //Tower level 3
                            level++;
                            labelInit = (JLabel) labelInit.getComponent(labelInit.getComponentCount() - 1);
                            if (labelInit.getComponent(labelInit.getComponentCount() - 1) instanceof ButtonCircle) {
                                labelInit.remove(labelInit.getComponentCount() - 1);
                            }
                        }
                    }
                }
                //label on newCell
                JLabel l;
                l = (JLabel) buttonMatrix[newPos.row][newPos.col].getComponent(buttonMatrix[newPos.row][newPos.col].getComponentCount() - 1);
                if(l.getComponent(l.getComponentCount() - 1) instanceof ButtonCircle){
                    switch (level){
                        case 0:
                            labelInit.add(l.getComponent(l.getComponentCount()-1));
                            break;
                        case 1:
                            l.getComponent(l.getComponentCount() - 1).setBounds(getProportionWidth(5,18,labelInit.getWidth()),getProportionHeight(5,19,labelInit.getHeight()),getProportionWidth(8,18,labelInit.getWidth()),getProportionHeight(8,19,labelInit.getHeight()));
                            labelInit.add(l.getComponent(l.getComponentCount() - 1));
                            break;
                    }
                }
                if (l.getComponentCount() != 0) {
                    //Tower level 1
                    JLabel labelTower1 = (JLabel) l.getComponent(l.getComponentCount() - 1);
                    if(labelTower1.getComponentCount() != 0) {
                        if (labelTower1.getComponent(labelTower1.getComponentCount() - 1) instanceof ButtonCircle) {
                            switch (level) {
                                case 0:
                                    labelInit.add(labelTower1.getComponent(labelTower1.getComponentCount() - 1));
                                    break;
                                case 1:
                                    labelTower1.getComponent(labelTower1.getComponentCount() - 1).setBounds(getProportionWidth(5, 18, labelInit.getWidth()), getProportionHeight(5, 19, labelInit.getHeight()), getProportionWidth(8, 18, labelInit.getWidth()), getProportionHeight(8, 19, labelInit.getHeight()));
                                    labelInit.add(l.getComponent(labelTower1.getComponentCount() - 1));
                                    break;
                                case 2:
                                    labelTower1.getComponent(labelTower1.getComponentCount() - 1).setBounds(getProportionWidth(5, 20, labelInit.getWidth()), getProportionHeight(4, 16, labelInit.getHeight()), getProportionWidth(10, 19, labelInit.getWidth()), getProportionHeight(10, 20, labelInit.getHeight()));
                                    labelInit.add(labelTower1.getComponent(labelTower1.getComponentCount() - 1));
                                    break;
                            }
                        }
                    }
                    if (labelTower1.getComponentCount() != 0) {
                        //Tower level 2
                        JLabel labelTower2 = (JLabel) labelTower1.getComponent(labelTower1.getComponentCount() - 1);
                        if(labelTower2.getComponentCount() != 0) {
                            if (labelTower2.getComponent(labelTower2.getComponentCount() - 1) instanceof ButtonCircle) {
                                switch (level) {
                                    case 1:
                                        labelTower2.getComponent(labelTower2.getComponentCount() - 1).setBounds(getProportionWidth(5, 18, labelInit.getWidth()), getProportionHeight(5, 19, labelInit.getHeight()), getProportionWidth(8, 18, labelInit.getWidth()), getProportionHeight(8, 19, labelInit.getHeight()));
                                        labelInit.add(labelTower2.getComponent(labelTower2.getComponentCount() - 1));
                                        break;
                                    case 2:
                                        labelTower2.getComponent(labelTower2.getComponentCount() - 1).setBounds(getProportionWidth(5, 20, labelInit.getWidth()), getProportionHeight(4, 16, labelInit.getHeight()), getProportionWidth(10, 19, labelInit.getWidth()), getProportionHeight(10, 20, labelInit.getHeight()));
                                        labelInit.add(labelTower2.getComponent(labelTower2.getComponentCount() - 1));
                                        break;
                                    case 3:
                                        labelTower2.getComponent(labelTower2.getComponentCount() - 1).setBounds(getProportionWidth(4, 20, labelInit.getWidth()), getProportionHeight(4, 19, labelInit.getHeight()), getProportionWidth(12, 19, labelInit.getWidth()), getProportionHeight(11, 20, labelInit.getHeight()));
                                        labelInit.add(labelTower2.getComponent(labelTower2.getComponentCount() - 1));
                                        break;
                                }
                            }
                        }
                        if (labelTower2.getComponentCount() != 0) {
                            //Tower level 3
                            JLabel labelTower3 = (JLabel) labelTower2.getComponent(labelTower2.getComponentCount() - 1);
                            if(labelTower3.getComponentCount() != 0){
                                if(labelTower3.getComponent(labelTower3.getComponentCount() - 1) instanceof ButtonCircle){
                                    switch (level){
                                        case 2:
                                            labelTower3.getComponent(labelTower3.getComponentCount() - 1).setBounds(getProportionWidth(5, 20, labelInit.getWidth()), getProportionHeight(4, 16, labelInit.getHeight()), getProportionWidth(10, 19, labelInit.getWidth()), getProportionHeight(10, 20, labelInit.getHeight()));
                                            labelInit.add(labelTower3.getComponent(labelTower3.getComponentCount() - 1));
                                            break;
                                        case 3:
                                            labelTower3.getComponent(labelTower3.getComponentCount() - 1).setBounds(getProportionWidth(4, 20, labelInit.getWidth()), getProportionHeight(4, 19, labelInit.getHeight()), getProportionWidth(12, 19, labelInit.getWidth()), getProportionHeight(11, 20, labelInit.getHeight()));
                                            labelInit.add(labelTower3.getComponent(labelTower3.getComponentCount() - 1));
                                            break;
                                    }
                                }
                            }
                            labelWorker.setBounds(getProportionWidth(4,20,labelTower3.getWidth()),getProportionHeight(4,19,labelTower3.getHeight()),getProportionWidth(12,19,labelTower3.getWidth()),getProportionHeight(11,20,labelTower3.getHeight()));
                            labelTower3.add(labelWorker);
                        }else{
                            labelWorker.setBounds(getProportionWidth(5,20,labelTower2.getWidth()),getProportionHeight(4,16,labelTower2.getHeight()),getProportionWidth(10,19,labelTower2.getWidth()),getProportionHeight(10,20,labelTower2.getHeight()));
                            labelTower2.add(labelWorker);
                        }
                    }else{
                        labelWorker.setBounds(getProportionWidth(5,18,labelTower1.getWidth()),getProportionHeight(5,19,labelTower1.getHeight()),getProportionWidth(8,18,labelTower1.getWidth()),getProportionHeight(8,19,labelTower1.getHeight()));
                        labelTower1.add(labelWorker);
                    }
                }else {
                    l.add(labelWorker);
                    buttonMatrix[newPos.row][newPos.col].add(l);
                }
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
                        Image imageTower = new ImageIcon("src/main/resources/frame_blue.png").getImage().getScaledInstance(getProportionWidth(129,18,labelEmptyWidth),getProportionHeight(126,19,labelEmptyHeight),Image.SCALE_DEFAULT);
                        buildLabel = new JLabel("");
                        buildLabel.setIcon(new ImageIcon(imageTower));
                        JLabel labelButton = (JLabel) buttonMatrix[buildPos.row][buildPos.col].getComponent(buttonMatrix[buildPos.row][buildPos.col].getComponentCount() - 1);
                        buildLabel.setBounds(getProportionWidth(-3,18,labelEmptyWidth),getProportionHeight(1,19,labelEmptyHeight),getProportionWidth(129,18,labelEmptyWidth),getProportionHeight(126,19,labelEmptyHeight));
                        buildLabel.setOpaque(false);
                        labelButton.add(buildLabel);
                        //buttonMatrix[buildPos.row][buildPos.col].add(labelButton);
                        break;
                    case 2:
                        JLabel buildCorrect0 = (JLabel) buttonMatrix[buildPos.row][buildPos.col].getComponent(buttonMatrix[buildPos.row][buildPos.col].getComponentCount() - 1);
                        JLabel buildCorrect = (JLabel) buildCorrect0.getComponent(buildCorrect0.getComponentCount() - 1);
                        Image imageTowerLevel2 = new ImageIcon("src/main/resources/frame_coral.png").getImage().getScaledInstance(getProportionWidth(13,16,buildCorrect.getWidth()),getProportionHeight(17,19,buildCorrect.getHeight()),Image.SCALE_DEFAULT);
                        JLabel buildLabel1 = new JLabel("");
                        buildLabel1.setIcon(new ImageIcon(imageTowerLevel2));
                        buildLabel1.setOpaque(false);
                        buildLabel1.setBounds(getProportionWidth(2,18,buildCorrect.getWidth()),getProportionHeight(1,15,buildCorrect.getHeight()),getProportionWidth(13,16,buildCorrect.getWidth()),getProportionHeight(17,19,buildCorrect.getHeight()));
                        buildCorrect.add(buildLabel1);
                        //buildCorrect0.add(buildCorrect);
                        //buttonMatrix[buildPos.row][buildPos.col].add(buildCorrect0);
                        break;
                    case 3:
                        buildLabel = new JLabel();
                        buildLabel.setLayout(new BorderLayout());
                        JLabel buildLabel0 = (JLabel)buttonMatrix[buildPos.row][buildPos.col].getComponent(buttonMatrix[buildPos.row][buildPos.col].getComponentCount() - 1);
                        JLabel buildTower1 = (JLabel) buildLabel0.getComponent(buildLabel0.getComponentCount() - 1);
                        JLabel buildTower2 = (JLabel) buildTower1.getComponent(buildTower1.getComponentCount() - 1);
                        JLabel buildLabel3 = new JLabel("");
                        Image imageTowerLevel3 = new ImageIcon("src/main/resources/frame_yellow.png").getImage().getScaledInstance(getProportionWidth(14,17,buildTower2.getWidth()),getProportionHeight(14,16,buildTower2.getHeight()),Image.SCALE_DEFAULT);
                        buildLabel3.setIcon(new ImageIcon(imageTowerLevel3));
                        buildLabel3.setOpaque(false);
                        buildLabel3.setBounds(getProportionWidth(2,18,buildTower2.getWidth()),getProportionHeight(1,16,buildTower2.getHeight()),getProportionWidth(14,17,buildTower2.getWidth()),getProportionHeight(14,16,buildTower2.getHeight()));
                        buildTower2.add(buildLabel3);
                        break;
                    case 4:

                        JLabel build1 = (JLabel) buttonMatrix[buildPos.row][buildPos.col].getComponent(buttonMatrix[buildPos.row][buildPos.col].getComponentCount() - 1);
                        if(build1.getComponentCount() != 0){ //Case atlas power
                            //Tower 1
                            build1 = (JLabel) build1.getComponent(build1.getComponentCount() - 1);
                            if(build1.getComponentCount() != 0){
                                //Tower 2
                                build1 = (JLabel) build1.getComponent(build1.getComponentCount() - 1);
                                if(build1.getComponentCount() != 0){
                                    //Tower 3
                                    build1 = (JLabel) build1.getComponent(build1.getComponentCount() - 1);
                                }
                            }
                        }
                        Image imageDome = new ImageIcon("src/main/resources/cm_bg.png").getImage().getScaledInstance(getProportionWidth(16,18,build1.getWidth()),getProportionHeight(15,16,build1.getHeight()),Image.SCALE_DEFAULT);
                        JButton dome = new JButton();
                        dome.setIcon(new ImageIcon(imageDome));
                        dome.setBounds(getProportionWidth(3,18,build1.getWidth()),getProportionHeight(2,16,build1.getHeight()),getProportionWidth(12,18,build1.getWidth()),getProportionHeight(12,16,build1.getHeight()));
                        build1.add(dome);
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

                    JLabel labelButton = (JLabel) buttonMatrix[p.row][p.col].getComponent(buttonMatrix[p.row][p.col].getComponentCount() - 1);
                    if(labelButton.getComponentCount() != 0){
                        //Tower 1
                        JLabel labelTow1 = (JLabel) labelButton.getComponent(labelButton.getComponentCount() - 1);
                        if(labelTow1.getComponentCount() != 0){
                            //Tower 2
                            JLabel labelTow2 = (JLabel) labelTow1.getComponent(labelTow1.getComponentCount() - 1);
                            if(labelTow2.getComponentCount() != 0){
                                //Tower 3
                                JLabel labelTow3 = (JLabel) labelTow2.getComponent(labelTow2.getComponentCount() - 1);
                               if(labelTow3.getComponentCount() != 0){ //Case power apollo

                               }else{
                                   Image imageTow3 = new ImageIcon(this.getClass().getResource("/playermoveindicator_blue.png")).getImage().getScaledInstance(getProportionWidth(15, 19, labelTow3.getWidth()), getProportionHeight(15, 19, labelTow3.getHeight()), Image.SCALE_DEFAULT);
                                   JLabel labelIndicatorTow3 = new JLabel("");
                                   labelIndicatorTow3.setIcon(new ImageIcon(imageTow3));
                                   labelIndicatorTow3.setBounds(getProportionWidth(2, 18, labelTow3.getWidth()), getProportionHeight(2, 19, labelTow3.getHeight()), getProportionWidth(15, 19, labelTow3.getWidth()), getProportionHeight(15, 19, labelTow3.getHeight()));
                                   labelTow3.add(labelIndicatorTow3);
                               }
                            }else{
                                Image imageTow2 = new ImageIcon(this.getClass().getResource("/playermoveindicator_blue.png")).getImage().getScaledInstance(getProportionWidth(15, 19, labelTow2.getWidth()), getProportionHeight(15, 19, labelTow2.getHeight()), Image.SCALE_DEFAULT);
                                JLabel labelIndicatorTow2 = new JLabel("");
                                labelIndicatorTow2.setIcon(new ImageIcon(imageTow2));
                                labelIndicatorTow2.setBounds(getProportionWidth(2, 18, labelTow2.getWidth()), getProportionHeight(2, 19, labelTow2.getHeight()), getProportionWidth(15, 19, labelTow2.getWidth()), getProportionHeight(15, 19, labelTow2.getHeight()));
                                labelTow2.add(labelIndicatorTow2);
                            }
                        }else{
                            Image imageTow1 = new ImageIcon(this.getClass().getResource("/playermoveindicator_blue.png")).getImage().getScaledInstance(getProportionWidth(15, 19, labelTow1.getWidth()), getProportionHeight(15, 19, labelTow1.getHeight()), Image.SCALE_DEFAULT);
                            JLabel labelIndicatorTow1 = new JLabel("");
                            labelIndicatorTow1.setIcon(new ImageIcon(imageTow1));
                            labelIndicatorTow1.setBounds(getProportionWidth(2, 18, labelTow1.getWidth()), getProportionHeight(2, 19, labelTow1.getHeight()), getProportionWidth(15, 19, labelTow1.getWidth()), getProportionHeight(15, 19, labelTow1.getHeight()));
                            labelTow1.add(labelIndicatorTow1);
                        }
                    }else {
                        JLabel labelIndicator = new JLabel("");
                        Image imageIndicator = new ImageIcon(this.getClass().getResource("/playermoveindicator_blue.png")).getImage().getScaledInstance(getProportionWidth(125, 18, labelEmptyWidth), getProportionHeight(127, 19, labelEmptyHeight), Image.SCALE_DEFAULT);
                        labelIndicator.setIcon(new ImageIcon(imageIndicator));
                        labelIndicator.setBounds(getProportionWidth(-2, 18, labelEmptyWidth), getProportionHeight(-4, 19, labelEmptyHeight), getProportionWidth(125, 18, labelEmptyWidth), getProportionHeight(130, 19, labelEmptyHeight));
                        labelButton.add(labelIndicator);
                    }
                }
            }
        });
    }

    @Override
    public void updateSelectedCardView(PlayerSelectGodMessage message) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JLabel godChosen = getIconGodProfile(imageContainer.getGodimage(message.getGodName()));
                labelGod.add(godChosen);
            }
        });
    }


}