package bomberman.observers;

public interface KeyboardObserver {
    void moveUp(byte id);       // Player movement
    void moveDown(byte id);
    void moveLeft(byte id);
    void moveRight(byte id);
    void plantBomb(byte id);    // Planting bomb by player
}
