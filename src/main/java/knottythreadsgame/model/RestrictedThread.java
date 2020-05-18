package knottythreadsgame.model;

import knottythreadsgame.constants.Constants;
import org.jetbrains.annotations.NotNull;

public class RestrictedThread extends Thread {
    protected double maxLength;
    protected Constants.THREADS_STATES threadState;

    public RestrictedThread(@NotNull Knot firstKnot, @NotNull Knot secondKnot) {
        super(firstKnot, secondKnot);

        this.maxLength = this.getLength() * Constants.MAX_LENGTH_COEFFICIENT;
    }

    /**
     * Проверить состояние нити:
     * испустить событие в случае порватия, окраситься в случае максимального натяжения
     */
    public Constants.THREADS_STATES setNewState() {
        if (super.getLength() >= this.maxLength) {
            System.out.println("Thread reached it's max length");
            this.threadState = Constants.THREADS_STATES.REACHED_MAX_LENGTH;
        }
        else {
            this.threadState = Constants.THREADS_STATES.NORMAL;
        }

        return this.threadState;
    }

    public Constants.THREADS_STATES getState() {
        return this.threadState;
    }

}
