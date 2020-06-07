package it.polimi.ingsw.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GodChoiceDialog extends JLabel {

    private List<String> chosenGod = new ArrayList<>();
    private final ImageContainer imageContainer = new ImageContainer();
    private List<JButton> godsShow;
    boolean isThreePlayerGame;
    boolean isGodLike;
    private JButton okButton;
    private JFrame frame;
    private JDialog dialog;
    private Map<String,String> godProfile;
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private ButtonCircle buttonDone;
    private ButtonCircle buttonDelete;
    private boolean clicked = false;


    public GodChoiceDialog(List<String> listGodName, boolean isThreePlayerGame, boolean isGodLike, ActionListener okListener) {
        super("");
        frame = (JFrame) super.getFocusCycleRootAncestor();
        this.isThreePlayerGame = isThreePlayerGame;
        this.isGodLike = isGodLike;
        setLayout(new BorderLayout());


        GridBagLayout godLayout = new GridBagLayout();
        GridBagConstraints lim = new GridBagConstraints();
        lim.ipadx = 25;
        lim.ipady = 50;

        JPanel panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        panel.setLayout(godLayout);
        godsShow = createListGods(listGodName);
        for(JButton j : godsShow){
            godLayout.setConstraints(j, lim);
            panel.add(j);
        }




        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane, BorderLayout.CENTER);
        //bottone per confermare
        this.okButton = new JButton("OK");
        okButton.setEnabled(false);
        okButton.addActionListener(okListener);
        add(okButton, BorderLayout.SOUTH);


        setVisible(true);
    }

    public List<String> getChosenGod(){
        return this.chosenGod;
    }

    public void selectMultipleGod(String god, JButton button) {
        if (clicked) {
            //Illuminare il dio
            button.setBackground(Color.GREEN);
            button.setOpaque(true);
            chosenGod.add(god);
        } else {
            //Spegnere il dio
            button.setBackground(Color.WHITE);
            button.setOpaque(true);
            chosenGod.remove(god);
        }

        if(this.isThreePlayerGame){
            if(chosenGod.size() == 3){
                okButton.setEnabled(true);
            }
            else{
                okButton.setEnabled(false);
            }
        }
        else{
            if(chosenGod.size() == 2){
                okButton.setEnabled(true);
            }
            else{
                okButton.setEnabled(false);
            }
        }
    }

    public void selectSingleGod(String god, JButton button){
        if (clicked) {
            //Illuminare il dio
            button.setBackground(Color.GREEN);
            button.setOpaque(true);
            chosenGod.add(god);

        } else {
            //Spegnere il dio
            button.setBackground(Color.WHITE);
            button.setOpaque(true);
            chosenGod.remove(god);
        }

        if(chosenGod.size() == 1){
            okButton.setEnabled(true);
        }
        else{
            okButton.setEnabled(false);
        }
    }

    private List<JButton> createListGods(List<String> gods){
        List<JButton> listGods = new ArrayList<>();
        for(String god: gods){
            Image imageGod = imageContainer.getGodimage(god).getScaledInstance(150,300,Image.SCALE_DEFAULT);
            JButton buttonGod = new JButton();
            buttonGod.setIcon(new ImageIcon(imageGod));
            buttonGod.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    getIconDialog(god,buttonGod,isGodLike);
                }
            });
            listGods.add(buttonGod);
        }
        return listGods;
    }

    public void getIconDialog(String godName, JButton buttonGod,boolean isGodLike){
        dialog = new JDialog(frame,"GOD PROFILE");
        Font font = new Font("Impatto", Font.PLAIN, 33);
        Font font1 = new Font("Impatto", Font.PLAIN, 10);
        JLabel labelGround = new JLabel("");
        Image imageGround = new ImageIcon("src/main/resources/Odyssey_UI_Backdrop.png").getImage().getScaledInstance(520,315,Image.SCALE_DEFAULT);
        labelGround.setIcon(new ImageIcon(imageGround));
        JLabel labelGod = new JLabel("");
        Image imageGod = imageContainer.getGodimage(godName).getScaledInstance(150,250,Image.SCALE_DEFAULT);
        labelGod.setIcon(new ImageIcon(imageGod));
        labelGod.setBounds(2,20,150,250);
        labelGround.add(labelGod);
        createMap();
        JLabel labelGodName = new JLabel(godName.toUpperCase());
        labelGodName.setBounds(155,10,600,50);
        labelGodName.setFont(font);
        labelGodName.setForeground(Color.WHITE);
        String power = getPower(godName);
        String[] powerSplit = power.split(",");
        String firstPart = powerSplit[0];
        String secondPart = powerSplit[1];
        JLabel labelFirst = new JLabel(firstPart);
        JLabel labelSecond = new JLabel(secondPart);
        labelFirst.setBounds(155,45,500,50);
        labelSecond.setBounds(155,60,500,50);
        labelSecond.setFont(font1);
        labelSecond.setForeground(Color.WHITE);
        labelFirst.setForeground(Color.WHITE);
        labelFirst.setFont(font1);
        Image imageDone = new ImageIcon("src/main/resources/Done.png").getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        buttonDone = new ButtonCircle(new ImageIcon(imageDone),Color.GREEN,true,e ->{
            if(chosenGod.contains(godName)){
                dialog.dispose();
                return;
            }else{
            buttonDone.click();
                if (!clicked) {
                    click();
                }
                if(isGodLike){
                    selectMultipleGod(godName,buttonGod);
                }else{
                    selectSingleGod(godName,buttonGod);
                }
                dialog.dispose();
            }
        });
        buttonDone.setBounds(200,200,60,60);
        Image imageDelete = new ImageIcon("src/main/resources/Destroy.png").getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        buttonDelete = new ButtonCircle(new ImageIcon(imageDelete),Color.RED,true,e ->{
            clicked = false;
            if(isGodLike){
                selectMultipleGod(godName,buttonGod);
            }else{
                selectSingleGod(godName,buttonGod);
            }
            dialog.dispose();
        });
        buttonDelete.setBounds(280,200,60,60);
        labelGround.add(buttonDone);
        labelGround.add(buttonDelete);
        labelGround.add(labelSecond);
        labelGround.add(labelFirst);
        labelGround.add(labelGodName);
        dialog.add(labelGround);
        dialog.setPreferredSize(new Dimension(520, 315));
        dialog.setLocation(screenSize.width / 4, screenSize.height / 4);
        dialog.setResizable(false);
        dialog.pack();
        dialog.setVisible(true);
    }

    private void createMap(){
        godProfile = new HashMap<>();
        godProfile.put("Apollo","Your Worker may move into an opponent Worker’s, space by forcing their Worker to the space yours just vacated.");
        godProfile.put("Artemis","Your Worker may move one additional time, but not back to its initial space.");
        godProfile.put("Athena","If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.");
        godProfile.put("Atlas","Your Worker may build, a dome at any level.");
        godProfile.put("Demeter","Your Worker may build one additional time, but not on the same space.");
        godProfile.put("Hephaestus","Your Worker may build one additional, block (not dome) on top of your first block.");
        godProfile.put("Minotaur","Our Worker may move into an opponent Worker’s space if their Worker, can be forced one space straight backwards to an unoccupied space.");
        godProfile.put("Pan","You also win if your Worker, moves down two or more levels.");
        godProfile.put("Prometheus","If your Worker does not move up, it may build both before and after moving.");
        godProfile.put("Zeus","Your worker may, build a block under itself.");
        godProfile.put("Hestia","Your worker may build one additional time, but this cannot be on a perimeter space.");
        godProfile.put("Triton","Each time your worker moves into a, perimeter space it may immediately move again.");
        godProfile.put("Charon","Before your worker moves you may force a neighboring opponent worker, to the space directly on the other side of your worker if that space is unoccupied.");
        godProfile.put("Hera","An opponent cannot win, by moving into a perimeter space.");
    }

    private String getPower(String nameGod){
        return godProfile.get(nameGod);
    }

    public void click() {
        this.clicked = !this.clicked;
    }

}






/*
package it.polimi.ingsw.view;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
*/
/**
 * Dialog that appears when a player has to select gods,
 * either when godlike chooses the gods of the match or when a normal player has to choose his own god
 * Constructor needs the JFrame GUI, and a list of gods to visualize, and the ImageContainer
 * <p>
 * Reimplementation of the DefaultListCellRenderer to visualize images with the JList
 *//*
public class GodChoiceDialog extends JDialog {
    private final JPanel panel = new JPanel();
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final Map<String, Image> imageMap;
    private final ImageContainer container;
    public GodChoiceDialog(JFrame frame, List<String> godsToShow, ImageContainer container, boolean threeplayersgame) {
        super(frame, "God choice");
        setLayout(new BorderLayout());
        this.container = container;
        */
/**
 * Reimplementation of the DefaultListCellRenderer to visualize images with the JList
 *//*
        class GodChoiceDialogRender extends DefaultListCellRenderer {
            final Font font = new Font("helvetica", Font.PLAIN, 50);
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setIcon(new ImageIcon(imageMap.get((String) value)));
                label.setHorizontalTextPosition(JLabel.CENTER);
                label.setVerticalTextPosition(JLabel.BOTTOM);
                label.setFont(font);
                label.setBackground(Color.DARK_GRAY);
                return label;
            }
        }
        imageMap = createImageMap(godsToShow);
        JList<Object> list = new JList<>(godsToShow.toArray());
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(1);
        list.setCellRenderer(new GodChoiceDialogRender());
        if(godsToShow.size() > 3){
            if(threeplayersgame)
                list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            else
                list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        }
        else
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(screenSize.width/2,screenSize.height/2));
        //setPreferredSize(new Dimension(screenSize.width - screenSize.width/3,screenSize.height/2));
        setLocation(screenSize.width/5, screenSize.height/4);
        setResizable(false);
        add(scrollPane, BorderLayout.NORTH);
        JButton okButton = new JButton("OK");
        add(okButton, BorderLayout.SOUTH);
        pack();
        //setMinimumSize(new Dimension(screenSize.width/6,screenSize.height/3));
        setPreferredSize(new Dimension((screenSize.width)- screenSize.width/4,screenSize.height/3));
        setVisible(true);
    }
    private Map<String, Image> createImageMap(List<String> godsToShow) {
        Map<String, Image> map = new HashMap<>();
        for(String s : godsToShow){
            map.put(s, container.getGodimage(s).getScaledInstance(screenSize.width/6, screenSize.height/3,Image.SCALE_DEFAULT));
        }
        return map;
    }
}*/