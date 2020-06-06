package it.polimi.ingsw.view;

import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.Client.ViewModelInterface;
import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GUI extends ClientView {

    private JPanel panel1;
    private PrincipalLabel labelGod;
    private PrincipalLabel labelTerminal;
    private PrincipalLabel labelNick;
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
    private static WelcomeFrame frame;
    private static PrincipalLabel label;
    private static Map<Position,LabelCircle> listLabelPosition = new HashMap<>();
    private static Map<Position,LevelPane> listLayerPosition = new HashMap<>();
    private static Map<Position,LabelLux> listLuxPosition = new HashMap<>();
    private static Map<PlayerIndex,String> listPlayerGod = new HashMap<>();
    private static boolean caseApMin = false;
    private static LabelCircle labelApMin ;
    private static LabelCircle firstLabelApMin;
    private static Map<PlayerIndex,String> nicksName = new HashMap<>();


    private ButtonCircle buttonPower;
    private ButtonCircle buttonEndTurn;
    private ButtonCircle buttonTutorial;
    private ButtonCircle buttonMenu;
    private JButton buttonPlayerRed;
    private JButton buttonPlayerBlue;
    private JButton buttonPlayerGray;

    private static ImageContainer imageContainer = new ImageContainer();

    private static GodChoiceDialog godChoiceDialog;
    private static ChoosingPlayerDialog choosePlayerDialog;
    private static final int labelTerminalWidth = getProportionWidth(350,1400,internalFrameWidth);
    private static final int labelTerminalEight = getProportionHeight(800,800,internalFrameEight);


    public GUI(ViewModelInterface clientModel) {
        super(clientModel);
    }

    private static JLabel getIconGodProfile(Image godImage, String godName) {

        JLabel labelBorderGod = new JLabel(godName);
        Image imageBorderGod = imageContainer.getBorderGod().getScaledInstance(getProportionWidth(220, 350, labelGodWidth), getProportionHeight(320, 800, labelGodHeight), Image.SCALE_DEFAULT);
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
                e -> new GodIconDialog(frame,godName)
        );
        return labelBorderGod;
    }

    public void initGUI() {
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
                            //send message to server if you are current player
                            if(clientModel.isAmICurrentPlayer()){
                                super.handleMessage(
                                        new PositionMessage(
                                                this.getPlayer(),
                                                buttonCell.getPosition(),
                                                buttonPower.isClicked()
                                        )
                                );
                            }
                        }
                );
                layout.setConstraints(buttonCells[i][j], lim);
                listLayerPosition.put(new Position(i,j),new LevelPane());
                for(Position pos : listLayerPosition.keySet()){
                    if(pos.equals(new Position(i,j))){
                        buttonCells[i][j].add(listLayerPosition.get(pos));
                    }
                }
                panel1.add(buttonCells[i][j]);
                buttonCells[i][j].setOpaque(false);
                buttonCells[i][j].setContentAreaFilled(false);
                //buttonCells[i][j].setBorderPainted(false);
            }
        }

        panel1.setOpaque(false);



        Image image = new ImageIcon(this.getClass().getResource("/SantoriniBoard.png")).getImage().getScaledInstance(getProportionWidth(1400,1400,FRAME_WIDTH),getProportionHeight(800,820,FRAME_HEIGHT),Image.SCALE_DEFAULT);
        label = new PrincipalLabel(image,new BorderLayout());

        Image image1 = new ImageIcon(this.getClass().getResource("/Santorini.png")).getImage().getScaledInstance(getProportionWidth(1400,1400,FRAME_WIDTH),getProportionHeight(800,820,FRAME_HEIGHT),Image.SCALE_DEFAULT);
        PrincipalLabel label1 = new PrincipalLabel(image1,new BorderLayout());




        Image imageTerminal = new ImageIcon(this.getClass().getResource("/bg_panelEdgeLeft.png")).getImage().getScaledInstance(labelTerminalWidth,labelTerminalEight,Image.SCALE_DEFAULT);
        labelTerminal = new PrincipalLabel(imageTerminal);




        labelGodWidth = getProportionWidth(350,1400,internalFrameWidth);
        labelGodHeight = getProportionHeight(800,800,internalFrameEight);
        Image imageGod = new ImageIcon(this.getClass().getResource("/bg_panelEdgeRight.png")).getImage().getScaledInstance(labelGodWidth,labelGodHeight,Image.SCALE_DEFAULT);
        labelGod = new PrincipalLabel(imageGod);

        Image imageNick = imageContainer.getPlayersTerminalNick().getScaledInstance(getProportionWidth(200, 350, labelTerminalWidth), getProportionHeight(250, 800, labelTerminalEight),Image.SCALE_DEFAULT);
        labelNick = new PrincipalLabel(imageNick);
        labelNick.setBounds(getProportionWidth(80,350,labelTerminalWidth),getProportionHeight(190,800,labelTerminalEight),getProportionWidth(200,350,labelTerminalWidth),getProportionHeight(250,800,labelTerminalEight));
        buttonPlayerBlue = new JButton();
        buttonPlayerRed = new JButton();
        buttonPlayerGray = new JButton();
        Image imageButtonBlue = imageContainer.getButtonColorPlayer(1).getScaledInstance(getProportionWidth(100,350,labelNick.getWidth()),getProportionHeight(100,800,labelNick.getHeight()),Image.SCALE_DEFAULT);
        Image imageButtonRed = imageContainer.getButtonColorPlayer(0).getScaledInstance(getProportionWidth(100,350,labelNick.getWidth()),getProportionHeight(100,800,labelNick.getHeight()),Image.SCALE_DEFAULT);
        Image imageButtonGray = imageContainer.getButtonColorPlayer(2).getScaledInstance(getProportionWidth(100,350,labelNick.getWidth()),getProportionHeight(100,800,labelNick.getHeight()),Image.SCALE_DEFAULT);
        buttonPlayerBlue.setIcon(new ImageIcon(imageButtonBlue));
        buttonPlayerRed.setIcon(new ImageIcon(imageButtonRed));
        buttonPlayerGray.setIcon(new ImageIcon(imageButtonGray));
        buttonPlayerBlue.setBounds(getProportionWidth(245,350,labelNick.getWidth()),getProportionHeight(150,800,labelNick.getHeight()),getProportionWidth(100,350,labelNick.getWidth()),getProportionHeight(100,800,labelNick.getHeight()));
        buttonPlayerRed.setBounds(getProportionWidth(245,350,labelNick.getWidth()),getProportionHeight(300,800,labelNick.getHeight()),getProportionWidth(100,350,labelNick.getWidth()),getProportionHeight(100,800,labelNick.getHeight()));
        buttonPlayerGray.setBounds(getProportionWidth(245,350,labelNick.getWidth()),getProportionHeight(445,800,labelNick.getHeight()),getProportionWidth(100,350,labelNick.getWidth()),getProportionHeight(100,800,labelNick.getHeight()));
        buttonPlayerRed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GodIconDialog(frame,listPlayerGod.get(PlayerIndex.PLAYER1));
            }
        });
        buttonPlayerBlue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GodIconDialog(frame,listPlayerGod.get(PlayerIndex.PLAYER0));
            }
        });
        buttonPlayerGray.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GodIconDialog(frame,listPlayerGod.get(PlayerIndex.PLAYER2));
            }
        });
        labelNick.add(buttonPlayerRed);
        labelNick.add(buttonPlayerBlue);
        labelNick.add(buttonPlayerGray);


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
                                if(!clientModel.isThereASelectedWorker()){
                                    showMessage("You must select a worker before");
                                    buttonPower.click();
                                    return;
                                }
                                removeActionsFromView();
                                showActionPositions(clientModel.getActionPositions(clientModel.getSelectedWorkerPos(), ActionType.POWER),true);
                            }
                            //if user clicks again on buttonPower normal action cells must be showed
                            else{
                                removeActionsFromView();
                                showActionPositions(clientModel.getActionPositions(clientModel.getSelectedWorkerPos(), ActionType.MOVE),false);
                            }
                        }
                        catch(NullPointerException npe){
                            //TODO: non bellissimo magari bloccare il bottone è meglio
                            showMessage("You must select a worker before");
                        }
                    }
                    else{
                        //TODO: AVVERTI UTENTE caso non puo usare il potere
                        showMessage("You can't use a God Power now");
                    }
                }
        );
        buttonEndTurn = new ButtonCircle(new ImageIcon(imageEndTurn), Color.WHITE,
                e -> {
                    if(clientModel.getCurrentState() == GameState.ENDPHASE || clientModel.getCurrentState() == GameState.BUILDPOWER){
                        handleMessage(new EndTurnMessage(clientModel.getPlayerIndex()));
                    }
                    else
                        showMessage("You can't end the turn now");
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
        labelTerminal.add(labelNick);



        label.add(labelGod,BorderLayout.EAST);
        label.add(labelTerminal,BorderLayout.WEST);
        label.add(panel1,BorderLayout.CENTER);
        //frame.getContentPane().add(label);

        frame = new WelcomeFrame(label1);




        //frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //frame.pack();
        //frame.setVisible(true);
    }

    @Override
    public void deactivatePower(){
        if(buttonPower.isClicked())
            this.buttonPower.click();
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
        godChoiceDialog = new GodChoiceDialog( gods /*godLabels*/, clientModel.isThreePlayersGame(), clientModel.isGodLikeChoosingCards(),
                e -> {
                    handleMessage(new GodLikeChoseMessage(clientModel.getPlayerIndex(), godChoiceDialog.getChosenGod()));
                    frame.dispose();
                    frame = new WelcomeFrame(label);

                });
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.dispose();
                frame = new WelcomeFrame(godChoiceDialog);

            }
        });
    }

    @Override
    public void showGodToSelect(List<String> godLikeGods) {
        godChoiceDialog = new GodChoiceDialog(godLikeGods, clientModel.isThreePlayersGame(), clientModel.isGodLikeChoosingCards(),
                e ->{
                    handleMessage(new PlayerSelectGodMessage(clientModel.getPlayerIndex(), godChoiceDialog.getChosenGod().get(0)));
                    frame.dispose();
                    frame = new WelcomeFrame(label);
                });
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.dispose();
                frame = new WelcomeFrame(godChoiceDialog);
            }
        });
    }

    @Override
    public String showSelectIP(String message) {
        return JOptionPane.showInputDialog(frame, message);
    }

    @Override
    public void showMessage(String message) {
        SwingUtilities.invokeLater(
                () -> JOptionPane.showMessageDialog(frame, message)
        );
    }

    @Override
    public void showGodLikeChooseFirstPlayer() {
        nicksName.put(PlayerIndex.PLAYER0,clientModel.getNickname(PlayerIndex.PLAYER0));
        nicksName.put(PlayerIndex.PLAYER1,clientModel.getNickname(PlayerIndex.PLAYER1));
        if(clientModel.isThreePlayersGame()){
            nicksName.put(PlayerIndex.PLAYER2,clientModel.getNickname(PlayerIndex.PLAYER2));
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                choosePlayerDialog = new ChoosingPlayerDialog(frame,clientModel.isThreePlayersGame(),nicksName,e ->{
                    handleMessage(new GodLikeChooseFirstPlayerMessage(clientModel.getPlayerIndex(),choosePlayerDialog.getSelectedPlayer()));
                    choosePlayerDialog.dispose();
                });
            }
        });
    }

    @Override
    public void showCurrentPlayer(PlayerIndex currentPlayer) {
        //TODO: da implementare
    }

    @Override
    public void showGetNickname() {
        SwingUtilities.invokeLater(
                () -> new WelcomeDialog(frame, this)
        );
    }

    //TODO: serve per rimuovere le celle illuminate
    @Override
    public void removeActionsFromView() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                for (Position pos : listLuxPosition.keySet()) {
                    LabelLux firsLabelLux = listLuxPosition.get(pos);
                    LevelPane firsLabel = listLayerPosition.get(pos);
                    firsLabel.remove(firsLabelLux);
                    firsLabel.revalidate();
                    firsLabel.repaint();
                }
                listLuxPosition.clear();
            }
        });
    }

    private LabelCircle getPlayerIcon(PlayerIndex playerIndex) {
        LabelCircle buttonPlayer = null;
        if (playerIndex.equals(PlayerIndex.PLAYER0)) {
            Image imagePlayer = new ImageIcon(("src/main/resources/TalusToken.png")).getImage().getScaledInstance(getProportionWidth(50, 18, labelEmptyWidth), getProportionHeight(50, 19, labelEmptyHeight), Image.SCALE_DEFAULT);
            buttonPlayer = new LabelCircle(new ImageIcon(imagePlayer), Color.RED);
            buttonPlayer.setBounds(getProportionWidth(37, 18, labelEmptyWidth), getProportionHeight(34, 19, labelEmptyHeight), getProportionWidth(56, 18, labelEmptyWidth), getProportionHeight(56, 19, labelEmptyHeight));
        } else if (playerIndex.equals(PlayerIndex.PLAYER1)) {
            Image imagePlayer = new ImageIcon(("src/main/resources/TalusToken.png")).getImage().getScaledInstance(getProportionWidth(50, 18, labelEmptyWidth), getProportionHeight(50, 19, labelEmptyHeight), Image.SCALE_DEFAULT);
            buttonPlayer = new LabelCircle(new ImageIcon(imagePlayer), Color.BLUE);
            buttonPlayer.setBounds(getProportionWidth(37, 18, labelEmptyWidth), getProportionHeight(34, 19, labelEmptyHeight), getProportionWidth(56, 18, labelEmptyWidth), getProportionHeight(56, 19, labelEmptyHeight));
        }else if(playerIndex.equals(PlayerIndex.PLAYER2)) {
            Image imagePlayer = new ImageIcon(("src/main/resources/TalusToken.png")).getImage().getScaledInstance(getProportionWidth(50, 18, labelEmptyWidth), getProportionHeight(50, 19, labelEmptyHeight), Image.SCALE_DEFAULT);
            buttonPlayer = new LabelCircle(new ImageIcon(imagePlayer), Color.DARK_GRAY);
            buttonPlayer.setBounds(getProportionWidth(37, 18, labelEmptyWidth), getProportionHeight(34, 19, labelEmptyHeight), getProportionWidth(56, 18, labelEmptyWidth), getProportionHeight(56, 19, labelEmptyHeight));
        }

        return buttonPlayer;

    }

    @Override
    public void updatePutWorker(PutWorkerMessage message) {
        Position pos1 = message.getPositionOne();
        Position pos2 = message.getPositionTwo();

        LabelCircle labelWorker = getPlayerIcon(message.getClient());
        LabelCircle labelWorker2 = getPlayerIcon(message.getClient());

        LevelPane labelButton = listLayerPosition.get(pos1);
        LevelPane labelButton1 = listLayerPosition.get(pos2);

        listLabelPosition.put(pos1,labelWorker);
        listLabelPosition.put(pos2,labelWorker2);



        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {


                labelButton.add(labelWorker, labelButton.getLayerPane(4));
                labelButton.revalidate();
                labelButton.repaint();
                labelButton1.add(labelWorker2, labelButton1.getLayerPane(4));
                labelButton1.revalidate();
                labelButton1.repaint();

            }
        });
    }


    @Override
    public void updateMoveWorker(MoveMessage message) {
        Position oldPos = message.getWorkerPosition();
        Position newPos = message.getMovePosition();


        //LabelCircle labelWorker = listLabelPosition.get(oldPos);
        //listLabelPosition.remove(oldPos);
        //listLabelPosition.put(newPos, labelWorker);


        SwingUtilities.invokeLater(
                () -> {
                    if(caseApMin){
                        LabelCircle labelMin = labelApMin;
                        LabelCircle labelSecond = firstLabelApMin;
                        listLabelPosition.put(newPos,labelMin);
                        listLabelPosition.put(oldPos,labelSecond);
                        LevelPane lFirst = listLayerPosition.get(oldPos);
                        LevelPane lMin = listLayerPosition.get(newPos);
                        lFirst.add(labelSecond,4);
                        lFirst.revalidate();
                        lFirst.repaint();
                        lMin.add(labelMin,4);
                        lMin.revalidate();
                        lMin.repaint();
                        caseApMin = false;
                    }else {
                        LabelCircle labelWorker = listLabelPosition.get(oldPos);
                        firstLabelApMin = labelWorker;
                        LevelPane levelToRemove = listLayerPosition.get(oldPos);
                        listLabelPosition.remove(oldPos);
                        levelToRemove.revalidate();
                        levelToRemove.repaint();
                        if (listLabelPosition.get(newPos) != null) {
                            LabelCircle labelApollo = listLabelPosition.get(newPos);
                            LevelPane lToRemove = listLayerPosition.get(newPos);
                            listLabelPosition.remove(newPos);
                            lToRemove.revalidate();
                            lToRemove.repaint();
                            labelApMin = labelApollo;
                            caseApMin = true;
                            return;
                        }
                        listLabelPosition.put(newPos, labelWorker);
                        LevelPane l = listLayerPosition.get(newPos);
                        l.add(labelWorker, 4);
                        l.revalidate();
                        l.repaint();
                        removeActionsFromView();
                    }
                });


    }

    @Override
    public void updateBuild(BuildViewMessage message){
        Position buildPos = message.getBuildPosition();
        //Position myPos = clientModel.getSelectedWorkerPos();
        int level = message.getLevel();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //TODO: nei vari casi inserire le immagini dei diversi livelli! cambiare l'immagine nel caso 1

                JLabel buildLabel;

                switch (level){  //TODO: è giusto il level?
                    case 1:
                        Image imageTower = imageContainer.getTowerLevel(0).getScaledInstance(getProportionWidth(129,18,labelEmptyWidth),getProportionHeight(126,19,labelEmptyHeight),Image.SCALE_DEFAULT);
                        buildLabel = new JLabel("");
                        buildLabel.setIcon(new ImageIcon(imageTower));
                        LevelPane labelButton = listLayerPosition.get(buildPos);
                        buildLabel.setBounds(getProportionWidth(-3,18,labelEmptyWidth),getProportionHeight(1,19,labelEmptyHeight),getProportionWidth(129,18,labelEmptyWidth),getProportionHeight(126,19,labelEmptyHeight));
                        buildLabel.setOpaque(false);
                        labelButton.add(buildLabel, 1);
                        removeActionsFromView();
                        break;
                    case 2:
                        LevelPane buildCorrect = listLayerPosition.get(buildPos);
                        Image imageTowerLevel2 = imageContainer.getTowerLevel(1).getScaledInstance(getProportionWidth(13,16,buildCorrect.getWidth()),getProportionHeight(17,19,buildCorrect.getHeight()),Image.SCALE_DEFAULT);
                        JLabel buildLabel1 = new JLabel("");
                        buildLabel1.setIcon(new ImageIcon(imageTowerLevel2));
                        buildLabel1.setOpaque(false);
                        buildLabel1.setBounds(getProportionWidth(2,18,buildCorrect.getWidth()),getProportionHeight(1,15,buildCorrect.getHeight()),getProportionWidth(13,16,buildCorrect.getWidth()),getProportionHeight(17,19,buildCorrect.getHeight()));
                        buildCorrect.add(buildLabel1, 2);
                        removeActionsFromView();
                        break;
                    case 3:
                        LevelPane buildTower2 = listLayerPosition.get(buildPos);
                        JLabel buildLabel3 = new JLabel("");
                        Image imageTowerLevel3 = imageContainer.getTowerLevel(2).getScaledInstance(getProportionWidth(11,17,buildTower2.getWidth()),getProportionHeight(12,16,buildTower2.getHeight()),Image.SCALE_DEFAULT);
                        buildLabel3.setIcon(new ImageIcon(imageTowerLevel3));
                        buildLabel3.setOpaque(false);
                        buildLabel3.setBounds(getProportionWidth(4,21,buildTower2.getWidth()),getProportionHeight(2,16,buildTower2.getHeight()),getProportionWidth(11,17,buildTower2.getWidth()),getProportionHeight(12,16,buildTower2.getHeight()));
                        buildTower2.add(buildLabel3, 3);
                        removeActionsFromView();
                        break;
                    case 4:

                        LevelPane build1 = listLayerPosition.get(buildPos);
                        Image imageDome = imageContainer.getTowerLevel(3).getScaledInstance(getProportionWidth(50,18,build1.getWidth()),getProportionHeight(50,16,build1.getHeight()),Image.SCALE_DEFAULT);
                        JButton dome = new JButton();
                        dome.setIcon(new ImageIcon(imageDome));
                        dome.setBounds(getProportionWidth(-2,18,build1.getWidth()),getProportionHeight(-1,16,build1.getHeight()),getProportionWidth(50,18,build1.getWidth()),getProportionHeight(50,16,build1.getHeight()));
                        dome.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                new ErrorDialog(frame,"DOME LEVEL 4: you can't move on it!");
                            }
                        });
                        build1.add(dome, 4);
                        removeActionsFromView();
                        break;
                    default:
                        //error
                }
            }
        });
    }

    @Override
    public void showActionPositions(List<Position> actionPos, boolean isPowerCells){

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                for(Position p : actionPos){

                    LevelPane labelButton = listLayerPosition.get(p);
                    Image blueLight;
                    if(!isPowerCells) {
                        blueLight = imageContainer.getBlueLight().getScaledInstance(getProportionWidth(125, 18, labelEmptyWidth), getProportionHeight(127, 19, labelEmptyHeight), Image.SCALE_DEFAULT);
                    }else{
                        blueLight = imageContainer.getPurpleLight().getScaledInstance(getProportionWidth(125, 18, labelEmptyWidth), getProportionHeight(127, 19, labelEmptyHeight), Image.SCALE_DEFAULT);
                    }
                    LabelLux labelIndicator = new LabelLux(blueLight);
                    labelIndicator.setBounds(getProportionWidth(-2, 18, labelEmptyWidth), getProportionHeight(-4, 19, labelEmptyHeight), getProportionWidth(125, 18, labelEmptyWidth), getProportionHeight(130, 19, labelEmptyHeight));
                    listLuxPosition.put(p,labelIndicator);
                    labelButton.add(labelIndicator,labelButton.getLayerPane(0));
                    labelButton.revalidate();
                    labelButton.repaint();

                }
            }
        });

    }

    @Override
    public void updateSelectedCardView(PlayerSelectGodMessage message) {
        listPlayerGod.put(message.getClient(),message.getGodName());
        JLabel labelNick1 = new JLabel(clientModel.getNickname(PlayerIndex.PLAYER0));
        labelNick1.setBounds(getProportionWidth(10,350,labelNick.getWidth()),getProportionHeight(80,800,labelNick.getHeight()),getProportionWidth(200,350,labelNick.getWidth()),getProportionHeight(250,800,labelNick.getHeight()));
        labelNick.add(labelNick1);
        JLabel labelNick2 = new JLabel(clientModel.getNickname(PlayerIndex.PLAYER1));
        labelNick2.setBounds(getProportionWidth(10,350,labelNick.getWidth()),getProportionHeight(240,800,labelNick.getHeight()),getProportionWidth(200,350,labelNick.getWidth()),getProportionHeight(250,800,labelNick.getHeight()));
        labelNick.add(labelNick2);
        if(clientModel.isThreePlayersGame()){
            JLabel labelNick3 = new JLabel(clientModel.getNickname(PlayerIndex.PLAYER2));
            labelNick3.setBounds(getProportionWidth(10,350,labelNick.getWidth()),getProportionHeight(390,800,labelNick.getHeight()),getProportionWidth(200,350,labelNick.getWidth()),getProportionHeight(250,800,labelNick.getHeight()));
            labelNick.add(labelNick3);
        }else{
            labelNick.remove(buttonPlayerGray);
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //god image
                labelNick.revalidate();
                labelNick.repaint();
                JLabel godChosen = getIconGodProfile(imageContainer.getGodimage(message.getGodName()), message.getGodName());

                //case when this is the client god
                if(message.getClient().equals(clientModel.getPlayerIndex())){
                    labelGod.add(godChosen);
                    labelGod.revalidate();
                    labelGod.repaint();
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
                if (listLuxPosition.size() != 0) removeActionsFromView();
                showActionPositions(clientModel.getActionPositions(message.getPosition(), ActionType.MOVE),false);
            }
        });
    }

    @Override
    public void updateRemovePlayer(RemovePlayerMessage message) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                for(Position p : message.getRemovePositions()){
                    LevelPane l = listLayerPosition.get(p);
                    l.remove(listLabelPosition.get(p));
                    listLabelPosition.remove(p);
                    l.revalidate();
                    l.repaint();
                }
            }
        });
    }

    @Override
    public void showWinner(OkMessage message) {
        //TODO: miss label win and close button
        Image imageWinner = imageContainer.getImageWinner().getScaledInstance(getProportionWidth(1400,1400,FRAME_WIDTH),getProportionHeight(800,820,FRAME_HEIGHT),Image.SCALE_DEFAULT);
        PrincipalLabel labelWinner = new PrincipalLabel(imageWinner);
        frame.dispose();
        frame = new WelcomeFrame(labelWinner);
    }

    @Override
    public void showLoser(OkMessage message) {
        showMessage(message.getErrorMessage());
    }

    @Override
    public void showPowerButton() {
        
    }

    @Override
    public void showEndTurnButton() {

    }


}