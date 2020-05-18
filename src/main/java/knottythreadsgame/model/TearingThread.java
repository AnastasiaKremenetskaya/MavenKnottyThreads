package knottythreadsgame.model;

import knottythreadsgame.constants.Constants;
import knottythreadsgame.listeners.ThreadEventListener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TearingThread extends RestrictedThread {
    public TearingThread(@NotNull Knot firstKnot, @NotNull Knot secondKnot) {
        super(firstKnot, secondKnot);

        this.threadState = Constants.THREADS_STATES.NORMAL;
    }

    /**
     * Проверить состояние нити:
     * испустить событие в случае порватия, окраситься в случае максимального натяжения
     */
    public Constants.THREADS_STATES setNewState() {
        this.threadState = Constants.THREADS_STATES.NORMAL;

        //Если достигнута максимальная длина
        if (super.getLength() >= super.maxLength) {
            for (ThreadEventListener threadEventListener : threadEventListeners) {
                System.out.println("Alas, the tread teared!");
                threadEventListener.treadTeared();
                this.threadState = Constants.THREADS_STATES.TEARED;
            }
        }

        //Если оставшаяся длина составляет более трети от исходной
        else if (super.maxLength - super.getLength() >= super.maxLength / 3) {
            this.threadState = Constants.THREADS_STATES.NORMAL;
        }
        else {
            System.out.println("Watch out: the tread is ready to tear!");
            this.threadState = Constants.THREADS_STATES.READY_TO_TEAR;
        }

        return this.threadState;
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
