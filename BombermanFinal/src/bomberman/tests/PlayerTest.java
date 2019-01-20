package bomberman.tests;

import bomberman.entitie.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    public void isStillAlive(){         // Checking state of player life
        Player player = new Player(1);

        assertTrue(player.isStillAlive());
        player.setStillAlive(false);
        assertFalse(player.isStillAlive());
    }

}