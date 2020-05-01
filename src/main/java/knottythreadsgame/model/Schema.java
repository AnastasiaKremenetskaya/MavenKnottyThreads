package knottythreadsgame.model;

import knottythreadsgame.listeners.SchemaEventListener;
import knottythreadsgame.listeners.ThreadEventListener;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Schema {
    private static final int EASY_KNOTS_AMOUNT = 6;
    private static final int MEDIUM_KNOTS_AMOUNT = 8;
    private static final int HARD_KNOTS_AMOUNT = 10;
    private static final int IMPOSSIBLE_KNOTS_AMOUNT = 12;
    private static final double THREAD_MAX_LENGTH = 11.0;

    private List<Thread> threads = new ArrayList<>();
    private List<Knot> knots = new ArrayList<>();

    private Knot currentKnot;

    private ThreadObserver threadObserver;

    public Schema(List<Knot> knots) {
        this.knots.addAll(knots);
        this.connectKnots();
        this.threadObserver = new ThreadObserver();
    }

    /**
     * Взять выбранный по координате узел
     *
     * @param newPos
     */
    public void getSelectedKnot(Point2D newPos) {
        for (Knot knot : this.knots) {
            if (knot.getPosition().distance(newPos) <= 10) {
                currentKnot = knot;
                currentKnot.setPosition(newPos);
            }
        }
    }

    /**
     * Перетаскивать выбранный узел в пределах поля
     *
     * @param newPos
     */
    public void dragSelectedKnot(Point2D newPos) {
        if (currentKnot != null) {
            currentKnot.setPosition(newPos);
        }
    }

    /**
     * Отпустить узел
     */
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

    private void connectKnots() {
        //Для уровней easy и medium генерируем растягиваемые нити
        if (knots.size() < HARD_KNOTS_AMOUNT) {
            Thread newThread;

            for (int i = 0; i < knots.size() - 1; i++) {
                newThread = new Thread(knots.get(i), knots.get(i + 1));
                this.threads.add(newThread);
                knots.get(i).addThread(newThread);
                knots.get(i + 1).addThread(newThread);

            }
            //Соединить начальный и конечный узел
            newThread = new Thread(knots.get(0), knots.get(knots.size() - 1));
            this.threads.add(newThread);
            knots.get(0).addThread(newThread);
            knots.get(knots.size() - 1).addThread(newThread);
        }

        //Для прочих - рвущиеся
        else {
            TearingThread newThread;

            for (int i = 0; i < knots.size() - 1; i++) {
                newThread = new TearingThread(knots.get(i), knots.get(i + 1), THREAD_MAX_LENGTH);
                newThread.addThreadListener(this.threadObserver);
                this.threads.add(newThread);
                knots.get(i).addThread(newThread);
                knots.get(i + 1).addThread(newThread);
            }
            //Соединить начальный и конечный узел
            newThread = new TearingThread(knots.get(0), knots.get(knots.size() - 1), THREAD_MAX_LENGTH);
            newThread.addThreadListener(this.threadObserver);
            this.threads.add(newThread);
            knots.get(0).addThread(newThread);
            knots.get(knots.size() - 1).addThread(newThread);
        }
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

    private class ThreadObserver implements ThreadEventListener {

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
    }
}
