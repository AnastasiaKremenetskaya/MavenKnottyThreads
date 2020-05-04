package knottythreadsgame.model;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Knot {
    private Point2D position;
    private List<Thread> threads = new ArrayList<>();

    public Knot(Point2D position) {
        this.position = position;
    }

    public Point2D getPosition() {
        return position;
    }

    public void addThread(Thread thread) {
        this.threads.add(thread);
    }

    public List<Thread> getThreads() {
        return this.threads;
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
}
