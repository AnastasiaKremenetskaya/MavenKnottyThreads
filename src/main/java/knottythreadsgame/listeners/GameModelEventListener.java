package knottythreadsgame.listeners;

public interface GameModelEventListener {
    void treadReachedMaxLength();
    void gameFailed();
    void gameCompleted();
}
