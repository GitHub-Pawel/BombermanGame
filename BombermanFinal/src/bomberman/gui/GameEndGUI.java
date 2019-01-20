package bomberman.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameEndGUI extends JFrame {

    private JLabel gameEndLabel;        // Ending label
    private JButton quitButton = new JButton("", new ImageIcon("menuImages\\quitButton.jpg"));

    /********************************************************************
     *                         Constructor                              *
     ********************************************************************/

    public GameEndGUI (ImageIcon gameEndImage){

        setTitle("Game end");
        setSize(315, 330);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        gameEndLabel = new JLabel("", gameEndImage, JLabel.CENTER);
        gameEndLabel.setBounds(0, 0, 300, 300);

        quitButton.setBounds(50, 200, 200, 50);
        quitButton.setVisible(true);

        gameEndLabel.add(quitButton);

        add(gameEndLabel);
        setVisible(true);


        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }


}
