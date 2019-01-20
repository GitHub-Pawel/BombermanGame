package bomberman.entitie.box;

import bomberman.component.Block;

import javax.swing.*;

public class Case extends Block {

    private boolean isDestroyed;
    private Bonus bonus;            // All cases has bonuses but by default bonus is null
    private FinalGate finalGate;    // All cases has final gate but only one of them has gate which is not null

    /********************************************************************
     *                         Constructor                              *
     ********************************************************************/

    public Case() {
        super();
        this.setImage(new ImageIcon("images\\case.jpg"));
        bonus = new Bonus();
    }

    /********************************************************************
     *                        Getters & Setters                         *
     ********************************************************************/

    public void setDestroyed(boolean destroyed) {
        this.isDestroyed = destroyed;
        if (this.isDestroyed == true){
            this.setImage(new ImageIcon("images\\caseWithFire.jpg"));
        } else{
            this.setImage(new ImageIcon("images\\case.jpg"));
        }
    }

    public Bonus getBonus(){
        return this.bonus;
    }
    public void setBonus(int nr){
        if (nr == 0)
            this.bonus.setAddBomb();
        else
            this.bonus.setIncreaseFire();
    }

    public FinalGate getFinalGate() {
        return finalGate;
    }
    public void setFinalGate(FinalGate finalGate) {
        this.finalGate = finalGate;
    }
}
