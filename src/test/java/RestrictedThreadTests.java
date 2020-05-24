import knottythreadsgame.constants.ThreadConstants;
import knottythreadsgame.model.Knot;
import knottythreadsgame.model.RestrictedThread;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class RestrictedThreadTests {
    private Knot firstKnot = new Knot(new Point2D.Double(149.0, 235.0));
    private Knot secondKnot = new Knot(new Point2D.Double(386.0, 366.0));
    private RestrictedThread testRestrictedThread;

    @BeforeEach
    void setUp() {
        testRestrictedThread = new RestrictedThread(firstKnot, secondKnot);
        firstKnot.addThread(testRestrictedThread);
        secondKnot.addThread(testRestrictedThread);
    }

    @Test
    public void thread_has_normal_length() {
        testRestrictedThread.setNewState();
        assertSame(ThreadConstants.THREADS_STATES.NORMAL, testRestrictedThread.getState());
    }

    @Test
    public void thread_has_reached_max_length() {
        firstKnot.setPosition(new Point2D.Double(1000.0, 3090.0));

        assertSame(ThreadConstants.THREADS_STATES.REACHED_MAX_LENGTH, testRestrictedThread.getState());
    }

    @Test
    public void sets_thread_with_right_state() {
        assertSame(ThreadConstants.THREADS_STATES.NORMAL, testRestrictedThread.getState());
    }

    @Test
    public void correctly_defines_length() {
        assertEquals(271, Math.round(testRestrictedThread.getLength()));
    }

    @Test
    public void changes_state() {
        firstKnot.setPosition(new Point2D.Double(100.0, 390.0));

        assertSame(ThreadConstants.THREADS_STATES.NORMAL, testRestrictedThread.getState());
    }
}