package knottythreadsgame.model;

import knottythreadsgame.listeners.GameModelEventListener;
import knottythreadsgame.listeners.SchemaEventListener;
import knottythreadsgame.view.GameField;
import org.jetbrains.annotations.NotNull;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class GameModel {
    private static GameModel instance;

    private static String difficultyLevel;
    private Schema schema;
    private GameField gameField;

    private SchemaObserver schemaObserver;

    private GameModel(String difficultyLevel) {
        GameModel.difficultyLevel = difficultyLevel;

        generateSchema();
        generateField();

        // "Следим" за схемой
        this.schemaObserver = new SchemaObserver();
    }

    public static GameModel start(@NotNull String difficultyLevel) {
        //Если объект еще не создан
        if (instance == null) {

            //Создать новый объект
            instance = new GameModel(difficultyLevel);
        }

        //Вернуть ранее созданный объект
        return instance;
    }

    public Schema getSchema() {
        return this.schema;
    }

    // ------------ Обработка действий пользователя мышью  ------------
    //TODO вынести в контроллер?
    public void takeKnot(Point2D.Double position) {
        schema.getSelectedKnot(position);
    }

    public void observeKnotDragging(Point2D.Double position) {
        schema.dragSelectedKnot(position);
    }

    public void releaseKnot() {
        schema.releaseSelectedKnot();
    }

    // ------------ Задаем обстановку и следим за окончанием игры  ------------
    private void generateSchema() {
        SchemaFactory factory = new SchemaFactory();

        this.schema = factory.getSchemaFromJson(difficultyLevel);
        this.schema.addSchemaListener(this.schemaObserver);
    }

    private void generateField() {
        this.gameField = new GameField(this);
    }

    // ---------------------- Порождает события -----------------------------
    ArrayList<GameModelEventListener> gameModelEventListeners = new ArrayList();

    public void addGameModelListener(GameModelEventListener l) {
        gameModelEventListeners.add(l);
    }

    public void deleteGameModelListener(GameModelEventListener l) {
        gameModelEventListeners.remove(l);
    }

    private class SchemaObserver implements SchemaEventListener {
        @Override
        public void treadReachedMaxLength() {
            for (GameModelEventListener gameModelEventListener : gameModelEventListeners) {
                gameModelEventListener.treadReachedMaxLength();
            }
        }

        @Override
        public void treadTeared() {
            for (GameModelEventListener gameModelEventListener : gameModelEventListeners) {
                gameModelEventListener.gameFailed();
            }
        }

        @Override
        public void noCrossings() {
            for (GameModelEventListener gameModelEventListener : gameModelEventListeners) {
                gameModelEventListener.gameCompleted();
            }
        }
    }
}
