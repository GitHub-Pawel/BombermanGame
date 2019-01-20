package bomberman.tests;

import bomberman.entitie.Bomb;
import bomberman.entitie.Player;
import bomberman.GameEngine;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

class KeyboardTest {

    @Test
    public void update(){       // Checking if the use of keys is correct
        GameEngine game = new GameEngine(2, 15, 1);

        assertTrue(game.getBoard().getTable()[1][1] instanceof Player);
        assertFalse(game.getBoard().getTable()[1][2] instanceof Player);

        game.getKeyboard().update(KeyEvent.VK_D);
        assertTrue(game.getBoard().getTable()[1][2] instanceof Player);
        assertFalse(game.getBoard().getTable()[1][1] instanceof Player);

        game.getKeyboard().update(KeyEvent.VK_A);
        assertTrue(game.getBoard().getTable()[1][1] instanceof Player);
        assertFalse(game.getBoard().getTable()[1][2] instanceof Player);

        game.getKeyboard().update(KeyEvent.VK_S);
        assertTrue(game.getBoard().getTable()[2][1] instanceof Player);
        assertFalse(game.getBoard().getTable()[1][1] instanceof Player);

        game.getKeyboard().update(KeyEvent.VK_W);
        assertTrue(game.getBoard().getTable()[1][1] instanceof Player);
        assertFalse(game.getBoard().getTable()[2][1] instanceof Player);

        game.getKeyboard().update(KeyEvent.VK_SPACE);
        assertTrue(game.getBoard().getTable()[1][1] instanceof Bomb);

        //second player

        assertTrue(game.getBoard().getTable()[game.getBoard().getTableLength() - 2][game.getBoard().getTableLength() - 2] instanceof Player);
        assertFalse(game.getBoard().getTable()[game.getBoard().getTableLength() - 3][game.getBoard().getTableLength() - 2] instanceof Player);

        game.getKeyboard().update(KeyEvent.VK_LEFT);
        assertTrue(game.getBoard().getTable()[game.getBoard().getTableLength() - 2][game.getBoard().getTableLength() - 3] instanceof Player);
        assertFalse(game.getBoard().getTable()[game.getBoard().getTableLength() - 2][game.getBoard().getTableLength() - 2] instanceof Player);

        game.getKeyboard().update(KeyEvent.VK_RIGHT);
        assertTrue(game.getBoard().getTable()[game.getBoard().getTableLength() - 2][game.getBoard().getTableLength() - 2] instanceof Player);
        assertFalse(game.getBoard().getTable()[game.getBoard().getTableLength() - 2][game.getBoard().getTableLength() - 3] instanceof Player);

        game.getKeyboard().update(KeyEvent.VK_UP);
        assertTrue(game.getBoard().getTable()[game.getBoard().getTableLength() - 3][game.getBoard().getTableLength() - 2] instanceof Player);
        assertFalse(game.getBoard().getTable()[game.getBoard().getTableLength() - 2][game.getBoard().getTableLength() - 2] instanceof Player);

        game.getKeyboard().update(KeyEvent.VK_DOWN);
        assertTrue(game.getBoard().getTable()[game.getBoard().getTableLength() - 2][game.getBoard().getTableLength() - 2] instanceof Player);
        assertFalse(game.getBoard().getTable()[game.getBoard().getTableLength() - 3][game.getBoard().getTableLength() - 2] instanceof Player);

        game.getKeyboard().update(KeyEvent.VK_ENTER);
        assertTrue(game.getBoard().getTable()[game.getBoard().getTableLength() - 2][game.getBoard().getTableLength() - 2] instanceof Bomb);


    }

}