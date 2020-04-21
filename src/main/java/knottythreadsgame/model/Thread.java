package knottythreadsgame.model;

import org.jetbrains.annotations.NotNull;

import java.awt.geom.Point2D;

public class Thread {
    private Knot firstKnot;
    private Knot secondKnot;

    public Thread(@NotNull Knot firstKnot,@NotNull Knot secondKnot) {
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
    public boolean isCrossing(Thread otherThread) {
        //Найти четыре варианта ориентации для базовых и специальных случаев
        int o1 = orientation(firstKnot.getPosition(), secondKnot.getPosition(), otherThread.firstKnot.getPosition());
        int o2 = orientation(firstKnot.getPosition(), secondKnot.getPosition(), otherThread.secondKnot.getPosition());
        int o3 = orientation(otherThread.firstKnot.getPosition(), otherThread.secondKnot.getPosition(), firstKnot.getPosition());
        int o4 = orientation(otherThread.firstKnot.getPosition(), otherThread.secondKnot.getPosition(), secondKnot.getPosition());

        //Базовый случай
        if (o1 != o2 && o3 != o4)
            return true;

        //Специальные случаи
        // p1, q1 and p2 are colinear and p2 lies on segment p1q1
        if (o1 == 0 && onSegment(firstKnot.getPosition(), otherThread.firstKnot.getPosition(), secondKnot.getPosition()))
            return true;

        // p1, q1 and q2 are colinear and q2 lies on segment p1q1
        if (o2 == 0 && onSegment(firstKnot.getPosition(), otherThread.secondKnot.getPosition(), secondKnot.getPosition()))
            return true;

        // p2, q2 and p1 are colinear and p1 lies on segment p2q2
        if (o3 == 0 && onSegment(otherThread.firstKnot.getPosition(), firstKnot.getPosition(), otherThread.secondKnot.getPosition()))
            return true;

        // p2, q2 and q1 are colinear and q1 lies on segment p2q2
        if (o4 == 0 && onSegment(otherThread.firstKnot.getPosition(), secondKnot.getPosition(), otherThread.secondKnot.getPosition()))
            return true;

        return false;
    }

    public Knot getFirstKnot() {
        return firstKnot;
    }

    public Knot getSecondKnot() {
        return secondKnot;
    }

    /**
     * Проверяет, лежит ли точка q на сегменте 'pr'
     * @param p
     * @param q
     * @param r
     * @return
     */
    private static boolean onSegment(Point2D p, Point2D q, Point2D r)
    {
        return q.getX() <= Math.max(p.getX(), r.getX()) && q.getX() >= Math.min(p.getX(), r.getX()) &&
                q.getY() <= Math.max(p.getY(), r.getY()) && q.getY() >= Math.min(p.getY(), r.getY());
    }

    /**
     * Найти ориентацию для данных трех точек (p, q, r)
     * @param p
     * @param q
     * @param r
     * @return
     * // 0 --> p, q и r коллинеарны
     * // 1 --> по часовой
     * // 2 --> против часовой
     */
    private static int orientation(Point2D p, Point2D q, Point2D r)
    {
        int val = (int) ((q.getY() - p.getY()) * (r.getX() - q.getX()) -
                        (q.getX() - p.getX()) * (r.getY() - q.getY()));

        if (val == 0)
            return 0; //коллинеарны

        return (val > 0)? 1: 2;
    }
}
