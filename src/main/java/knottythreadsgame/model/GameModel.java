package knottythreadsgame.model;

import knottythreadsgame.view.GameField;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.text.Position;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

public class GameModel {
    private static GameModel instance;

    private static String difficultyLevel;
    private Schema schema;
    private GameField gameField;

    private GameModel(String difficultyLevel) {
        GameModel.difficultyLevel = difficultyLevel;

        // "Следим" за схемой
        SchemaObserver schemaObserver = new SchemaObserver();

        generateSchema();
        generateField();
    }

    public static GameModel start(@NotNull String difficultyLevel){
        //Если объект еще не создан
        if(instance == null) {

            //Создать новый объект
            instance = new GameModel(difficultyLevel);
        }

        //Вернуть ранее созданный объект
        return instance;
    }

    // ------------ Задаем обстановку и следим за окончанием игры  ------------
    private void generateSchema() {
        SchemaFactory factory = new SchemaFactory();

        this.schema = factory.getSchemaFromJson(this.difficultyLevel);
    }


    private void generateField() {
        this.gameField = new GameField();
        this.gameField.drawSchema(schema);
    }

    private void identifyGameSituation(MouseEvent mouseEvent) {
        Point2D mousePos = new Point2D.Double(mouseEvent.getX(), mouseEvent.getY());

        for (Knot knot : schema.getKnots()){
            if (knot.getPosition().distance(mousePos) <= 10) {
                if (!knot.setPosition(mousePos)) {
                    gameField.notifyAboutThreadTearing();
                    gameField.onExit();
                }
            }
        }
    }

    private class SchemaObserver implements MouseMotionListener, MouseListener {

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {

        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            identifyGameSituation(mouseEvent);
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseDragged(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {

        }
    }
}
