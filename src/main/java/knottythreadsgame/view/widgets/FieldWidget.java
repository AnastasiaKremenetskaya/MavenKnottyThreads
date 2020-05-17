package knottythreadsgame.view.widgets;

import knottythreadsgame.model.Knot;
import knottythreadsgame.model.Schema;
import knottythreadsgame.model.Thread;

import javax.swing.*;
import java.awt.*;

public class FieldWidget extends JPanel {
    private Schema schema;
    private WidgetFactory widgetFactory;

    public FieldWidget(Schema schema, WidgetFactory widgetFactory) {
        this.schema = schema;
        this.widgetFactory = widgetFactory;
        setPreferredSize(new Dimension(420,420));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Отрисовать нити
        for (Thread thread : schema.getThreads()) {
            widgetFactory.create(thread).paintComponent(g);
        }

        //Отрисовать узлы
        for (Knot knot : schema.getKnots()) {
            widgetFactory.create(knot).paintComponent(g);
        }
    }
}
