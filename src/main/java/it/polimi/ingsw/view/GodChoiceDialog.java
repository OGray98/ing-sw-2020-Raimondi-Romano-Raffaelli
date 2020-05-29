package it.polimi.ingsw.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GodChoiceDialog extends JDialog {

    private List<String> chosenGod = new ArrayList<>();
    private final ImageContainer imageContainer = new ImageContainer();
    private List<JButton> godsShow;
    boolean isThreePlayerGame;
    boolean isGodLike;
    private JButton okButton;


    public GodChoiceDialog(JFrame frame/* List<JLabel> godsToShow*/,List<String> listGodName, boolean isThreePlayerGame, boolean isGodLike, ActionListener okListener) {
        super(frame, "God choice");
        this.isThreePlayerGame = isThreePlayerGame;
        this.isGodLike = isGodLike;

        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(new Dimension(screenSize.width / 2, screenSize.height / 2));
        setResizable(false);
        setLocation(screenSize.width / 4, screenSize.height / 4);

        //this.view = view;
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


       /* for (JLabel l : godsToShow) {
            panel.setBackground(Color.DARK_GRAY);
            godLayout.setConstraints(l, lim);
            panel.add(l);
        }*/

        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane, BorderLayout.CENTER);
        //bottone per confermare
        this.okButton = new JButton("OK");
        okButton.setEnabled(false);
        okButton.addActionListener(okListener);
        add(okButton, BorderLayout.SOUTH);

        pack();
        setMinimumSize(new Dimension(300, 30));
        setVisible(true);
    }

    public List<String> getChosenGod(){
        return this.chosenGod;
    }

    public void selectMultipleGod(String god, JButton button) {
        if (!chosenGod.contains(god)) {
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
        if (!chosenGod.contains(god)) {
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
                    if(isGodLike){
                        selectMultipleGod(god, buttonGod);
                    }
                    else{
                        selectSingleGod(god, buttonGod);
                    }
                }
            });
            listGods.add(buttonGod);
        }
        return listGods;
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
