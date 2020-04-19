package knottythreadsgame.model;

import knottythreadsgame.events.KnotMovementsListener;
import knottythreadsgame.view.GameField;
import org.jetbrains.annotations.NotNull;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameModel {
    private static GameModel instance;

    private static String difficultyLevel;
    private Schema schema;
    private GameField gameField;

    private GameModel(String difficultyLevel) {
        GameModel.difficultyLevel = difficultyLevel;
        this.gameField = new GameField();

        // "Следим" за схемой
        SchemaObserver schemaObserver = new SchemaObserver();

        generateSchema();
        generateField();
    }

    public static GameModel getInstance(@NotNull String difficultyLevel){
        //Если объект еще не создан
        if(instance == null) {

            //Создать новый объект
            instance = new GameModel(difficultyLevel);
        }

        //Вернуть ранее созданный объект
        return instance;
    }

    // ------------ Задаем обстановку и следим за окончанием игры  ------------

    private void generateField() {
        this.gameField.drawSchema(schema);
    }

    private boolean generateSchema() {
        schema = new Schema();

        schema.generateSchema(this.difficultyLevel);

        return true;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public Schema getSchema() {
        return schema;
    }

    private static class SchemaObserver implements KnotMovementsListener {

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {

        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {

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
