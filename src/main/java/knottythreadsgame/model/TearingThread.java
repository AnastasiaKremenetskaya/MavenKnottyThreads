package knottythreadsgame.model;

import knottythreadsgame.constants.Constants;
import knottythreadsgame.listeners.ThreadEventListener;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

public class TearingThread extends RestrictedThread {
    private Color color;

    public TearingThread(@NotNull Knot firstKnot, @NotNull Knot secondKnot) {
        super(firstKnot, secondKnot);

        this.setDefaultColor();
    }

    /**
     * Проверить состояние нити:
     * испустить событие в случае порватия, окраситься в случае максимального натяжения
     */
    public Constants.THREADS_STATES checkTreadState() {
        setDefaultColor();

        //Если достигнута максимальная длина
        if (super.getLength() >= super.maxLength) {
            for (ThreadEventListener threadEventListener : threadEventListeners) {
                System.out.println("Alas, the tread teared!");
                threadEventListener.treadTeared();
                return Constants.THREADS_STATES.TEARED;
            }
        }

        //Если оставшаяся длина составляет более трети от исходной
        else if (super.maxLength - super.getLength() >= super.maxLength / 3) {
            return Constants.THREADS_STATES.NORMAL;
        }

        System.out.println("Watch out: the tread is ready to tear!");
        setTearingColor();
        return Constants.THREADS_STATES.READY_TO_TEAR;
    }

    private void setTearingColor() {
        this.color = Color.RED;
    }

    private void setDefaultColor() {
        this.color = Color.BLACK;
    }

    public Color getColor() {
        return color;
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
