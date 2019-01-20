package bomberman.inputOutput;

import bomberman.exception.WrongKeyException;
import bomberman.observers.KeyboardObserver;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard extends JFrame implements KeyListener {

    private boolean[] keys = new boolean[KeyEvent.VK_X + 1];    // KeyEvent.VK_X has the highest value from all of keys
    private KeyboardObserver keyboardObserver;
    private Keyboard keyboardID;
    private byte secondId = 1;          // In singleplayer mode it becomes 0 and allows player1 to using keys assigned to player2 as well


    /********************************************************************
     *                         Constructor                              *
     ********************************************************************/

    public Keyboard(){
        keyboardID = this;
    }

    /********************************************************************
     *                  Methods of reading the keys                     *
     ********************************************************************/

    public void checkForException(KeyEvent er) throws WrongKeyException {       // When using other keys than established
        if (!(er.getKeyCode() == KeyEvent.VK_UP || er.getKeyCode() == KeyEvent.VK_W ||
                er.getKeyCode() == KeyEvent.VK_DOWN || er.getKeyCode() == KeyEvent.VK_S ||
                er.getKeyCode() == KeyEvent.VK_LEFT || er.getKeyCode() == KeyEvent.VK_A ||
                er.getKeyCode() == KeyEvent.VK_RIGHT || er.getKeyCode() == KeyEvent.VK_D ||
                er.getKeyCode() == KeyEvent.VK_ENTER || er.getKeyCode() == KeyEvent.VK_SPACE))
                    throw new WrongKeyException();
    }


    @Override
    public void keyPressed(KeyEvent e) {        // When the key goes down
        try {
            checkForException(e);
            if (keys[e.getKeyCode()] == false) {
                keys[e.getKeyCode()] = true;
                this.update(e.getKeyCode());
            }
        } catch (WrongKeyException wke){}
    }

    @Override
    public void keyReleased(KeyEvent e) {       // When the key comes up
        try {
            checkForException(e);
            keys[e.getKeyCode()] = false;
        } catch (WrongKeyException wke){}
    }


    @Override
    public void keyTyped(KeyEvent e) {
        // When the unicode character represented by this key
        // is sent by the keyboard to system input.
    }

    public void subscribe(KeyboardObserver o){
        keyboardObserver = o;
    }

    public void unsubscribe(KeyboardObserver o){
        if (keyboardObserver == o){
            keyboardObserver = null;
        }
    }

    public void update(int key) {       // Execution the methods assigned to keys
        switch (key){
            case KeyEvent.VK_W:
                keyboardObserver.moveUp((byte) 0);
                break;
            case KeyEvent.VK_S:
                keyboardObserver.moveDown((byte) 0);
                break;
            case KeyEvent.VK_A:
                keyboardObserver.moveLeft((byte) 0);
                break;
            case KeyEvent.VK_D:
                keyboardObserver.moveRight((byte) 0);
                break;
            case KeyEvent.VK_SPACE:
                keyboardObserver.plantBomb((byte) 0);
                break;
            case KeyEvent.VK_UP:
                keyboardObserver.moveUp(secondId);
                break;
            case KeyEvent.VK_DOWN:
                keyboardObserver.moveDown(secondId);
                break;
            case KeyEvent.VK_LEFT:
                keyboardObserver.moveLeft(secondId);
                break;
            case KeyEvent.VK_RIGHT:
                keyboardObserver.moveRight(secondId);
                break;
            case KeyEvent.VK_ENTER:
                keyboardObserver.plantBomb(secondId);
                break;
        }
    }

    /********************************************************************
     *                      Getters & Setters                           *
     ********************************************************************/

    public Keyboard getKeyboardID() {
        return keyboardID;
    }

    public void setSecondId(byte secondId) {
        this.secondId = secondId;
    }
}

