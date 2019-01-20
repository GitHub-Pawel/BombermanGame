package bomberman.observers;

import bomberman.exception.GhostMovingException;

public interface GhostObserver{
    void fly(int row, int Column, int rowAdd, int columnAdd) throws GhostMovingException;  // Ghost movement
}
