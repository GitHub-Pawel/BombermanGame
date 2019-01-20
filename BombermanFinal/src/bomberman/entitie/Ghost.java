package bomberman.entitie;

import bomberman.component.Block;
import bomberman.exception.GhostMovingException;
import bomberman.observers.GhostObserver;
import bomberman.inputOutput.Sound;

import javax.swing.*;
import java.util.Random;

public class Ghost extends Block implements Runnable {
    private int column;
    private int row;
    private GhostObserver ghostObserver;
    private boolean stillAlive;

    /********************************************************************
     *                         Constructors                             *
     ********************************************************************/

    public Ghost(){
        super();
        this.setImage(new ImageIcon("images\\ghost.jpg"));
    }
    public Ghost(int _row, int _column){
        this();
        this.column = _column;
        this.row = _row;
        this.stillAlive = true;
    }

    /********************************************************************
     *                             Methods                              *
     ********************************************************************/

    @Override
    public void run(){      // Ghosts movement
        Random random = new Random();
        int rowAdd, columnAdd;
        while (this.stillAlive == true){
            this.setImage(new ImageIcon("images\\Ghost.jpg"));
            try{
                do {                                                        // To refresh directions once at least
                    rowAdd = random.nextInt(3)-1;
                    columnAdd = random.nextInt(3)-1;
                }while (rowAdd == columnAdd || rowAdd == -columnAdd);       // Not to ghosts move diagonally
                try {
                    ghostObserver.fly(this.row, this.column, rowAdd, columnAdd);
                    Thread.sleep(500 + random.nextInt(100)-50);
                } catch (GhostMovingException e){}
            } catch (InterruptedException e){}
        }
    }

    public void subscribe(GhostObserver o){
        ghostObserver = o;
    }

    public void unsubscribe(GhostObserver o){
        if (ghostObserver == o){
            ghostObserver = null;
        }
    }

    /********************************************************************
     *                        Getters & Setters                         *
     ********************************************************************/

    @Override
    public int getColumn() {
        return column;
    }
    @Override
    public void setColumn(int column) {
        this.column = column;
    }

    @Override
    public int getRow() {
        return row;
    }
    @Override
    public void setRow(int row) {
        this.row = row;
    }

    public void setStillAlive(boolean stillAlive) {
        if (stillAlive == false){
            Sound.play("sounds\\killedGhost.wav");
        }
        this.stillAlive = stillAlive;
    }
}
