package knottythreadsgame.model;

import knottythreadsgame.listeners.ThreadEventListener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TearingThread extends Thread {
    private double maxLength;

    public TearingThread(@NotNull Knot firstKnot, @NotNull Knot secondKnot, double maxLength) {
        super(firstKnot, secondKnot);
        this.maxLength = maxLength;
    }

    public double length() {
        return Math.sqrt(Math.pow(firstKnot.getPosition().getX() - secondKnot.getPosition().getX(), 2)
                +
                Math.pow(firstKnot.getPosition().getY() - secondKnot.getPosition().getY(), 2));
    }

    public void checkTreadState() {
        if (this.length() >= this.maxLength) {
            for (ThreadEventListener threadEventListener : threadEventListeners) {
                threadEventListener.treadTeared();
            }
        }

        //Если оставшаяся длина составляет менее трети от исходной
        else if (this.maxLength - this.length() <= this.maxLength/3) {
            for (ThreadEventListener threadEventListener : threadEventListeners) {
                threadEventListener.treadReachedMaxLength();
            }
        }
    }

    // ---------------------- Порождает события -----------------------------
    ArrayList<ThreadEventListener> threadEventListeners = new ArrayList();

    public void addThreadListener(ThreadEventListener l) {
        threadEventListeners.add(l);
    }

    public void deleteThreadListener(ThreadEventListener l) {
        threadEventListeners.remove(l);
    }
}
