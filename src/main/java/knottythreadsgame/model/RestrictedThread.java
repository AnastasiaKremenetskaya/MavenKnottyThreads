package knottythreadsgame.model;

import knottythreadsgame.constants.Constants;
import org.jetbrains.annotations.NotNull;

public class RestrictedThread extends Thread {
    protected double maxLength;

    public RestrictedThread(@NotNull Knot firstKnot, @NotNull Knot secondKnot) {
        super(firstKnot, secondKnot);

        this.maxLength = this.getLength() * Constants.MAX_LENGTH_COEFFICIENT;
    }

    /**
     * Проверить состояние нити:
     * испустить событие в случае порватия, окраситься в случае максимального натяжения
     */
    public Constants.THREADS_STATES checkTreadState() {
        if (this.getLength() >= this.maxLength) {
            System.out.println("Thread reached it's max length");
            return Constants.THREADS_STATES.REACHED_MAX_LENGTH;
        }
        return Constants.THREADS_STATES.NORMAL;
    }

    public double getLength() {
        return Math.sqrt(Math.pow(firstKnot.getPosition().getX() - secondKnot.getPosition().getX(), 2)
                +
                Math.pow(firstKnot.getPosition().getY() - secondKnot.getPosition().getY(), 2));
    }
}
