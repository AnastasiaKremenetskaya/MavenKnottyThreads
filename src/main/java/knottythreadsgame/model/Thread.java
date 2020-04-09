package knottythreadsgame.model;

public class Thread {
    private Knot firstKnot;
    private Knot secondKnot;

    public Thread(Knot firstKnot, Knot secondKnot) {
        this.firstKnot = firstKnot;
        this.secondKnot = secondKnot;
    }

    public Knot getFirstKnot() {
        return firstKnot;
    }

    public Knot getSecondKnot() {
        return secondKnot;
    }
}
