package knottythreadsgame.model;

import knottythreadsgame.json_handler.ReadJSON;

import java.util.ArrayList;
import java.util.List;

public class Schema {
    private List<Thread> threads = new ArrayList<>();
    private List<Knot> knots = new ArrayList<>();

    public Schema(List<Knot> knots) {
        this.knots.addAll(knots);
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
