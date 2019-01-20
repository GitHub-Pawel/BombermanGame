package bomberman.component;

import javax.swing.*;

public abstract class Block {

    private int column;         // x
    private int row;            // y
    private ImageIcon image;    // block image


    /********************************************************************
     *                         Constructors                             *
     ********************************************************************/

    public Block(){
    }

    public Block(int row, int column){
        this.column = column;
        this.row = row;
    }

    /********************************************************************
     *                      Getters & Setters                           *
     ********************************************************************/

    public int getColumn() { return column; }
    public void setColumn(int column) { this.column = column; }

    public int getRow() { return row; }
    public void setRow(int row) { this.row = row; }

    public ImageIcon getImage() { return image;}
    public void setImage(ImageIcon image) { this.image = image; }

}
