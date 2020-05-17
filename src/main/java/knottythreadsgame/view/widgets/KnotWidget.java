package knottythreadsgame.view.widgets;

import knottythreadsgame.model.Knot;

import javax.swing.*;
import java.awt.*;

public class KnotWidget extends JPanel {
    private final Knot knot;

    public KnotWidget(Knot point) {
        this.knot = point;
    }

    /**
     * Отрисовка узла
     *
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Color color = g.getColor();
        g.setColor(Color.YELLOW);

        g.fillOval((int)knot.getPosition().getX() - 5, (int)knot.getPosition().getY() - 5, 10, 10);
        g.setColor(color);
    }
}
