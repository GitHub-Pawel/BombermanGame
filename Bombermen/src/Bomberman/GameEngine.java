package Bomberman;

public class GameEngine extends Board implements Observer{
    protected SimpleFrame frame;
    protected Player player;
    protected GameEngine gameEngineID;

    public GameEngine(){
        super();
        frame = new SimpleFrame();
        frame.addKeyListener(keyboardID);
        player = new Player();
        gameEngineID = this;
        subscribe(gameEngineID);
    }

    public void moveUp(){
        if (table[player.getRow()-1][player.getColumn()] == 0) {
            table[player.getRow() - 1][player.getColumn()] = 1;
            table[player.getRow()][player.getColumn()] = 0;
            player.setRow(player.getRow() - 1);
        }





        System.out.print(player.getRow());
        System.out.print("  ");
        System.out.print(player.getColumn());
        System.out.println();
    }

    public void moveDown(){
        if (table[player.getRow()+1][player.getColumn()] == 0) {
            table[player.getRow() + 1][player.getColumn()] = 1;
            table[player.getRow()][player.getColumn()] = 0;
            player.setRow(player.getRow() + 1);
        }





        System.out.print(player.getRow());
        System.out.print("  ");
        System.out.print(player.getColumn());
        System.out.println();
    }

    public void moveLeft(){
        if (table[player.getRow()][player.getColumn()-1] == 0){
            table[player.getRow()][player.getColumn()-1] = 1;
            table[player.getRow()][player.getColumn()] = 0;
            player.setColumn(player.getColumn()-1);
        }






        System.out.print(player.getRow());
        System.out.print("  ");
        System.out.print(player.getColumn());
        System.out.println();
    }

    public void moveRight(){
        if (table[player.getRow()][player.getColumn()+1] == 0){
            table[player.getRow()][player.getColumn()+1] = 1;
            table[player.getRow()][player.getColumn()] = 0;
            player.setColumn(player.getColumn()+1);
        }






        System.out.print(player.getRow());
        System.out.print("  ");
        System.out.print(player.getColumn());
        System.out.println();
    }
}

