package Bomberman;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard extends JFrame implements KeyListener {

    protected boolean[] keys = new boolean[120];
    protected boolean up, down, left, right, space;
    protected Observer observer;
    protected Keyboard keyboardID;

    public Keyboard(){
        keyboardID = this;
    }

    public void update() {
        up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
        down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
        left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
        right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
        space = keys[KeyEvent.VK_SPACE] || keys[KeyEvent.VK_X];
        changePosition();
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if (keys[e.getKeyCode()] == false) {
            keys[e.getKeyCode()] = true;
            this.update();
        }
        // when the key goes down
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
        this.update();
        // when the key comes up
    }


    @Override
    public void keyTyped(KeyEvent e) {
        // when the unicode character represented by this key
        // is sent by the keyboard to system input.
    }

    public void subscribe(Observer o){
        observer = o;
    }

    public void unsubscribe(Observer o){
        if (observer == o){
            observer = null;
        }
    }

    public void changePosition(){
        if(up)
            observer.moveUp();
        else if(down)
            observer.moveDown();
        else if(left)
            observer.moveLeft();
        else if(right)
            observer.moveRight();
    }
}