package knottythreadsgame.model;

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
        this.position = position;

        for (Thread thread : threads) {
            //Проверить ограничения для рвущихся нитей
            if (thread instanceof TearingThread) {
                ((TearingThread) thread).checkTreadState();
            }
        }
    }

    public Point2D getPosition() {
        return position;
    }

    public List<Thread> getThreads() {
        return this.threads;
    }
}
