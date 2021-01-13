package knottythreadsgame.view.widgets;

import knottythreadsgame.model.Knot;
import knottythreadsgame.model.Thread;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class WidgetFactory {
    private final Map<Knot, KnotWidget> knots = new HashMap<>();
    private final Map<Thread, ThreadWidget> threads = new HashMap<>();

    public KnotWidget create(@NotNull Knot knot) {
        if(knots.containsKey(knot))
            return knots.get(knot);

        KnotWidget item = new KnotWidget(knot);
        knots.put(knot, item);

        return item;
    }

    public KnotWidget getWidget(@NotNull Knot knot) {
        return knots.get(knot);
    }

    public ThreadWidget create(@NotNull Thread thread) {
        if(threads.containsKey(thread))
            return threads.get(thread);

        ThreadWidget item = new ThreadWidget(thread);
        threads.put(thread, item);

        return item;
    }

    public ThreadWidget getWidget(@NotNull Thread thread) {
        return threads.get(thread);
    }
}
