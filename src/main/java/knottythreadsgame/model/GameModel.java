package knottythreadsgame.model;

import knottythreadsgame.view.GameField;
import org.jetbrains.annotations.NotNull;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameModel implements MouseListener {
//    private final int EASY_KNOTS_AMOUNT = 6;
//    private final int MEDIUM_KNOTS_AMOUNT = 6;
//    private final int HARD_KNOTS_AMOUNT = 6;
//    private final int IMPOSSIBLE_KNOTS_AMOUNT = 6;

    private String difficultyLevel;
    private Schema schema;
    private GameField gameField;

    public GameModel(@NotNull String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
        this.gameField = new GameField();
        generateSchema();
        generateField();
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
}
