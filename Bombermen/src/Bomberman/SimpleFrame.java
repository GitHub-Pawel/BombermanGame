package Bomberman;

import javax.swing.*;
import java.awt.Dimension;

public class SimpleFrame extends JFrame {

    public SimpleFrame(){
        setPreferredSize(new Dimension(300, 100));

        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
