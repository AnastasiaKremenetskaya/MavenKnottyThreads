package knottythreadsgame.model;

import org.jetbrains.annotations.NotNull;

public class Thread {
    protected Knot firstKnot;
    protected Knot secondKnot;

    public Thread(@NotNull Knot firstKnot, @NotNull Knot secondKnot) {
        if (firstKnot != secondKnot) {
            this.firstKnot = firstKnot;
            this.secondKnot = secondKnot;
        } else
            throw new IllegalArgumentException("Error: Thread's knots are identical");
    }

    /**
     * Проверяет наличие пересечений с другой нитью
     *
     * @param otherThread
     * @return
     */
    public boolean isCrossing(@NotNull Thread otherThread) {
        if (firstKnot.equals(otherThread.firstKnot) && secondKnot.equals(otherThread.secondKnot))
            return false;

        double o1 = orientation(otherThread.secondKnot.getPosition().getX() - otherThread.firstKnot.getPosition().getX(),
                otherThread.secondKnot.getPosition().getY() - otherThread.firstKnot.getPosition().getY(),
                firstKnot.getPosition().getX() - otherThread.firstKnot.getPosition().getX(),
                firstKnot.getPosition().getY() - otherThread.firstKnot.getPosition().getY());

        double o2 = orientation(otherThread.secondKnot.getPosition().getX() - otherThread.firstKnot.getPosition().getX(),
                otherThread.secondKnot.getPosition().getY() - otherThread.firstKnot.getPosition().getY(),
                secondKnot.getPosition().getX() - otherThread.firstKnot.getPosition().getX(),
                secondKnot.getPosition().getY() - otherThread.firstKnot.getPosition().getY());

        double o3 = orientation(secondKnot.getPosition().getX() - firstKnot.getPosition().getX(),
                secondKnot.getPosition().getY() - firstKnot.getPosition().getY(),
                otherThread.firstKnot.getPosition().getX() - firstKnot.getPosition().getX(),
                otherThread.firstKnot.getPosition().getY() - firstKnot.getPosition().getY());

        double o4 = orientation(secondKnot.getPosition().getX() - firstKnot.getPosition().getX(),
                secondKnot.getPosition().getY() - firstKnot.getPosition().getY(),
                otherThread.secondKnot.getPosition().getX() - firstKnot.getPosition().getX(),
                otherThread.secondKnot.getPosition().getY() - firstKnot.getPosition().getY());

        if ( (0 - o1 * o2 > 0.001) && ( 0 - o3 * o4 > 0.001)) {
            return true;
        }
        return false;
    }

    /**
     * Найти ориентацию по четырем точкам
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    private double orientation(double x1, double y1, double x2, double y2) {
        return x1 * y2 - x2 * y1;
    }

    public Knot getFirstKnot() {
        return firstKnot;
    }

    public Knot getSecondKnot() {
        return secondKnot;
    }
}
