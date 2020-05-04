package knottythreadsgame.model;

import knottythreadsgame.listeners.GameModelEventListener;
import knottythreadsgame.listeners.SchemaEventListener;
import knottythreadsgame.view.GameField;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class GameModel {
    private static GameModel instance;

    private static String difficultyLevel;
    private Schema schema;

    private GameModel(String difficultyLevel) {
        GameModel.difficultyLevel = difficultyLevel;

        generateSchema();
        generateField();
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

    public void clear() {
        instance = null;
    }

    // ------------ Задаем обстановку и следим за окончанием игры  ------------
    private void generateSchema() {
        SchemaFactory factory = new SchemaFactory();

        this.schema = factory.getSchemaFromJson(difficultyLevel);
        this.schema.addSchemaListener(new SchemaObserver());
    }

    private void generateField() {
        new GameField(this);
    }

    // ---------------------- Порождает события -----------------------------
    private ArrayList<GameModelEventListener> gameModelEventListeners = new ArrayList();

    public void addGameModelListener(GameModelEventListener l) {
        gameModelEventListeners.add(l);
    }

    public void deleteGameModelListener(GameModelEventListener l) {
        gameModelEventListeners.remove(l);
    }

    private class SchemaObserver implements SchemaEventListener {
        @Override
        public void treadTeared() {
            System.out.println("Watch out: the tread is ready to tear!");

            for (GameModelEventListener gameModelEventListener : gameModelEventListeners) {
                gameModelEventListener.gameFailed();
            }
        }

        @Override
        public void noCrossings() {
            System.out.println("Congrats! Mission completed!");

            for (GameModelEventListener gameModelEventListener : gameModelEventListeners) {
                gameModelEventListener.gameCompleted();
            }
        }
    }
}
