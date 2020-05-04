package knottythreadsgame.model;

import knottythreadsgame.constants.Constants;
import knottythreadsgame.listeners.SchemaEventListener;
import knottythreadsgame.listeners.ThreadEventListener;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Schema {
    private List<Thread> threads = new ArrayList<>();
    private List<Knot> knots = new ArrayList<>();

    private Knot currentKnot;

    private ThreadObserver threadObserver;

    public Schema(List<Knot> knots) {
        for (Knot knot: knots){
            System.out.println("Generated knot position: " + knot.getPosition());
        }

        this.knots.addAll(knots);
        this.connectKnots();
        this.threadObserver = new ThreadObserver();
    }

    /**
     * Взять выбранный по координате узел
     *
     * @param newPos
     */
    public boolean getSelectedKnot(Point2D newPos) {
        for (Knot knot : this.knots) {
            if (knot.getPosition().distance(newPos) <= 10) {
                System.out.println("Selected knot: " + knot.getPosition());

                currentKnot = knot;
                currentKnot.setPosition(newPos);
                return true;
            }
        }
        return false;
    }

    /**
     * Перетаскивать выбранный узел в пределах поля
     *
     * @param newPos
     */
    public boolean dragSelectedKnot(Point2D newPos) {
        if (currentKnot != null && belongsField(newPos)) {
            currentKnot.setPosition(newPos);
            return true;
        }
        return false;
    }

    /**
     * Отпустить узел
     */
    public void releaseSelectedKnot() {
        System.out.println("moved to " + currentKnot.getPosition());

        currentKnot = null;

        if (!this.hasCrossingThreads()) {
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

    private void connectKnots() {
        //Для уровней easy и medium генерируем растягиваемые нити
        if (knots.size() < Constants.HARD_KNOTS_AMOUNT) {
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
                newThread = new TearingThread(knots.get(i), knots.get(i + 1), Constants.THREAD_MAX_LENGTH);
                newThread.addThreadListener(this.threadObserver);
                this.threads.add(newThread);
                knots.get(i).addThread(newThread);
                knots.get(i + 1).addThread(newThread);
            }
            //Соединить начальный и конечный узел
            newThread = new TearingThread(knots.get(0), knots.get(knots.size() - 1), Constants.THREAD_MAX_LENGTH);
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
                    return true;
                }
            }
        }
        return false;
    }

    private boolean belongsField(Point2D point2D) {
        return point2D.getX() > -1 &&
                point2D.getX() < Constants.FIELD_WIDTH &&
                point2D.getY() > -1 &&
                point2D.getY() < Constants.FIELD_HEIGHT;
    }

    // ---------------------- Порождает события -----------------------------
    private ArrayList<SchemaEventListener> schemaListeners = new ArrayList();

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
    }
}
