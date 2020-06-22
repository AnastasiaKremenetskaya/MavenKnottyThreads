package knottythreadsgame.view.widgets;

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
            g2.setColor(((TearingThread) this.thread).getColor());
        }
        else {
            g2.setColor(Color.DARK_GRAY);
        }

        shape = new Line2D.Double(this.thread.getFirstKnot().getPosition(), this.thread.getSecondKnot().getPosition());
        g2.draw(shape);

        g2.setColor(color);
    }
}
