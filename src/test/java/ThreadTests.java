import knottythreadsgame.model.*;
import knottythreadsgame.model.Thread;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.*;

public class ThreadTests {
    private Knot firstKnot = new Knot(new Point2D.Double(149.0, 235.0));
    private Knot secondKnot = new Knot(new Point2D.Double(386.0, 366.0));
    private Thread testThread;

    @BeforeEach
    void setUp() {
        testThread = new Thread(firstKnot, secondKnot);
    }

    @Test
    public void correctly_defines_first_knot() {
        assertSame(testThread.getFirstKnot(), firstKnot);
    }

    @Test
    public void correctly_defines_second_knot() {
        assertSame(testThread.getSecondKnot(), secondKnot);
    }

    @Test
    public void is_crossing_other_thread() {
        Knot otherFirstKnot = new Knot(new Point2D.Double(198.0, 58.0));
        Knot otherSecondKnot = new Knot(new Point2D.Double(175.8, 413.0));

        Thread otherThread = new Thread(otherFirstKnot, otherSecondKnot);

        assertTrue(testThread.isCrossing(otherThread));
    }

    @Test
    public void is_not_crossing_other_thread() {
        Knot otherFirstKnot = new Knot(new Point2D.Double(268.9, 274.6));
        Knot otherSecondKnot = new Knot(new Point2D.Double(168.9, 174.6));

        Thread otherThread = new Thread(otherFirstKnot, otherSecondKnot);

        assertFalse(testThread.isCrossing(otherThread));
    }

    @Test
    public void create_thread_with_identical_knots() {
        assertThrows(IllegalArgumentException.class, () ->
                new Thread(firstKnot, firstKnot)
        );
    }
}