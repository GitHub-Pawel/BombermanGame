package Bomberman;

public class Board extends Keyboard {
    protected int[][] table;
    protected int tableLength;
    protected Board boardID;

    public Board() {
        boardID = this;
        tableLength = 19;

        this.table = new int[tableLength][tableLength];

        for (int row = 0; row < tableLength; ++row) {
            for (int column = 0; column < tableLength; ++column) {
                if (row == 0 || row == tableLength - 1) {
                    table[row][column] = 2;
                } else if (row % 2 == 0 && column % 2 == 0) {
                    table[row][column] = 2;
                } else if (row % 2 != 0 && (column == 0 || column == tableLength - 1)) {
                    table[row][column] = 2;
                } else {
                    table[row][column] = 0;
                }
            }
        }

        table[1][1] = 1;
    }

    public void view() {
        for (int row = 0; row < tableLength; ++row) {
            for (int column = 0; column < tableLength; ++column) {
                if (table[row][column] == 0){
                    System.out.print(" ");
                } else if(table[row][column] == 1){
                    System.out.print("*");
                } else if(table[row][column] == 2){
                    System.out.print("#");
                }
            }
            System.out.println();
        }
    }
}
