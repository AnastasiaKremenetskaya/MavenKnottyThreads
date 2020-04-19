package knottythreadsgame.model;

public class Thread {
    private Knot firstKnot;
    private Knot secondKnot;

    public Thread(Knot firstKnot, Knot secondKnot) {
        if (firstKnot != secondKnot) {
            this.firstKnot = firstKnot;
            this.secondKnot = secondKnot;
        } else
            throw new IllegalArgumentException("Error: Thread's knots are identical");
    }

    public Knot getFirstKnot() {
        return firstKnot;
    }

    public Knot getSecondKnot() {
        return secondKnot;
    }
}
