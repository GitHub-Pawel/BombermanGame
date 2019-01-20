package bomberman.entitie;

import bomberman.component.Block;
import bomberman.inputOutput.Sound;
import bomberman.observers.BombObserver;

import javax.swing.*;

public class Bomb extends Block implements Runnable{
    private int power;      // Bomb power, determines the bomb explosion range
    private BombObserver bombObserver;
    private byte idOfBomber;

    /********************************************************************
     *                         Constructor                              *
     * @param row                                                       *
     * @param column                                                    *
     ********************************************************************/

    public Bomb(int row, int column) {
        super(row, column);
        this.setImage(new ImageIcon("images\\playerWithBomb.jpg"));
        this.power = 1;
    }

    /********************************************************************
     *                             Methods                              *
     ********************************************************************/

    public void explode(){
        Sound.play("sounds\\boom.wav");
        bombObserver.burn(this);
    }

    public void vanish() { bombObserver.fade(this);}


    @Override
    public void run(){      // Bomb explosion process
        try {
            Thread.sleep(2000);
            explode();
            Thread.sleep(1000);
            vanish();
        } catch (InterruptedException e) {
        }
    }

    public void subscribe(BombObserver o){
        bombObserver = o;
    }

    public void unsubscribe(BombObserver o){
        if (bombObserver == o){
            bombObserver = null;
        }
    }

    /********************************************************************
     *                        Getters & Setters                         *
     ********************************************************************/

    public byte getIdOfBomber() {
        return idOfBomber;
    }
    public void setIdOfBomber(byte idOfBomber) {
        this.idOfBomber = idOfBomber;
    }

    public int getPower() {
        return power;
    }
    public void setPower(int power) {
        this.power = power;
    }

}
