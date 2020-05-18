import knottythreadsgame.listeners.ThreadEventListener;
import knottythreadsgame.model.Knot;
import knottythreadsgame.model.TearingThread;
import knottythreadsgame.model.Thread;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.awt.*;
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
    public void sets_thread_with_right_color() {
        assertSame(testTearingThread.getColor(), Color.BLACK);
    }

    @Test
    public void correctly_defines_length() {
        assertEquals(Math.round(testTearingThread.getLength()), 271);
    }

    @Test
    public void color_itself() {
        firstKnot.setPosition(new Point2D.Double(100.0, 390.0));

        assertSame(testTearingThread.getColor(), Color.RED);
    }

    @Test
    public void tears() {
        firstKnot.setPosition(new Point2D.Double(0.0, 1000.0));

        verify(threadObserver).treadTeared();
    }
}