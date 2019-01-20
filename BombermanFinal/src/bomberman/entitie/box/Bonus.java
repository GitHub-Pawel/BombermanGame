package bomberman.entitie.box;

import bomberman.component.Block;
import javax.swing.*;

public class Bonus extends Block {
    private boolean isSet;          // Specifies whether bonus is hidden in case
    private boolean addBomb;
    private boolean increaseFire;


    /********************************************************************
     *                         Constructor                              *
     ********************************************************************/

    public Bonus(){
        this.isSet = false;
        this.addBomb = false;
        this.increaseFire  = false;
    }

    /********************************************************************
     *                        Getters & Setters                         *
     ********************************************************************/

    public boolean isSet() {
        return isSet;
    }

    public boolean isAddBomb() {
        return addBomb;
    }
    public void setAddBomb() {
        this.addBomb = true;
        this.isSet = true;
        this.setImage(new ImageIcon("images\\addBomb.jpg"));
    }

    public boolean isIncreaseFire() {
        return increaseFire;
    }
    public void setIncreaseFire() {
        this.increaseFire = true;
        this.isSet = true;
        this.setImage(new ImageIcon("images\\increaseFire.jpg"));
    }
}
