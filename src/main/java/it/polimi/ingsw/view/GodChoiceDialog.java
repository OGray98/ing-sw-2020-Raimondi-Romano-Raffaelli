package it.polimi.ingsw.view;

import it.polimi.ingsw.Client.ClientView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * Dialog that appears when a player has to select gods,
 * either when godlike chooses the gods of the match or when a normal player has to choose his own god
 * Constructor needs the JFrame GUI, and a list of gods to visualize, and the ImageContainer
 * */
public class GodChoiceDialog extends JDialog {

    private JPanel panel = new JPanel();
    private JScrollPane scrollPane;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    //private ClientView view;
    private final Map<String, Image> imageMap;
    private ImageContainer container;

    public GodChoiceDialog(JFrame frame, List<String> godsToShow, ImageContainer container){
        super(frame, "God choice");
        setLayout(new BorderLayout());

        this.container = container;

        /**
         * Reimplementation of the DefaultListCellRenderer to visualize images with the JList
         * */
        class GodChoiceDialogRender extends DefaultListCellRenderer {

            Font font = new Font("helvetica", Font.PLAIN, 50);

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
        JList list = new JList(godsToShow.toArray());
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(1);
        list.setCellRenderer(new GodChoiceDialogRender());

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(screenSize.width/2,screenSize.height/2));
        setPreferredSize(new Dimension(screenSize.width - screenSize.width/3,screenSize.height/2));
        setLocation(screenSize.width/5, screenSize.height/4);
        setResizable(false);

        add(scrollPane, BorderLayout.NORTH);

        /*old implementation:

        setPreferredSize(new Dimension(screenSize.width/2,screenSize.height/2));
        setResizable(false);
        setLocation(screenSize.width/4, screenSize.height/4);

        //this.view = view;
        GridBagLayout godLayout = new GridBagLayout();
        GridBagConstraints lim = new GridBagConstraints();
        lim.ipadx = 50;
        lim.ipady = 100;

        panel.setLayout(godLayout);

        for(JLabel l : godsToShow){
            panel.setBackground(Color.DARK_GRAY);
            godLayout.setConstraints(l, lim);
            panel.add(l);
        }

        scrollPane = new JScrollPane(panel);
        add(scrollPane, BorderLayout.CENTER);*/

        pack();
        setMinimumSize(new Dimension(screenSize.width/6,screenSize.height/3));
        setVisible(true);
    }

    private Map<String, Image> createImageMap(List<String> godsToShow) {
        Map<String, Image> map = new HashMap<>();
        for(String s : godsToShow){
            map.put(s, container.getGodimage(s).getScaledInstance(screenSize.width/6, screenSize.height/3,Image.SCALE_DEFAULT));
        }
        return map;
    }
}