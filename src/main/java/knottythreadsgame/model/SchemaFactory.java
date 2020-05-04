package knottythreadsgame.model;

import knottythreadsgame.constants.Constants;
import knottythreadsgame.json_handler.ReadJSON;

import java.util.ArrayList;
import java.util.List;

public class SchemaFactory {
    /**
     * Считывает необходимые для генерации схемы позиции узлов из JSON файла
     *
     * @param difficultyLevel уровень, для которого необходимо получить вершины
     * @return
     */
    public Schema getSchemaFromJson(String difficultyLevel) {
        ReadJSON jsonReader = new ReadJSON();

        ArrayList <Knot> knots = new ArrayList<>(jsonReader.getPositionsFromJson(difficultyLevel));
        ArrayList <Thread> threads = new ArrayList<>(connectKnots(knots));

        return new Schema(knots, threads);
    }

    /**
     * Соединить узлы нитями
     *
     * @param knots
     * @return
     */
    private List<Thread> connectKnots(List<Knot> knots) {
        List<Thread> threads = new ArrayList<>();

        //Для уровней easy и medium генерируем растягиваемые нити
        if (knots.size() < Constants.HARD_KNOTS_AMOUNT) {
            Thread newThread;

            //Соединить все вершины по очереди
            for (int i = 0; i < knots.size() - 1; i++) {
                newThread = new Thread(knots.get(i), knots.get(i + 1));
                threads.add(newThread);
                knots.get(i).addThread(newThread);
                knots.get(i + 1).addThread(newThread);
            }

            //Соединить начальный и конечный узел
            newThread = new Thread(knots.get(0), knots.get(knots.size() - 1));
            threads.add(newThread);
            knots.get(0).addThread(newThread);
            knots.get(knots.size() - 1).addThread(newThread);
        }

        //Для прочих - рвущиеся
        else {
            TearingThread newThread;

            //Соединить все вершины по очереди
            for (int i = 0; i < knots.size() - 1; i++) {
                newThread = new TearingThread(knots.get(i), knots.get(i + 1));
                threads.add(newThread);
                knots.get(i).addThread(newThread);
                knots.get(i + 1).addThread(newThread);
            }
            //Соединить начальный и конечный узел
            newThread = new TearingThread(knots.get(0), knots.get(knots.size() - 1));
            threads.add(newThread);
            knots.get(0).addThread(newThread);
            knots.get(knots.size() - 1).addThread(newThread);
        }

        return threads;
    }
}
