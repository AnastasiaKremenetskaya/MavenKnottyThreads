package knottythreadsgame.model;

import knottythreadsgame.json_handler.ReadJSON;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Schema {
    private List<Thread> threads = new ArrayList<>();
    private List<Knot> knots = new ArrayList<>();

    /**
     * @param difficultyLevel уровень сложности игры
     */
    public void generateSchema(String difficultyLevel) {
        ReadJSON jsonReader = new ReadJSON();

        knots = new ArrayList<>(jsonReader.getPositionsFromJson(difficultyLevel));
    }

    /**
     * Проверить, имеются ли пересекающиеся нити
     *
     * @return
     */
    public boolean hasCrossingThreads() {
        for (Thread thread : threads) {
            for (Thread otherTread : threads) {
                if (thread.isCrossing(otherTread)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Передвинуть узел
     *
     * @return
     */
    public boolean moveKnot() {
        return true;
    }

    public List<Thread> getThreads() {
        return this.threads;
    }

    public List<Knot> getKnots() {
        return this.knots;
    }

}
