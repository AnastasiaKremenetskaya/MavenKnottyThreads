package knottythreadsgame.view;

import knottythreadsgame.controllers.ModelController;
import knottythreadsgame.listeners.GameModelEventListener;
import knottythreadsgame.model.*;
import knottythreadsgame.model.Thread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class GameField extends JFrame {
    private JButton exitButton;
    private JPanel contentPane; //все игровое окно
    private JPanel gameSpace; //поле, в пределах которого перемещаются нити

    private JPanel panelPlot; // Кастомная панель для отрисовки

    private Schema schema;
    private GameModel model;
    private ModelController controller;

    public GameField(GameModel model) {
        this.model = model;
        this.controller = new ModelController(model);

        this.schema = this.model.getSchema();

        //Frame init
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setContentPane(contentPane);
        pack();
        setResizable(false);

        panelPlot = new MyPanel(this.schema);
        panelPlot.setOpaque(true);
        panelPlot.setBackground(Color.CYAN);
        gameSpace.add(panelPlot);

        //Добавить слушателей
        this.model.addGameModelListener(new GameModelObserver());

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onExit();
            }
        });

        panelPlot.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                if (controller.takeKnot(new Point2D.Double(mouseEvent.getX(), mouseEvent.getY()))) {
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                controller.releaseKnot();
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });

        panelPlot.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
                controller.observeKnotDragging(new Point2D.Double(mouseEvent.getX(), mouseEvent.getY()));
                repaint();
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
