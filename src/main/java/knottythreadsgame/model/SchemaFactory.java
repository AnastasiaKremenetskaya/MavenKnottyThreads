package knottythreadsgame.model;

import knottythreadsgame.json_handler.ReadJSON;

import java.util.ArrayList;

public class SchemaFactory {
    /**
     * Считывает необходимые для генерации схемы позиции узлов из JSON файла
     * @param difficultyLevel уровень, для которого необходимо получить вершины
     * @return
     */
    public Schema getSchemaFromJson(String difficultyLevel) {
        ReadJSON jsonReader = new ReadJSON();

        ArrayList <Knot> knots = new ArrayList<>(jsonReader.getPositionsFromJson(difficultyLevel));

        return new Schema(knots);
    }
}
