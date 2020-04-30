package knottythreadsgame.listeners;

public interface SchemaEventListener {
    void treadReachedMaxLength();
    void treadTeared();
    void noCrossings();
}
