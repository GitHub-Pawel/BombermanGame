package bomberman.entitie.basic;

import bomberman.component.Block;

import javax.swing.*;

public class Wall extends Block {

    /********************************************************************
     *           Singleton Design Pattern (Private Constructor)         *
     ********************************************************************/

    private static Wall instance = null;
    public static Wall getInstance(){
        if (instance == null){
            instance = new Wall();
        }
        return instance;
    }

    private Wall() {
        super();
        this.setImage(new ImageIcon("images\\wall.jpg"));
    }
}
