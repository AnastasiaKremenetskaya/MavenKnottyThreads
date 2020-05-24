package knottythreadsgame.model;

import knottythreadsgame.constants.ThreadConstants;
import org.jetbrains.annotations.NotNull;

public class RestrictedThread extends Thread {
    protected double maxLength;
    protected ThreadConstants.THREADS_STATES threadState;

    public RestrictedThread(@NotNull Knot firstKnot, @NotNull Knot secondKnot) {
        super(firstKnot, secondKnot);

        this.maxLength = this.getLength() * ThreadConstants.MAX_LENGTH_COEFFICIENT;
    }

    /**
     * Проверить состояние нити:
     * испустить событие в случае порватия, окраситься в случае максимального натяжения
     */
    public ThreadConstants.THREADS_STATES setNewState() {
        if (super.getLength() >= this.maxLength) {
            System.out.println("Thread reached it's max length");
            this.threadState = ThreadConstants.THREADS_STATES.REACHED_MAX_LENGTH;
        }
        else {
            this.threadState = ThreadConstants.THREADS_STATES.NORMAL;
        }

        return this.threadState;
    }

    public ThreadConstants.THREADS_STATES getState() {
        return this.threadState;
    }

}
