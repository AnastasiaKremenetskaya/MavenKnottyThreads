package knottythreadsgame.model;

import knottythreadsgame.constants.ThreadConstants;
import org.jetbrains.annotations.NotNull;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Knot {
    private Point2D position;
    private List<Thread> threads = new ArrayList<>();

    public Knot(Point2D position) {
        this.position = position;
    }

    /**
     * Добавить нить, принадлежащую узлу
     *
     * @param thread
     */
    public void addThread(@NotNull Thread thread) {
        if (thread.getFirstKnot() != this && thread.getSecondKnot() != this) {
            throw new IllegalArgumentException("Error: The thread is connected with another knots");
        }

        this.threads.add(thread);
    }

    public void setPosition(Point2D position) {
        Point2D previousPosition = this.position;
        this.position = position;

        if (this.treadsAreBreakingRestrictions()) {
            this.position = previousPosition;
        }
    }

    /**
     * Проверить, возможно ли задать узлу желаемую позицию
     *
     * @return
     */
    private boolean treadsAreBreakingRestrictions() {
        for (Thread thread : threads) {
            //Проверить ограничения для нитей с ограниченной длиной
            if (thread instanceof RestrictedThread) {
                ((RestrictedThread) thread).setNewState();
                if (((RestrictedThread) thread).getState() == ThreadConstants.THREADS_STATES.REACHED_MAX_LENGTH)
                    return true;
            }
        }

        return false;
    }

    public Point2D getPosition() {
        return position;
    }

    public List<Thread> getThreads() {
        return this.threads;
    }
}
