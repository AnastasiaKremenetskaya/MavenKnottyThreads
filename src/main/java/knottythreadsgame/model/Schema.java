package knottythreadsgame.model;

import knottythreadsgame.listeners.SchemaEventListener;
import knottythreadsgame.listeners.TreadEventListener;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Schema {
    private List<Thread> threads = new ArrayList<>();
    private List<Knot> knots = new ArrayList<>();

    private Knot currentKnot;

    private TreadObserver treadObserver;


    public Schema(List<Knot> knots) {
        this.knots.addAll(knots);
        this.treadObserver = new TreadObserver();
    }

    /**
     * Передвинуть узел
     *
     * @return
     */
    public void getSelectedKnot(Point2D newPos) {
        for (Knot knot : this.knots) {
            if (knot.getPosition().distance(newPos) <= 10) {
                currentKnot = knot;
                currentKnot.setPosition(newPos);
            }
        }
    }

    public void dragSelectedKnot(Point2D newPos) {
        if (currentKnot != null) {
            currentKnot.setPosition(newPos);
        }
    }

    public void releaseSelectedKnot() {
        currentKnot = null;

        if (!hasCrossingThreads()) {
            for (SchemaEventListener schemaEventListener : schemaListeners) {
                schemaEventListener.noCrossings();
            }
        }
    }

    public List<Thread> getThreads() {
        return this.threads;
    }

    public List<Knot> getKnots() {
        return this.knots;
    }

    /**
     * Проверить, имеются ли пересекающиеся нити
     *
     * @return
     */
    private boolean hasCrossingThreads() {
        for (Thread thread : threads) {
            for (Thread otherTread : threads) {
                if (thread.isCrossing(otherTread)) {
                    return false;
                }
            }
        }
        return true;
    }

    // ---------------------- Порождает события -----------------------------
    ArrayList<SchemaEventListener> schemaListeners = new ArrayList();

    public void addSchemaListener(SchemaEventListener l) {
        schemaListeners.add(l);
    }

    public void deleteSchemaListener(SchemaEventListener l) {
        schemaListeners.remove(l);
    }

    private class TreadObserver implements TreadEventListener {

        @Override
        public void treadTeared() {
            for (SchemaEventListener schemaEventListener : schemaListeners) {
                schemaEventListener.treadTeared();
            }
        }

        @Override
        public void treadReachedMaxLength() {
            for (SchemaEventListener schemaEventListener : schemaListeners) {
                schemaEventListener.treadReachedMaxLength();
            }
        }
    }}
