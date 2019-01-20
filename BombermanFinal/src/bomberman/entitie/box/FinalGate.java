package bomberman.entitie.box;

import bomberman.component.Block;

import javax.swing.*;

public class FinalGate extends Block {
    private boolean openGate;

    /********************************************************************
     *           Singleton Design Pattern (Private Constructor)         *
     ********************************************************************/

    private static FinalGate instance = null;
    public static FinalGate getInstance() {
        if (instance == null){
            instance = new FinalGate();
        }
        return instance;
    }
    private FinalGate(){
        this.openGate = false;
        this.setImage(new ImageIcon("images\\closeGate.jpg"));
    }


    /********************************************************************
     *                      Getters & Setters                           *
     ********************************************************************/

    public void openGate(){
        this.setImage(new ImageIcon("images\\openGate.jpg"));
        this.openGate = true;
    }

    public boolean isOpenGate() {
        return openGate;
    }
}
