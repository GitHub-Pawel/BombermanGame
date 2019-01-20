package bomberman.tests;

import bomberman.entitie.Bomb;
import bomberman.entitie.Fire;
import bomberman.entitie.basic.Floor;
import bomberman.entitie.Player;
import bomberman.GameEngine;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameEngineTest {

    @Test
    public void moving(){       // Testing player movement
        GameEngine game = new GameEngine(1, 15, 1);

        assertTrue(game.getBoard().getTable()[1][1] instanceof Player);
        assertFalse(game.getBoard().getTable()[1][2] instanceof Player);

        game.moveRight((byte) 0);
        assertTrue(game.getBoard().getTable()[1][2] instanceof Player);
        assertFalse(game.getBoard().getTable()[1][1] instanceof Player);

        game.moveLeft((byte) 0);
        assertTrue(game.getBoard().getTable()[1][1] instanceof Player);
        assertFalse(game.getBoard().getTable()[1][2] instanceof Player);

        game.moveDown((byte) 0);
        assertTrue(game.getBoard().getTable()[2][1] instanceof Player);
        assertFalse(game.getBoard().getTable()[1][1] instanceof Player);

        game.moveUp((byte) 0);
        assertTrue(game.getBoard().getTable()[1][1] instanceof Player);
        assertFalse(game.getBoard().getTable()[2][1] instanceof Player);

    }

    @Test
    public void plantBomb(){       // Testing whether bomb is planted correctly
        GameEngine game = new GameEngine(1, 15, 1);

        game.plantBomb((byte) 0);
        assertTrue(game.getBoard().getTable()[1][1] instanceof Bomb);
    }

    @Test
    public void setBurnedFaded(){   // Testing the appearance and disappearance of fire
        GameEngine game = new GameEngine(1, 15, 1);

        game.setBurned(1, 2);
        assertTrue(game.getBoard().getTable()[1][2] instanceof Fire);
        game.setBurned(2, 1);
        assertTrue(game.getBoard().getTable()[2][1] instanceof Fire);

        game.setFaded(1, 2);
        assertTrue(game.getBoard().getTable()[1][2] instanceof Floor);
        game.setFaded(2, 1);
        assertTrue(game.getBoard().getTable()[2][1] instanceof Floor);
    }

    @Test
    public void burnFade(){         // Testing the bomb explosion and fire disappearance
        GameEngine game = new GameEngine(1, 15, 1);
        game.plantBomb((byte) 0);
        Bomb bomb = (Bomb) game.getBoard().getTable()[1][1];

        game.burn(bomb);
        assertTrue(game.getBoard().getTable()[1][1] instanceof Fire);
        assertTrue(game.getBoard().getTable()[1][2] instanceof Fire);
        assertTrue(game.getBoard().getTable()[2][1] instanceof Fire);

        game.fade(bomb);
        assertTrue(game.getBoard().getTable()[1][1] instanceof Floor);
        assertTrue(game.getBoard().getTable()[1][2] instanceof Floor);
        assertTrue(game.getBoard().getTable()[2][1] instanceof Floor);
    }




}