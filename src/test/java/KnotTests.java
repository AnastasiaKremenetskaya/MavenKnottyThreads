import knottythreadsgame.model.Knot;
import knottythreadsgame.model.Thread;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.*;

public class KnotTests {
    private Knot testKnot;

    @BeforeEach
    void setUp() {
        testKnot = new Knot(new Point2D.Double(268.9, 174.6));
    }

    @Test
    public void add_thread_with_undefined_first_knot() {
        assertThrows(IllegalArgumentException.class, () ->
                testKnot.addThread(new Thread(null, testKnot))
        );
    }

    @Test
    public void add_thread_with_undefined_second_knot() {
        assertThrows(IllegalArgumentException.class, () ->
                testKnot.addThread(new Thread(testKnot, null))
        );
    }

    @Test
    public void add_thread_with_different_knots() {
        Knot firstKnot = new Knot(new Point2D.Double(268.9, 274.6));
        Knot secondKnot = new Knot(new Point2D.Double(168.9, 174.6));
        Thread thread = new Thread(firstKnot, secondKnot);

        assertThrows(IllegalArgumentException.class, () ->
                testKnot.addThread(thread)
        );
    }

    @Test
    public void add_thread_to_itself() {
        Knot firstKnot = new Knot(new Point2D.Double(268.9, 274.6));
        Thread thread = new Thread(testKnot, firstKnot);
        testKnot.addThread(thread);

        assertTrue(testKnot.getThreads().contains(thread));
    }

    @Test
    public void set_new_position() {
        Point2D newPosition = new Point2D.Double(100.0, 100.0);
        testKnot.setPosition(newPosition);

        assertSame(testKnot.getPosition(), newPosition);
    }
}