import knottythreadsgame.constants.Constants;
import knottythreadsgame.listeners.ThreadEventListener;
import knottythreadsgame.model.Knot;
import knottythreadsgame.model.TearingThread;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.*;

public class TearingThreadTests {
    private Knot firstKnot = new Knot(new Point2D.Double(149.0, 235.0));
    private Knot secondKnot = new Knot(new Point2D.Double(386.0, 366.0));
    private TearingThread testTearingThread;
    private ThreadEventListener threadObserver = new ThreadEventListener() {
        @Override
        public void treadTeared() {
            threadObserver.treadTeared();
        }
    };

    @BeforeEach
    void setUp() {
        testTearingThread = new TearingThread(firstKnot, secondKnot);
        firstKnot.addThread(testTearingThread);
        secondKnot.addThread(testTearingThread);
        testTearingThread.addThreadListener(threadObserver);

        threadObserver = mock(ThreadEventListener.class);
    }

    @Test
    public void thread_has_normal_length() {
        assertSame(Constants.THREADS_STATES.NORMAL, testTearingThread.setNewState());
    }

    @Test
    public void thread_is_ready_to_tear() {
        firstKnot.setPosition(new Point2D.Double(100.0, 390.0));

        assertSame(Constants.THREADS_STATES.READY_TO_TEAR, testTearingThread.setNewState());
    }

    @Test
    public void thread_teared() {
        firstKnot.setPosition(new Point2D.Double(40.0, 1000.0));

        assertSame(Constants.THREADS_STATES.TEARED, testTearingThread.setNewState());
    }

    @Test
    public void sets_thread_with_right_state() {
        assertSame(testTearingThread.getState(), Constants.THREADS_STATES.NORMAL);
    }

    @Test
    public void correctly_defines_length() {
        assertEquals(Math.round(testTearingThread.getLength()), 271);
    }

    @Test
    public void changes_state() {
        firstKnot.setPosition(new Point2D.Double(100.0, 390.0));

        assertSame(testTearingThread.getState(), Constants.THREADS_STATES.READY_TO_TEAR);
    }

    @Test
    public void tears() {
        firstKnot.setPosition(new Point2D.Double(0.0, 1000.0));

        verify(threadObserver, atLeastOnce()).treadTeared();
    }
}