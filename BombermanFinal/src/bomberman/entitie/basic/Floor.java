package bomberman.entitie.basic;

import bomberman.component.Block;

import javax.swing.*;

public class Floor extends Block {

    /********************************************************************
     *           Singleton Design Pattern (Private Constructor)         *
     ********************************************************************/

    private static Floor instance = null;
    public static Floor getInstance() {
        if (instance == null) {
            instance = new Floor();
        }
        return instance;
    }
    private Floor() {
        super();
        this.setImage(new ImageIcon("images\\floor.jpg"));
    }
}
