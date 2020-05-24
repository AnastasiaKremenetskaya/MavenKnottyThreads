package knottythreadsgame.view.widgets;

import knottythreadsgame.constants.ThreadConstants;
import knottythreadsgame.model.TearingThread;
import knottythreadsgame.model.Thread;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class ThreadWidget extends JPanel {
    private final Thread thread;

    public ThreadWidget(Thread line) {
        this.thread = line;
    }

    /**
     * Отрисовка нити
     *
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        Shape shape;//Графический примитив

        g2.setStroke(new BasicStroke(5));
        Color color = g.getColor();
        if (this.thread instanceof TearingThread) {
            if (((TearingThread) this.thread).getState() == ThreadConstants.THREADS_STATES.READY_TO_TEAR) {
                g2.setColor(Color.RED);
            }
            else {
                g2.setColor(Color.BLACK);
            }
        }
        else {
            g2.setColor(Color.DARK_GRAY);
        }

        shape = new Line2D.Double(this.thread.getFirstKnot().getPosition(), this.thread.getSecondKnot().getPosition());
        g2.draw(shape);

        g2.setColor(color);
    }
}
