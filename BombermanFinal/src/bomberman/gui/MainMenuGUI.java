package bomberman.gui;

import bomberman.GameEngine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;

public class MainMenuGUI extends JFrame {

    private int fieldSize;
    private int numberOfGhosts;
    Font font = new Font("Berlin Sans FB", Font.BOLD, 30);
    private GameEngine game;

    // Main label and its buttons
    private JLabel mainLabel;
    private ImageIcon backgroundImage = new ImageIcon("menuImages\\mainLabel.jpg");

    private JButton singlePlayerButton = new JButton("", new ImageIcon("menuImages\\singleplayerButton.jpg"));
    private JButton multiplayerButton = new JButton("", new ImageIcon("menuImages\\multiplayerButton.jpg"));
    private JButton cooperativeButton = new JButton("", new ImageIcon("menuImages\\cooperativeButton.jpg"));
    private JButton guideButton = new JButton("", new ImageIcon("menuImages\\guideButton.jpg"));
    private JButton quitButton = new JButton("", new ImageIcon("menuImages\\quitButton.jpg"));
    private JButton optionsButton = new JButton("", new ImageIcon("menuImages\\optionsButton.jpg"));
    private JButton backButton = new JButton("", new ImageIcon("menuImages\\backButton.jpg"));

    // Guide label
    private JLabel guideLabel;
    private ImageIcon guideImage = new ImageIcon("menuImages\\guideLabel.jpg");

    // Options label, its buttons and text fields
    private JLabel optionsLabel;
    private ImageIcon optionsImage = new ImageIcon("menuImages\\optionsLabel.jpg");

    private JButton increaseFieldSizeButton = new JButton("", new ImageIcon("menuImages\\increaseButton.jpg"));
    private JButton decreaseFieldSizeButton = new JButton("", new ImageIcon("menuImages\\decreaseButton.jpg"));
    private JTextField currentFieldType = new JTextField();

    private JButton addGhostButton = new JButton("", new ImageIcon("menuImages\\addButton.jpg"));
    private JButton subtractGhostButton = new JButton("", new ImageIcon("menuImages\\subtractButton.jpg"));
    private JTextField currentNumberOfGhosts = new JTextField();


    /********************************************************************
     *                         Constructor                              *
     ********************************************************************/

    public MainMenuGUI(){

        this.fieldSize = 15;        // Default size of game field
        this.numberOfGhosts = 5;    // Default number of ghosts

        setTitle("bomberman");
        setSize(700, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        mainLabel = new JLabel("", backgroundImage, JLabel.CENTER);
        mainLabel.setBounds(0, 0, 700, 700);

        singlePlayerButton.setBounds(180, 270, 340, 50);
        singlePlayerButton.setVisible(true);

        multiplayerButton.setBounds(180, 330, 340, 50);
        multiplayerButton.setVisible(true);

        cooperativeButton.setBounds(180, 390, 340, 50);
        cooperativeButton.setVisible(true);

        guideButton.setBounds(180, 470, 340, 50);
        guideButton.setVisible(true);

        optionsButton.setBounds(180, 530, 340, 50);
        optionsButton.setVisible(true);

        quitButton.setBounds(180, 590, 340, 50);
        quitButton.setVisible(true);


        guideLabel = new JLabel("", guideImage, JLabel.CENTER);
        guideLabel.setBounds(0, 0, 700, 700);

        optionsLabel = new JLabel("", optionsImage, JLabel.CENTER);
        optionsLabel.setBounds(0, 0, 700, 700);

        backButton.setBounds(520, 620, 160, 40);
        backButton.setVisible(true);

        increaseFieldSizeButton.setBounds(50, 330, 160, 40);
        increaseFieldSizeButton.setVisible(true);

        decreaseFieldSizeButton.setBounds(250, 330, 160, 40);
        decreaseFieldSizeButton.setVisible(true);

        currentFieldType.setBounds(450, 330, 160, 40);
        currentFieldType.setVisible(true);

        addGhostButton.setBounds(50, 490, 160, 40);
        addGhostButton.setVisible(true);

        subtractGhostButton.setBounds(250, 490, 160, 40);
        subtractGhostButton.setVisible(true);

        currentNumberOfGhosts.setBounds(450, 490, 160, 40);
        currentNumberOfGhosts.setVisible(true);


        mainLabel.add(singlePlayerButton);
        mainLabel.add(multiplayerButton);
        mainLabel.add(cooperativeButton);
        mainLabel.add(guideButton);
        mainLabel.add(optionsButton);
        mainLabel.add(quitButton);
        add(mainLabel);

        setVisible(true);

        singlePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game = new GameEngine(1, fieldSize, numberOfGhosts);
                dispose();
            }
        });
        multiplayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game = new GameEngine(2, fieldSize, numberOfGhosts);
                dispose();
            }
        });
        cooperativeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game = new GameEngine(3, fieldSize, numberOfGhosts);
                dispose();
            }
        });
        guideButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                guideLabel.add(backButton);
                add(guideLabel);
                repaint();
            }
        });
        optionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                optionsLabel.add(backButton);
                optionsLabel.add(increaseFieldSizeButton);
                optionsLabel.add(decreaseFieldSizeButton);
                optionsLabel.add(currentFieldType);
                optionsLabel.add(addGhostButton);
                optionsLabel.add(subtractGhostButton);
                optionsLabel.add(currentNumberOfGhosts);
                add(optionsLabel);
                repaint();
                optionsRefresh();
            }
        });
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                add(mainLabel);
                repaint();
            }
        });
        increaseFieldSizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fieldSize=fieldSize + 2;
                if (fieldSize > 21) fieldSize = 21;            // Maximum size of game field
                optionsRefresh();
            }
        });
        decreaseFieldSizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fieldSize=fieldSize - 2;
                if (fieldSize < 13) fieldSize = 13;            // Minimum size of game field
                optionsRefresh();
            }
        });
        addGhostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numberOfGhosts++;
                if (numberOfGhosts > 9) numberOfGhosts = 9;     // Maximum number of ghosts
                optionsRefresh();
            }
        });
        subtractGhostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numberOfGhosts--;
                if (numberOfGhosts < 1) numberOfGhosts = 1;     // Minimum number of ghosts
                optionsRefresh();
            }
        });


    }

    /********************************************************************
     *                          Refresh method                          *
     ********************************************************************/

    public void optionsRefresh(){          // Refreshing text fields in options
        if (fieldSize == 13) {
            currentFieldType.setText("Very small");
        }else if (fieldSize == 15) {
            currentFieldType.setText("Small");
        }else if (fieldSize == 17) {
            currentFieldType.setText("Medium");
        }else if (fieldSize == 19) {
            currentFieldType.setText("Big");
        }else if (fieldSize == 21) {
            currentFieldType.setText("Very big");
        }
        currentFieldType.setFont(font);
        currentFieldType.setHorizontalAlignment(JTextField.CENTER);

        currentNumberOfGhosts.setText(Integer.toString(numberOfGhosts));
        currentNumberOfGhosts.setFont(font);
        currentNumberOfGhosts.setHorizontalAlignment(JTextField.CENTER);
    }


}
