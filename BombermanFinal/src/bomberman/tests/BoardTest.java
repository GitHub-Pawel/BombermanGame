package bomberman.tests;

import bomberman.component.Board;
import bomberman.entitie.basic.Floor;
import bomberman.entitie.basic.Wall;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    public void board(){        // Checking whether initialized board is correct
        Board board = new Board(15);

        for (int row = 0; row < board.getTableLength(); ++row) {
            for (int column = 0; column < board.getTableLength(); ++column) {
                if (row == 0 || row == board.getTableLength() - 1) {
                    assertTrue(board.getTable()[row][column] instanceof Wall);
                } else if (row % 2 == 0 && column % 2 == 0) {
                    assertTrue(board.getTable()[row][column] instanceof Wall);
                } else if (row % 2 != 0 && (column == 0 || column == board.getTableLength() - 1)) {
                    assertTrue(board.getTable()[row][column] instanceof Wall);
                } else {
                    assertTrue(board.getTable()[row][column] instanceof Floor);
                }
            }
        }

    }
    

}