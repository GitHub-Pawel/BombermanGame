package Bomberman;

public class PositionOnTheBoard {
    private int row;        // ~y
    private int column;     // ~x

    public PositionOnTheBoard(){
        row = 1;
        column = 1;
    }

    public int getRow() {
        return row;
    }
    public int getColumn() {
        return column;
    }

    public void setRow(int row) {
        this.row = row;
    }
    public void setColumn(int column) {
        this.column = column;
    }
}
