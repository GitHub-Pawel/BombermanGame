package bomberman;

import bomberman.component.*;
import bomberman.entitie.*;
import bomberman.entitie.basic.*;
import bomberman.entitie.box.*;
import bomberman.exception.GhostMovingException;
import bomberman.gui.*;
import bomberman.inputOutput.*;
import bomberman.observers.*;
import javax.swing.*;
import java.util.Random;

public class GameEngine implements KeyboardObserver, BombObserver, GhostObserver {
    private Board board;
    private BombermanGUI frame;
    private GameEndGUI gameEndGUI;
    private ImageIcon gameEndImage;
    private Keyboard keyboard;
    private Player [] players;
    private FinalGate finalGate;
    private volatile Thread[] ghostThreads;  // Table of ghost threads
    private volatile Thread[] bombThreads;   // Table of bomb threads
    private int bombCount;                   // We have to remember last number of bomb, to appoint a new thread
    private int gameMode;                    // It can only take values 1 (Singleplayer), 2 (Multiplayer) or 3 (Cooperative)
    private int numberOfGhosts;
    private int GhostOnTheBoard;
    private int numberOfPlayers;
    private boolean victory;
    private boolean defeat;
    private boolean player1Win;
    private boolean player2Win;


    /********************************************************************
     *                         Constructor                              *
     * @param gameMode                                                  *
     * @param fieldSize                                                 *
     * @param numberOfGhosts                                            *
     ********************************************************************/

    public GameEngine(int gameMode, int fieldSize, int numberOfGhosts){
        this.keyboard = new Keyboard();
        this.gameMode = gameMode;
        this.gameEndGUI = null;
        this.board = new Board(fieldSize);

        if (gameMode == 1)
            this.numberOfPlayers = 1;
        else if (gameMode == 2 || gameMode == 3)
            this.numberOfPlayers = 2;

        this.players = new Player[this.numberOfPlayers];
        this.players[0] = new Player(1);
        if (this.numberOfPlayers == 2) this.players[1] = new Player(this.board.getTableLength()-2);

        if (gameMode == 1){                             // Singleplayer mode
            this.keyboard.setSecondId((byte) 0);        // It allows using keys assigned to player2 as well
            board.getTable()[1][1] = players[0];
        } else if (gameMode == 2 || gameMode == 3){     // Multiplayer or Cooperative mode
            this.board.getTable()[1][1] = players[0];
            this.board.getTable()[this.board.getTableLength()-2][this.board.getTableLength()-2] = players[1];
        }

        this.insertCases();
        this.raffleBonus();
        this.frame = new BombermanGUI(board);
        this.frame.addKeyListener(this.keyboard.getKeyboardID());
        if (gameMode == 1 || gameMode == 3){                    // Inserting ghosts and gate only in Singleplayer or Cooperative mode
            this.numberOfGhosts = numberOfGhosts;               // Default number of ghosts is 5
            this.GhostOnTheBoard = this.numberOfGhosts;
            this.ghostThreads = new Thread[numberOfGhosts];
            insertGhosts();
            raffleGate();
        }
        this.keyboard.subscribe(this);
        this.bombThreads = new Thread[200];        // 200 is maximum number of threads
        this.bombCount = 0;
        this.victory = false;
        this.defeat = false;
        this.player1Win = false;
        this.player2Win = false;
        this.gameEndImage = null;
    }


    /********************************************************************
     *                     Game Initialization                          *
     *******************************************************************/

    public void insertCases(){              // Cases randomization, by default cases covers 25% of the game field
        Random random = new Random();
        int row, column;
        for (int i=0; i<(board.getTableLength()*board.getTableLength())/4; ++i){
            row = 0;
            column = 0;
            while (!(this.board.getTable()[row][column] instanceof Floor && row*column != 2 && row*column != (board.getTableLength()-2)*(board.getTableLength()-3))){
                row = random.nextInt(board.getTableLength()-1);
                column = random.nextInt(board.getTableLength()-1);
            }
            this.board.getTable()[row][column] = new Case();
        }
    }

    public void raffleBonus(){                            // Bonuses randomization, by default bonuses are hidden in 25% of cases
        Random random = new Random();
        int row, column, noOfBonus;
        for (int i=0; i<(board.getTableLength()*board.getTableLength())/16; ++i) {
            row = 0;
            column = 0;
            while (!(this.board.getTable()[row][column] instanceof Case)) {
                row = random.nextInt(board.getTableLength() - 1);
                column = random.nextInt(board.getTableLength() - 1);
            }
            noOfBonus = random.nextInt(2);
            ((Case) this.board.getTable()[row][column]).setBonus(noOfBonus);
        }
    }

    public void insertGhosts(){                           // Ghosts randomization and adding to separate threads
        Random random = new Random();
        int row, column;
        for (int i=0; i<numberOfGhosts; ++i){
            row = 0;
            column = 0;
            while (!(this.board.getTable()[row][column] instanceof Floor && row != 1  && column != 1 )){
                row = random.nextInt(board.getTableLength()-2);
                column = random.nextInt(board.getTableLength()-2);
            }
            Ghost newGhost = new Ghost(row, column);
            newGhost.subscribe(this);
            this.board.getTable()[row][column] = newGhost;
            ghostThreads[i] = new Thread(newGhost);
            ghostThreads[i].start();
        }
    }

    void raffleGate(){      // Drawing a final gate for one of the cases which does not contain a bonus
        Random random = new Random();
        int row = 0;
        int column = 0;
        while (!(this.board.getTable()[row][column] instanceof Case && !((Case) this.board.getTable()[row][column]).getBonus().isSet())){
            row = random.nextInt(board.getTableLength()-1);
            column = random.nextInt(board.getTableLength()-1);
        }
        this.finalGate = FinalGate.getInstance();
        ((Case) this.board.getTable()[row][column]).setFinalGate(finalGate);
    }


    /********************************************************************
     *                  Update Player Position                          *
     ********************************************************************/

    public void changePlayerPosition (byte id, int y_startup, int x_startup, int y_changed, int x_changed) {
        this.board.getTable()[y_changed][x_changed] = players[id];
        players[id].setRow(y_changed);
        players[id].setColumn(x_changed);

        if (this.board.getTable()[y_startup][x_startup] instanceof Player) {
            this.board.getTable()[y_startup][x_startup] = board.getFloor();
                    /* If firstly player drops bomb and next preses something else
                    then we mustn't change this Bomb into the Floor */
        } else {
            try {
                if (y_changed != players[id].lastoneBomb.getRow() || x_changed != players[id].lastoneBomb.getColumn()) {
                    players[id].lastoneBomb.setImage(new ImageIcon("images\\bomb.jpg"));                        // Player changes position after planting bomb
                }
            } catch (NullPointerException e) {
            }
        }
        frame.screenReload();
    }

    public void move(byte id, int add2Row, int add2Column){
        final int y_startup = players[id].getRow();
        final int x_startup = players[id].getColumn();
        final int y_changed = y_startup + add2Row;
        final int x_changed = x_startup + add2Column;


        if (players[id].isStillAlive() == true) {
            if (this.board.getTable()[y_changed][x_changed] instanceof Floor) {
                changePlayerPosition(id, y_startup, x_startup, y_changed, x_changed);               // Player moves into floor
                Sound.play("sounds\\move.wav");
            } else if (this.board.getTable()[y_changed][x_changed] instanceof Bonus) {
                if (((Bonus) this.board.getTable()[y_changed][x_changed]).isAddBomb()) {
                    players[id].setLimitOfBombs((byte) (players[id].getLimitOfBombs() + 1));        // Collecting addBomb bonus
                } else if (((Bonus) this.board.getTable()[y_changed][x_changed]).isIncreaseFire()) {
                    players[id].setPower(players[id].getPower() + 1);                               // Collecting increaseFire bonus
                }
                changePlayerPosition(id, y_startup, x_startup, y_changed, x_changed);               // Player moves into bonus
                Sound.play("sounds\\bonus.wav");
            } else if (this.board.getTable()[y_changed][x_changed] instanceof Fire) {
                    players[id].setStillAlive(false);                                               // Killing player when moves into fire
                changePlayerPosition(id, y_startup, x_startup, y_changed, x_changed);
                checkPlayerWin();
                checkDefeat();
            } else if (this.board.getTable()[y_changed][x_changed] instanceof FinalGate &&
                    ((FinalGate) this.board.getTable()[y_changed][x_changed]).isOpenGate()) {
                changePlayerPosition(id, y_startup, x_startup, y_changed, x_changed);
                this.board.getTable()[y_changed][x_changed].setImage(new ImageIcon("images\\winnerPlayer.jpg"));
                frame.screenReload();
                Sound.play("sounds\\win.wav");
                this.victory = true;                                                                // Player enters the final gate
                gameEnd();
            }
        }
    }


    @Override
    public void moveUp(byte id){
        move(id, -1, 0);
    }

    @Override
    public void moveDown(byte id){
        move(id, 1, 0);
    }

    @Override
    public void moveLeft(byte id){
        move(id, 0, -1);
    }

    @Override
    public void moveRight(byte id){
        move(id, 0 ,1);
    }

    /********************************************************************
     *                  Plant The Bomb                                  *
     ********************************************************************/

    @Override
    public void plantBomb(byte id){
        if(players[id].isStillAlive() == true && players[id].getPlantedBombs() < players[id].getLimitOfBombs()) {
            players[id].setPlantedBombs((byte)(players[id].getPlantedBombs() + 1));   // Increasing the number of actually planted bombs by player
            Bomb newBomb = new Bomb(players[id].getRow(), players[id].getColumn());   // Call for new instance of Bomb class
            newBomb.setIdOfBomber(id);
            newBomb.setPower(players[id].getPower());
            players[id].lastoneBomb = newBomb;
            newBomb.subscribe(this);                    // Connecting a new thread of bomb with main thread

            bombThreads[bombCount] = new Thread(newBomb);   // Definition of the new thread
            bombThreads[bombCount].start();                 // Starting the new thread
            this.bombCount++;

            this.board.getTable()[players[id].getRow()][players[id].getColumn()] = newBomb;

            frame.screenReload();
        }
    }

    public void setBurned(int row, int column) {                              // Setting fire
        if (!(this.board.getTable()[row][column] instanceof Wall || this.board.getTable()[row][column] instanceof FinalGate)) {  // Can not set fire to wall and final gate
            if (this.board.getTable()[row][column] instanceof Case) {
                ((Case) this.board.getTable()[row][column]).setDestroyed(true);            // Setting fire to cases
            }else if (this.board.getTable()[row][column] instanceof Player) {
                ((Player) this.board.getTable()[row][column]).setStillAlive(false);        // Setting fire to player and killing
                this.checkDefeat();
                this.checkPlayerWin();
            }else if (this.board.getTable()[row][column] instanceof Ghost){
                ((Ghost) this.board.getTable()[row][column]).setStillAlive(false);         // Setting fore to ghost and killing
                this.board.getTable()[row][column].setImage(new ImageIcon("images\\burnedGhost.jpg"));
            }else{
                this.board.getTable()[row][column] = new Fire();
            }
        }

        for (int i=0; i < this.numberOfPlayers; ++i){
            if (players[i].getRow() == row && players[i].getColumn() == column) {
                players[i].setStillAlive(false);
                this.checkDefeat();
                this.checkPlayerWin();
                this.board.getTable()[row][column].setImage(new ImageIcon("images\\burnedPlayer.jpg")); // When player had planted the bomb and did not escaped
            }
        }
    }

    @Override
    public void burn(Bomb bomb){
        int column = bomb.getColumn();
        int row = bomb.getRow();
        int power = bomb.getPower();

        setBurned(row, column);        // Burning the block which bomb was planted on

        // Exploding the bomb in four directions

        for(int i = 1; i <= power; i++) {                               // Bomb exploding range depends on the power
            if (column + i > board.getTableLength() - 1) break;         // Not to cross the boundaries of the board
            Block tmp = board.getTable()[row][column + i];              // Reference to the block
            setBurned(row, column + i);                          // Burning the neighboring block
            if(!(tmp instanceof Floor || tmp instanceof Bonus)) break;  // Not to cross burned object and burn the next
        }
        for(int i = 1; i <= power; i++) {                               // Second direction
            if (column - i < 0) break;
            Block tmp = board.getTable()[row][column - i];
            setBurned(row, column - i);
            if(!(tmp instanceof Floor || tmp instanceof Bonus)) break;
        }
        for(int i = 1; i <= power; i++) {                               // Third direction
            if (row + i > board.getTableLength() - 1) break;
            Block tmp = board.getTable()[row + i][column];
            setBurned(row + i, column);
            if(!(tmp instanceof Floor || tmp instanceof Bonus)) break;
        }
        for(int i = 1; i <= power; i++) {                               // Fourth direction
            if (row - i < 0) break;
            Block tmp = board.getTable()[row - i][column];
            setBurned(row - i, column);
            if(!(tmp instanceof Floor || tmp instanceof Bonus)) break;
        }

        frame.screenReload();
    }


    public void setFaded(int row, int column){                                                  // The fire disappears after explosion
        if (this.board.getTable()[row][column] instanceof Fire){
            this.board.getTable()[row][column] = this.board.getFloor();                         // Deleting the fire
        } else if (this.board.getTable()[row][column] instanceof Case){
            Bonus tmpBonus = ((Case) this.board.getTable()[row][column]).getBonus();
            if (tmpBonus.isSet() == true) {                                                     // Checking if case contains a bonus
                this.board.getTable()[row][column] = tmpBonus;                                  // Setting the hidden bonus
            }else if (((Case) this.board.getTable()[row][column]).getFinalGate() != null){      // Checking whether final gate is hidden
                this.board.getTable()[row][column] = ((Case) this.board.getTable()[row][column]).getFinalGate();
            } else {                                                                   // Setting final gate
                this.board.getTable()[row][column] = this.board.getFloor();            // Deleting the burned case
            }
        }else if (this.board.getTable()[row][column] instanceof Player){
            this.board.getTable()[row][column] = this.board.getFloor();                // Killing the player
        }else if (this.board.getTable()[row][column] instanceof Ghost){
            this.board.getTable()[row][column] = this.board.getFloor();                // Killing the ghost
            this.GhostOnTheBoard--;                                       // Decreasing the quantity of ghosts on the board
            if (this.GhostOnTheBoard == 0){
                this.finalGate.openGate();                                // Opening the final gate when the last ghost is killed
            }
        }
        gameEnd();
    }

    @Override
    public void fade(Bomb bomb) {
        int column = bomb.getColumn();
        int row = bomb.getRow();
        int power = bomb.getPower();
        players[bomb.getIdOfBomber()].setPlantedBombs((byte) (players[bomb.getIdOfBomber()].getPlantedBombs() - 1));   // Decreasing the number of actually planted bombs by player

        setFaded(row, column);      // Removing the fire on the block where the bomb was planted on

        // Removing fire in four directions

        for(int i = 1; i <= power; i++) {                           // Fire range depends on the bomb power
            if (column + i > board.getTableLength() - 1) break;     // Not to cross the boundaries of the board
            Block tmp = board.getTable()[row][column + i];          // Reference to the block
            setFaded(row, column + i);                       // Removing the fire
            if(!(tmp instanceof Fire)) break;                       // Not to cross burned object and remove the next
        }
        for(int i = 1; i <= power; i++) {
            if (column - i < 0) break;
            Block tmp = board.getTable()[row][column - i];
            setFaded(row,column - i);
            if(!(tmp instanceof Fire)) break;
        }
        for(int i = 1; i <= power; i++) {
            if (row + i > board.getTableLength() - 1) break;
            Block tmp = board.getTable()[row + i][column];
            setFaded(row + i, column);
            if(!(tmp instanceof Fire)) break;
        }
        for(int i = 1; i <= power; i++) {
            if (row - i < 0) break;
            Block tmp = board.getTable()[row - i][column];
            setFaded(row - i, column);
            if(!(tmp instanceof Fire)) break;
        }

        frame.screenReload();
    }



    /********************************************************************
     *                  Players State Update                            *
     ********************************************************************/

    public void checkPlayerWin (){      // Checking which player wins in Multiplayer game mode
        if (gameMode == 2) {
            if (players[0].isStillAlive() == false){
                this.player2Win = true;
                Sound.play("sounds\\win.wav");
            }
            if (players[1].isStillAlive() == false){
                this.player1Win = true;
                Sound.play("sounds\\win.wav");
            }
        }
    }

    public void checkDefeat(){          // Checking the defeat in Singleplayer and Cooperative game mode
        switch (gameMode){
            case 1:
                if (!players[0].isStillAlive()) {
                    Sound.play("sounds\\gameover.wav");
                    this.defeat = true;
                }
                break;
            case 3:
                if (!players[0].isStillAlive() && !players[1].isStillAlive()) {
                    Sound.play("sounds\\gameover.wav");
                    this.defeat = true;
                }
                break;
        }
    }

    public void gameEnd(){              // Checking end of the game and setting ending
        if (this.victory == true){
            gameEndImage = new ImageIcon("menuImages\\youWinLabel.jpg");
        } else if (this.defeat == true){
            gameEndImage = new ImageIcon("menuImages\\youLoseLabel.jpg");
        } else if (this.player1Win){
            gameEndImage = new ImageIcon("menuImages\\player1Win.jpg");
        } else if (this.player2Win){
            gameEndImage = new ImageIcon("menuImages\\player2Win.jpg");
        }

        if (gameEndImage != null && gameEndGUI == null){    // To display ending frame once
            gameEndGUI = new GameEndGUI(gameEndImage);
        }
    }


    /********************************************************************
     *                  Update Ghost Position                           *
     ********************************************************************/

    public void changeGhostPosition (int row, int column, int rowAdd, int columnAdd) {
        this.board.getTable()[row + rowAdd][column + columnAdd] = this.board.getTable()[row][column];         // Reference to the table
        this.board.getTable()[row][column] = board.getFloor();
        this.board.getTable()[row + rowAdd][column + columnAdd].setRow(row + rowAdd);            // Changing row of ghost position
        this.board.getTable()[row + rowAdd][column + columnAdd].setColumn(column + columnAdd);   // Changing column of ghost position
        frame.screenReload();
    }

    @Override
    public void fly(int row, int column, int rowAdd, int columnAdd) throws GhostMovingException {            // Ghost movement
        if (this.board.getTable()[row + rowAdd][column + columnAdd] instanceof Player) {
            ((Player) this.board.getTable()[row + rowAdd][column + columnAdd]).setStillAlive(false);         // Killing the player when moving on him
            checkDefeat();
            checkPlayerWin();
            this.board.getTable()[row][column].setImage(new ImageIcon("images\\eatenPlayer.jpg"));  // Setting icon of eaten player
            changeGhostPosition(row, column, rowAdd, columnAdd);                      // Changing ghost position
            try {
                Thread.currentThread().sleep(500);                                        // Stopping the ghost thread
            } catch (InterruptedException e) {
            }
            gameEnd();
        } else if (this.board.getTable()[row + rowAdd][column + columnAdd] instanceof Floor ||
                this.board.getTable()[row + rowAdd][column + columnAdd] instanceof Bonus) {
            changeGhostPosition(row, column, rowAdd, columnAdd);                      // Changing ghost position
        } else {
            throw new GhostMovingException();
        }
    }

    /********************************************************************
     *                             Getters                              *
     ********************************************************************/

    public Board getBoard() {
        return board;
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }
}
