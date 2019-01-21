package bomberman.component;

import bomberman.entitie.basic.Floor;
import bomberman.entitie.basic.Wall;

public class Board {

    private Block[][] table;        // game field
    private int tableLength;        // size of game field
    private final Floor floor;
    private final Wall wall;


    /********************************************************************
     *                         Constructor                              *
     * @param fieldSize                                                 *
     ********************************************************************/

    public Board(int fieldSize) {   // Creates an empty game field
        this.table = new Block[fieldSize][fieldSize];
        this.tableLength = fieldSize;
        this.floor = Floor.getInstance();
        this.wall = Wall.getInstance();

        for (int row = 0; row < tableLength; ++row) {
            for (int column = 0; column < tableLength; ++column) {
                if (row == 0 || row == tableLength - 1) {
                    table[row][column] = wall;
                } else if (row % 2 == 0 && column % 2 == 0) {
                    table[row][column] = wall;
                } else if (row % 2 != 0 && (column == 0 || column == tableLength - 1)) {
                    table[row][column] = wall;
                } else {
                    table[row][column] = floor;
                }
            }
        }
    }

    /********************************************************************
     *                      Getters & Setters                           *
     ********************************************************************/

    public Block[][] getTable() { return table; }

    public int getTableLength() { return tableLength; }

    public Floor getFloor() { return floor; }

}