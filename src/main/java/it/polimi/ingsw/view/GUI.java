package it.polimi.ingsw.view;

import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.Client.ViewModelInterface;
import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.utils.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GUI extends ClientView {

    private JPanel panel1;
    private PrincipalLabel labelGod;
    private static final Dimension FRAME_DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int FRAME_WIDTH = (FRAME_DIMENSION.width/2) * 3/2;
    private static final int FRAME_HEIGHT = (FRAME_DIMENSION.height / 2) * 3/2;
    private static int labelGodWidth;
    private static int labelGodHeight;
    private static final int internalFrameWidth = getProportionWidth(1400, 1400, FRAME_WIDTH);
    private static final int internalFrameEight = getProportionHeight(800, 820, FRAME_HEIGHT);
    private static final int labelEmptyWidth = getProportionWidth(18, 1400, internalFrameWidth);
    private static final int labelEmptyHeight = getProportionHeight(19, 800, internalFrameEight);
    private static final int ROW_NUM = 5;
    private final ButtonCell[][] buttonCells = new ButtonCell[ROW_NUM][ROW_NUM];
    //private static JFrame frame;
    private static WelcomeFrame frame;

    private ButtonCircle buttonPower;
    private ButtonCircle buttonEndTurn;
    private ButtonCircle buttonTutorial;
    private ButtonCircle buttonMenu;

    private ImageContainer imageContainer;

    private static GodChoiceDialog godChoiceDialog;


    public GUI(ViewModelInterface clientModel) {
        super(clientModel);
    }

    private static JLabel getIconGodProfile(Image godImage, String godName) {

        JLabel labelBorderGod = new JLabel(godName);
        Image imageBorderGod = new ImageIcon(("src/main/resources/clp_frame_gold.png")).getImage().getScaledInstance(getProportionWidth(220, 350, labelGodWidth), getProportionHeight(320, 800, labelGodHeight), Image.SCALE_DEFAULT);
        labelBorderGod.setIcon(new ImageIcon(imageBorderGod));
        int labelBorderGodWidth = getProportionWidth(220, 350, labelGodWidth);
        int labelBorderGodHeight = getProportionHeight(320, 800, labelGodHeight);
        labelBorderGod.setBounds(getProportionWidth(55, 350, labelGodWidth), getProportionHeight(140, 800, labelGodHeight), labelBorderGodWidth, labelBorderGodHeight);
        JButton buttonGod = new JButton();
        Image God = godImage.getScaledInstance(getProportionWidth(90, 150, labelBorderGodWidth), getProportionHeight(160, 230, labelBorderGodHeight), Image.SCALE_DEFAULT);
        buttonGod.setIcon(new ImageIcon(God));
        buttonGod.setBounds(getProportionWidth(31, 150, labelBorderGodWidth), getProportionHeight(31, 230, labelBorderGodHeight), getProportionWidth(90, 150, labelBorderGodWidth), getProportionHeight(170, 230, labelBorderGodHeight));
        buttonGod.setOpaque(false);
        labelBorderGod.add(buttonGod);
        buttonGod.addActionListener(
                e -> /*godChoiceDialog.selectGod(godName)*/ new GodIconDialog(frame,godName)
        );
        return labelBorderGod;
    }


    private static boolean isLabelCircle(JLabel component) {
        if (component.getComponent(component.getComponentCount() - 1) instanceof LabelCircle)
            return true;
        return false;
    }

    private static boolean isLabelLux(JLabel component) {
        if (component.getComponent(component.getComponentCount() - 1) instanceof LabelLux)
            return true;
        return false;
    }


    public void initGUI() {
        //frame = new JFrame("Santorini");
        //frame.setLocation(FRAME_DIMENSION.width / 8, FRAME_DIMENSION.height / 8);

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints lim = new GridBagConstraints();
        lim.ipadx = getProportionWidth(102, 1400, internalFrameWidth);
        lim.ipady = getProportionHeight(102, 800, internalFrameEight);

        lim.fill = GridBagConstraints.BOTH;
        lim.anchor = GridBagConstraints.CENTER;
        panel1 = new JPanel();
        panel1.setLayout(layout);

        for (int i = 0; i < 5; i++) {
            lim.gridy = i;
            for (int j = 0; j < 5; j++) {
                lim.gridx = j;
                buttonCells[i][j] = new ButtonCell(i, j, labelEmptyWidth, labelEmptyHeight,
                        e -> {
                            ButtonCell buttonCell = (ButtonCell) e.getSource();
                            super.handleMessage(
                                    new PositionMessage(
                                            this.getPlayer(),
                                            buttonCell.getPosition(),
                                            buttonPower.isClicked()
                                    )
                            );
                        }
                );
                layout.setConstraints(buttonCells[i][j], lim);
                panel1.add(buttonCells[i][j]);
                buttonCells[i][j].setOpaque(false);
                buttonCells[i][j].setContentAreaFilled(false);
                //buttonCells[i][j].setBorderPainted(false);
            }
        }

        panel1.setOpaque(false);
        imageContainer = new ImageContainer();



        //frame.setLayout(new BorderLayout());
        //frame.setPreferredSize(new Dimension(FRAME_WIDTH,FRAME_HEIGHT));
        //frame.setResizable(false);


        Image image = new ImageIcon(this.getClass().getResource("/SantoriniBoard.png")).getImage().getScaledInstance(getProportionWidth(1400,1400,FRAME_WIDTH),getProportionHeight(800,820,FRAME_HEIGHT),Image.SCALE_DEFAULT);
        PrincipalLabel label = new PrincipalLabel(image,new BorderLayout());




        final int labelTerminalWidth = getProportionWidth(350,1400,internalFrameWidth);
        final int labelTerminalEight = getProportionHeight(800,800,internalFrameEight);
        Image imageTerminal = new ImageIcon(this.getClass().getResource("/bg_panelEdgeLeft.png")).getImage().getScaledInstance(labelTerminalWidth,labelTerminalEight,Image.SCALE_DEFAULT);
        PrincipalLabel labelTerminal = new PrincipalLabel(imageTerminal);




        labelGodWidth = getProportionWidth(350,1400,internalFrameWidth);
        labelGodHeight = getProportionHeight(800,800,internalFrameEight);
        Image imageGod = new ImageIcon(this.getClass().getResource("/bg_panelEdgeRight.png")).getImage().getScaledInstance(labelGodWidth,labelGodHeight,Image.SCALE_DEFAULT);
        labelGod = new PrincipalLabel(imageGod);



        //Image of button
        Image imagePower = imageContainer.getButtonImage("buttonPower").getScaledInstance(getProportionWidth(90, 350, labelGodWidth), getProportionHeight(90, 800, labelGodHeight), Image.SCALE_DEFAULT);
        Image imageEndTurn = imageContainer.getButtonImage("buttonEndTurn").getScaledInstance(getProportionWidth(100, 350, labelGodWidth), getProportionHeight(100, 800, labelGodHeight), Image.SCALE_DEFAULT);
        Image imageTutorial = imageContainer.getButtonImage("buttonTutorial").getScaledInstance(getProportionWidth(90, 350, labelTerminalWidth), getProportionHeight(90, 800, labelTerminalEight), Image.SCALE_DEFAULT);
        Image imageMenu = imageContainer.getButtonImage("buttonMenu").getScaledInstance(getProportionWidth(95, 350, labelTerminalWidth), getProportionHeight(95, 800, labelTerminalEight), Image.SCALE_DEFAULT);


        //Creating button
        buttonPower = new ButtonCircle(new ImageIcon(imagePower), Color.WHITE,
                e -> {
                    if(clientModel.getCurrentState() != GameState.NULL && clientModel.getCurrentState() == clientModel.getPowerGodState()){
                        try{
                            clientModel.getSelectedWorkerPos();

                            buttonPower.click();
                            //if user clicks buttonPower power cells must be showed
                            if(buttonPower.isClicked()){
                                removeActionsFromView(clientModel.getActionPositions(clientModel.getSelectedWorkerPos(), ActionType.MOVE));
                                showActionPositions(clientModel.getActionPositions(clientModel.getSelectedWorkerPos(), ActionType.POWER));
                            }
                                //if user clicks again on buttonPower normal action cells must be showed
                            else{
                                removeActionsFromView(clientModel.getActionPositions(clientModel.getSelectedWorkerPos(), ActionType.POWER));
                                showActionPositions(clientModel.getActionPositions(clientModel.getSelectedWorkerPos(), ActionType.MOVE));
                            }
                        }
                        catch(NullPointerException npe){
                            //TODO: non bellissimo magari bloccare il bottone è meglio
                            JOptionPane.showMessageDialog(frame, "You must select a worker before");
                        }
                    }
                    else{
                        //TODO: AVVERTI UTENTE caso non puo usare il potere
                        JOptionPane.showMessageDialog(frame, "You can't use a God Power now");
                    }
                }
        );
        buttonEndTurn = new ButtonCircle(new ImageIcon(imageEndTurn), Color.WHITE,
                e -> {
                    new WelcomeDialog(new WelcomeFrame(),this);
                }
        );
        buttonTutorial = new ButtonCircle(new ImageIcon(imageTutorial), Color.WHITE,
                e -> {
                    new DialogTutorial(frame,"TUTORIAL");
                }
        );
        buttonMenu = new ButtonCircle(new ImageIcon(imageMenu), Color.WHITE,
                e -> {
                    frame.dispose();
                }
        );
        buttonPower.setBounds(getProportionWidth(50, 350, labelGodWidth), getProportionHeight(460, 800, labelGodHeight), getProportionWidth(95, 350, labelGodWidth), getProportionHeight(95, 800, labelGodHeight));
        buttonEndTurn.setBounds(getProportionWidth(180, 350, labelGodWidth), getProportionHeight(460, 800, labelGodHeight), getProportionWidth(90, 350, labelGodWidth), getProportionHeight(90, 800, labelGodHeight));
        buttonTutorial.setBounds(getProportionWidth(60, 350, labelTerminalWidth), getProportionHeight(470, 800, labelTerminalEight), getProportionWidth(92, 350, labelTerminalWidth), getProportionHeight(92, 800, labelTerminalEight));
        buttonMenu.setBounds(getProportionWidth(200,350,labelTerminalWidth),getProportionHeight(470,800,labelTerminalEight),getProportionWidth(85,350,labelTerminalWidth),getProportionHeight(85,800,labelTerminalEight));

        //Adding button to lateral panel
        labelGod.add(buttonPower);
        labelGod.add(buttonEndTurn);
        labelTerminal.add(buttonTutorial);
        labelTerminal.add(buttonMenu);



        label.add(labelGod,BorderLayout.EAST);
        label.add(labelTerminal,BorderLayout.WEST);
        label.add(panel1,BorderLayout.CENTER);
        //frame.getContentPane().add(label);

        frame = new WelcomeFrame(label);


        //frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //frame.pack();
        //frame.setVisible(true);
    }

    private static int getProportionWidth(int dimensionWidth, int oldContainerDimensionWidth, int newContainerWidth){
        int result;
        result = dimensionWidth * (newContainerWidth);
        result = result/oldContainerDimensionWidth;
        return result;

    }

    private static int getProportionHeight(int dimensionHeight, int oldContainerDimensionHeight, int newContainerHeight) {
        int res;
        res = dimensionHeight * (newContainerHeight);
        res = res / oldContainerDimensionHeight;
        return res;

    }

    @Override
    public void receiveErrorMessage(String error) {

    }

    @Override
    public void init() {
        SwingUtilities.invokeLater(this::initGUI);
    }

    @Override
    public void showGodLikeChoice(List<String> gods) {
        /*List<JLabel> godLabels = new ArrayList<>();
        gods.forEach(
                god -> godLabels.add(getIconGodProfile(imageContainer.getGodimage(god), god))
        );*/
        godChoiceDialog = new GodChoiceDialog(frame, gods /*godLabels*/, clientModel.isThreePlayersGame(), clientModel.isGodLikeChoosingCards(),
                e ->{
            if(clientModel.isGodLikeChoosingCards())
                super.handleMessage(new GodLikeChoseMessage(clientModel.getPlayerIndex(), godChoiceDialog.getChosenGod()));
                });
    }

    @Override
    public void showGodToSelect(List<String> godLikeGods) {

    }

    @Override
    public String showSelectIP(String message) {
        return JOptionPane.showInputDialog(frame, message);
    }

    @Override
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }

    @Override
    public void showGetNickname() {
        SwingUtilities.invokeLater(
                () -> new WelcomeDialog(frame, this)
        );
    }

    //TODO: serve per rimuovere le celle illuminate
    @Override
    public void removeActionsFromView(List<Position> list) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                for (Position pos : list) {
                    JLabel firsLabel = (JLabel) buttonCells[pos.row][pos.col].getComponent(buttonCells[pos.row][pos.col].getComponentCount() - 1);
                    if (firsLabel.getComponentCount() != 0 && !(isLabelLux(firsLabel))) {
                    //Tower 1
                    JLabel labelT1 = (JLabel) firsLabel.getComponent(firsLabel.getComponentCount() - 1);
                    if(labelT1.getComponentCount() != 0 && !(isLabelLux(firsLabel))){
                        //Tower 2
                        JLabel labelT2 = (JLabel) labelT1.getComponent(labelT1.getComponentCount() - 1);
                        if(labelT2.getComponentCount() != 0 && !(isLabelLux(labelT1))){
                         //Tower 3
                         JLabel labelT3 = (JLabel) labelT2.getComponent(labelT2.getComponentCount() - 1);
                         labelT3.remove(labelT3.getComponentCount() - 1);
                        }else{
                        labelT2.remove(labelT2.getComponentCount() - 1);
                        }
                    }else{
                     labelT1.remove(labelT1.getComponentCount() - 1);
                }
                }else{
                firsLabel.remove(firsLabel.getComponentCount() - 1);
                }
            }
            }
        });
    }

    private LabelCircle getPlayerIcon(PlayerIndex playerIndex) {
        LabelCircle buttonPlayer = null;
        if (playerIndex.equals(PlayerIndex.PLAYER0)) {
            Image imagePlayer = new ImageIcon(("src/main/resources/TalusToken.png")).getImage().getScaledInstance(getProportionWidth(50, 18, labelEmptyWidth), getProportionHeight(50, 19, labelEmptyHeight), Image.SCALE_DEFAULT);
            buttonPlayer = new LabelCircle(new ImageIcon(imagePlayer), Color.RED);
            buttonPlayer.setBounds(getProportionWidth(37, 18, labelEmptyWidth), getProportionHeight(34, 19, labelEmptyHeight), getProportionWidth(53, 18, labelEmptyWidth), getProportionHeight(53, 19, labelEmptyHeight));
        } else if (playerIndex.equals(PlayerIndex.PLAYER1)) {
            Image imagePlayer = new ImageIcon(("src/main/resources/TalusToken.png")).getImage().getScaledInstance(getProportionWidth(50, 18, labelEmptyWidth), getProportionHeight(50, 19, labelEmptyHeight), Image.SCALE_DEFAULT);
            buttonPlayer = new LabelCircle(new ImageIcon(imagePlayer), Color.BLUE);
            buttonPlayer.setBounds(getProportionWidth(37, 18, labelEmptyWidth), getProportionHeight(34, 19, labelEmptyHeight), getProportionWidth(53, 18, labelEmptyWidth), getProportionHeight(53, 19, labelEmptyHeight));
        }else if(playerIndex.equals(PlayerIndex.PLAYER2)) {
            Image imagePlayer = new ImageIcon(("src/main/resources/TalusToken.png")).getImage().getScaledInstance(getProportionWidth(50, 18, labelEmptyWidth), getProportionHeight(50, 19, labelEmptyHeight), Image.SCALE_DEFAULT);
            buttonPlayer = new LabelCircle(new ImageIcon(imagePlayer), Color.CYAN);
            buttonPlayer.setBounds(getProportionWidth(37, 18, labelEmptyWidth), getProportionHeight(34, 19, labelEmptyHeight), getProportionWidth(53, 18, labelEmptyWidth), getProportionHeight(53, 19, labelEmptyHeight));
        }

        return buttonPlayer;

    }

    @Override
    public void updatePutWorker(PutWorkerMessage message) {
        Position pos1 = message.getPositionOne();
        Position pos2 = message.getPositionTwo();

        LabelCircle labelWorker = getPlayerIcon(message.getClient());
        LabelCircle labelWorker2 = getPlayerIcon(message.getClient());

        JLabel labelButton = (JLabel) buttonCells[pos1.row][pos1.col].getComponent(buttonCells[pos1.row][pos1.col].getComponentCount() - 1);
        JLabel labelButton1 = (JLabel) buttonCells[pos2.row][pos2.col].getComponent(buttonCells[pos2.row][pos2.col].getComponentCount() - 1);

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

        LabelCircle labelWorker = getPlayerIcon(message.getClient());
        //labelWorker.setLayout(new BorderLayout());


        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //Label on old cell
                int level = 0;
                JLabel labelInit;
                labelInit = (JLabel) buttonCells[oldPos.row][oldPos.col].getComponent(buttonCells[oldPos.row][oldPos.col].getComponentCount() - 1);
                if (isLabelCircle(labelInit)) {
                    labelInit.remove(labelInit.getComponentCount() - 1);
                } else {
                    //Tower level 1
                    level++;
                    labelInit = (JLabel) labelInit.getComponent(labelInit.getComponentCount() - 1);
                    if (isLabelCircle(labelInit)) {
                        labelInit.remove(labelInit.getComponentCount() - 1);
                    } else {
                        //Tower level 2
                        level++;
                        labelInit = (JLabel) labelInit.getComponent(labelInit.getComponentCount() - 1);
                        if (isLabelCircle(labelInit)) {
                            labelInit.remove(labelInit.getComponentCount() - 1);
                        } else {
                            //Tower level 3
                            level++;
                            labelInit = (JLabel) labelInit.getComponent(labelInit.getComponentCount() - 1);
                            if (isLabelCircle(labelInit)) {
                                labelInit.remove(labelInit.getComponentCount() - 1);
                            }
                        }
                    }
                }
                //label on newCell
                JLabel l;
                l = (JLabel) buttonCells[newPos.row][newPos.col].getComponent(buttonCells[newPos.row][newPos.col].getComponentCount() - 1);
                if (isLabelCircle(l)) {
                    switch (level) {
                        case 0:
                            labelInit.add(l.getComponent(l.getComponentCount() - 1));
                            break;
                        case 1:
                            l.getComponent(l.getComponentCount() - 1).setBounds(getProportionWidth(5, 18, labelInit.getWidth()), getProportionHeight(5, 19, labelInit.getHeight()), getProportionWidth(8, 18, labelInit.getWidth()), getProportionHeight(8, 19, labelInit.getHeight()));
                            labelInit.add(l.getComponent(l.getComponentCount() - 1));
                            break;
                    }
                }
                if (l.getComponentCount() != 0) {
                    //Tower level 1
                    JLabel labelTower1 = (JLabel) l.getComponent(l.getComponentCount() - 1);
                    if(labelTower1.getComponentCount() != 0) {
                        if (isLabelCircle(labelTower1)) {
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
                            if (isLabelCircle(labelTower2)) {
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
                                if (isLabelCircle(labelTower3)) {
                                    switch (level) {
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
                    buttonCells[newPos.row][newPos.col].add(l);
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
                        JLabel labelButton = (JLabel) buttonCells[buildPos.row][buildPos.col].getComponent(buttonCells[buildPos.row][buildPos.col].getComponentCount() - 1);
                        buildLabel.setBounds(getProportionWidth(-3,18,labelEmptyWidth),getProportionHeight(1,19,labelEmptyHeight),getProportionWidth(129,18,labelEmptyWidth),getProportionHeight(126,19,labelEmptyHeight));
                        buildLabel.setOpaque(false);
                        labelButton.add(buildLabel);
                        //buttonMatrix[buildPos.row][buildPos.col].add(labelButton);
                        break;
                    case 2:
                        JLabel buildCorrect0 = (JLabel) buttonCells[buildPos.row][buildPos.col].getComponent(buttonCells[buildPos.row][buildPos.col].getComponentCount() - 1);
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
                        JLabel buildLabel0 = (JLabel) buttonCells[buildPos.row][buildPos.col].getComponent(buttonCells[buildPos.row][buildPos.col].getComponentCount() - 1);
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

                        JLabel build1 = (JLabel) buttonCells[buildPos.row][buildPos.col].getComponent(buttonCells[buildPos.row][buildPos.col].getComponentCount() - 1);
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

    public void showActionPositions(List<Position> actionPos){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                for(Position p : actionPos){

                    JLabel labelButton = (JLabel) buttonCells[p.row][p.col].getComponent(buttonCells[p.row][p.col].getComponentCount() - 1);
                    if (labelButton.getComponentCount() != 0 && !(isLabelCircle(labelButton))) {
                        //Tower 1
                        JLabel labelTow1 = (JLabel) labelButton.getComponent(labelButton.getComponentCount() - 1);
                        if (labelTow1.getComponentCount() != 0 && !(isLabelCircle(labelTow1))) {
                            //Tower 2
                            JLabel labelTow2 = (JLabel) labelTow1.getComponent(labelTow1.getComponentCount() - 1);
                            if (labelTow2.getComponentCount() != 0 && !(isLabelCircle(labelTow2))) {
                                //Tower 3
                                JLabel labelTow3 = (JLabel) labelTow2.getComponent(labelTow2.getComponentCount() - 1);
                                Image imageTow3 = new ImageIcon(this.getClass().getResource("/playermoveindicator_blue.png")).getImage().getScaledInstance(getProportionWidth(15, 19, labelTow3.getWidth()), getProportionHeight(15, 19, labelTow3.getHeight()), Image.SCALE_DEFAULT);
                                LabelLux labelIndicatorTow3 = new LabelLux(imageTow3);
                                labelIndicatorTow3.setBounds(getProportionWidth(2, 18, labelTow3.getWidth()), getProportionHeight(2, 19, labelTow3.getHeight()), getProportionWidth(15, 19, labelTow3.getWidth()), getProportionHeight(15, 19, labelTow3.getHeight()));
                                labelTow3.add(labelIndicatorTow3);

                            } else {
                                Image imageTow2 = new ImageIcon(this.getClass().getResource("/playermoveindicator_blue.png")).getImage().getScaledInstance(getProportionWidth(15, 19, labelTow2.getWidth()), getProportionHeight(15, 19, labelTow2.getHeight()), Image.SCALE_DEFAULT);
                                LabelLux labelIndicatorTow2 = new LabelLux(imageTow2);
                                labelIndicatorTow2.setBounds(getProportionWidth(2, 18, labelTow2.getWidth()), getProportionHeight(2, 19, labelTow2.getHeight()), getProportionWidth(15, 19, labelTow2.getWidth()), getProportionHeight(15, 19, labelTow2.getHeight()));
                                labelTow2.add(labelIndicatorTow2);
                            }
                        }else{
                            Image imageTow1 = new ImageIcon(this.getClass().getResource("/playermoveindicator_blue.png")).getImage().getScaledInstance(getProportionWidth(15, 19, labelTow1.getWidth()), getProportionHeight(15, 19, labelTow1.getHeight()), Image.SCALE_DEFAULT);
                            LabelLux labelIndicatorTow1 = new LabelLux(imageTow1);
                            labelIndicatorTow1.setBounds(getProportionWidth(2, 18, labelTow1.getWidth()), getProportionHeight(2, 19, labelTow1.getHeight()), getProportionWidth(15, 19, labelTow1.getWidth()), getProportionHeight(15, 19, labelTow1.getHeight()));
                            labelTow1.add(labelIndicatorTow1);
                        }
                    }else {
                        Image imageIndicator = new ImageIcon(this.getClass().getResource("/playermoveindicator_blue.png")).getImage().getScaledInstance(getProportionWidth(125, 18, labelEmptyWidth), getProportionHeight(127, 19, labelEmptyHeight), Image.SCALE_DEFAULT);
                        LabelLux labelIndicator = new LabelLux(imageIndicator);
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
                //god image
                JLabel godChosen = getIconGodProfile(imageContainer.getGodimage(message.getGodName()), message.getGodName());
                labelGod.add(godChosen);
                //case when this is the client god
                if(message.getClient().equals(clientModel.getPlayerIndex())){
                    labelGod.add(godChosen);
                }
                //case when this is not the client god
                else{
                    //TODO: mettere immagine del god scelto da nemici nel pannello a sinistra
                }
            }
        });
    }

    @Override
    public void updateActions(PositionMessage message){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                showActionPositions(clientModel.getActionPositions(message.getPosition(), ActionType.MOVE));
            }
        });
    }

}