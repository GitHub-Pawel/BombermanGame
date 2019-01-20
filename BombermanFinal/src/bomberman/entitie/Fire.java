package bomberman.entitie;

import bomberman.component.Block;

import javax.swing.*;

public class Fire extends Block {

    /********************************************************************
     *                         Constructor                              *
     ********************************************************************/

    public Fire() {
        super();
        this.setImage(new ImageIcon("images\\fire.jpg"));
    }
}
