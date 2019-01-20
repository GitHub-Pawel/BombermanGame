package bomberman.observers;

import bomberman.entitie.Bomb;

public interface BombObserver {
    void burn(Bomb _bomb);   // Fire appears
    void fade(Bomb _bomb);   // Fire disappears
}
