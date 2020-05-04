package knottythreadsgame.view;

import knottythreadsgame.model.Knot;
import knottythreadsgame.model.Schema;
import knottythreadsgame.model.TearingThread;
import knottythreadsgame.model.Thread;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

class MyPanel extends JPanel {
    private Schema schema;

    MyPanel(Schema schema) {
        this.schema = schema;
        setPreferredSize(new Dimension(420,420));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //Отрисовать нити
        for (Thread thread : schema.getThreads()) {
            drawLine(g2, thread);
        }

        //Отрисовать узлы
        for (Knot knot : schema.getKnots()) {
            drawPoint(g2, knot);
        }
    }

    /**
     * Отрисовка нити
     *
     * @param g
     * @param line Нить
     */
    private void drawLine(Graphics g, Thread line) {
        Graphics2D g2 = (Graphics2D) g;

        Shape shape;//Графический примитив

        g2.setStroke(new BasicStroke(5));
        Color color = g.getColor();
        if (line instanceof TearingThread) {
            g2.setColor(((TearingThread) line).getColor());
        }
        else {
            g2.setColor(Color.DARK_GRAY);
        }

        shape = new Line2D.Double(line.getFirstKnot().getPosition(), line.getSecondKnot().getPosition());
        g2.draw(shape);

        g2.setColor(color);
    }

    /**
     * Отрисовка узла
     *
     * @param g
     * @param point Узел
     */
    private void drawPoint(Graphics g, Knot point) {
        Color color = g.getColor();
        g.setColor(Color.YELLOW);

        g.fillOval((int)point.getPosition().getX() - 5, (int)point.getPosition().getY() - 5, 10, 10);
        g.setColor(color);
    }
}
