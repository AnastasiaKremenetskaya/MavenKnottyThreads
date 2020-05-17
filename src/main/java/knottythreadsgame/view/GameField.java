package knottythreadsgame.view;

import knottythreadsgame.controllers.ModelController;
import knottythreadsgame.listeners.GameModelEventListener;
import knottythreadsgame.model.*;
import knottythreadsgame.view.widgets.FieldWidget;
import knottythreadsgame.view.widgets.WidgetFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;

public class GameField extends JFrame {
    private JButton exitButton;
    private JPanel contentPane; //все игровое окно
    private JPanel gameSpace; //поле, в пределах которого перемещаются нити

    private JPanel fieldWidget; // Кастомная панель для отрисовки

    private Schema schema;
    private GameModel model;
    private ModelController controller;

    private WidgetFactory widgetFactory;

    public GameField(GameModel model) {
        this.model = model;
        this.controller = new ModelController(model);

        this.schema = this.model.getSchema();

        this.widgetFactory = new WidgetFactory();

        //Frame init
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setContentPane(contentPane);
        pack();
        setResizable(false);

        fieldWidget = new FieldWidget(this.schema, this.widgetFactory);
        fieldWidget.setOpaque(true);
        fieldWidget.setBackground(Color.CYAN);
        gameSpace.add(fieldWidget);

        //Добавить слушателей
        this.model.addGameModelListener(new GameModelObserver());

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onExit();
            }
        });

        fieldWidget.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                if (controller.takeKnot(new Point2D.Double(mouseEvent.getX(), mouseEvent.getY()))) {
                    fieldWidget.repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                controller.releaseKnot();
                fieldWidget.repaint();
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });

        fieldWidget.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
                controller.observeKnotDragging(new Point2D.Double(mouseEvent.getX(), mouseEvent.getY()));
                fieldWidget.repaint();
            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {

            }
        });

    }

    private void onExit() {
        this.model.clear();
        setVisible(false);
        dispose();

        Menu.showMenu();
    }

    private void notifyAboutThreadTearing() {
        JOptionPane.showMessageDialog(this, "Alas! The tread has teared!");
    }

    private void notifyAboutVictory() {
        JOptionPane.showMessageDialog(this, "Congrats! Mission completed!");
    }


    private class GameModelObserver implements GameModelEventListener {

        @Override
        public void gameFailed() {
            notifyAboutThreadTearing();
            onExit();
        }

        @Override
        public void gameCompleted() {
            notifyAboutVictory();
            onExit();
        }
    }
};
