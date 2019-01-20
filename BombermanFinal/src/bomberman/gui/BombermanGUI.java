package bomberman.gui;

import bomberman.component.Board;

import javax.swing.*;
import java.awt.*;

public class BombermanGUI extends JFrame {

    private Board board;
    private JPanel rootPanel;
    private JLabel field[][];


    /********************************************************************
     *                         Constructor                              *
     * @param board                                                     *
     ********************************************************************/

    public BombermanGUI(Board board){
        this.board = board;
        field = new JLabel[this.board.getTableLength()][this.board.getTableLength()];       // The game field consists of singular labels
        rootPanel = new JPanel();

        rootPanel.setLayout(new GridLayout(this.board.getTableLength(), this.board.getTableLength()));


        for (int i = 0; i < this.board.getTableLength(); i++) {          // Game field setting based on board state
            for (int j = 0; j < this.board.getTableLength(); j++) {
                field[i][j] = new JLabel();
                try {
                    field[i][j].setIcon(this.board.getTable()[i][j].getImage());
                } catch (NullPointerException e){
                }
                rootPanel.add(field[i][j]);
            }
        }

        this.setContentPane(this.rootPanel);
        this.setTitle("bomberman");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

    /********************************************************************
     *                         Screen reloading                         *
     ********************************************************************/

    public void screenReload(){         // Refreshing the game field in depend of board state
        for (int i = 0; i < board.getTableLength(); i++) {
            for (int j = 0; j < board.getTableLength(); j++) {
                try {
                    field[i][j].setIcon(board.getTable()[i][j].getImage());
                } catch (NullPointerException e){
                }
            }
        }
    }

}
