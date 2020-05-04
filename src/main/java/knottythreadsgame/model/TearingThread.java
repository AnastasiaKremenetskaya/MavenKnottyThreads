package knottythreadsgame.model;

import knottythreadsgame.constants.Constants;
import knottythreadsgame.listeners.ThreadEventListener;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

public class TearingThread extends Thread {
    private double maxLength;
    private Color color;

    public TearingThread(@NotNull Knot firstKnot, @NotNull Knot secondKnot) {
        super(firstKnot, secondKnot);

        this.setDefaultColor();
        this.maxLength = this.getLength() * Constants.MAX_LENGTH_COEFFICIENT;
    }

    /**
     * Проверить состояние нити:
     * испустить событие в случае порватия, окраситься в случае максимального натяжения
     */
    public void checkTreadState() {
        setDefaultColor();

        if (this.getLength() >= this.maxLength) {
            for (ThreadEventListener threadEventListener : threadEventListeners) {
                threadEventListener.treadTeared();
            }
        }

        //Если оставшаяся длина составляет менее трети от исходной
        else if (this.maxLength - this.getLength() <= this.maxLength/3) {
            System.out.println("Watch out: the tread is ready to tear!");
            setTearingColor();
        }
    }

    private void setTearingColor() {
        this.color = Color.RED;
    }

    private void setDefaultColor() {
        this.color = Color.BLACK;
    }

    public double getLength() {
        return Math.sqrt(Math.pow(firstKnot.getPosition().getX() - secondKnot.getPosition().getX(), 2)
                +
                Math.pow(firstKnot.getPosition().getY() - secondKnot.getPosition().getY(), 2));
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
