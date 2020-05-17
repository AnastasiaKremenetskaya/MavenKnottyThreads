import knottythreadsgame.constants.Constants;
import knottythreadsgame.listeners.SchemaEventListener;
import knottythreadsgame.model.*;
import knottythreadsgame.model.Thread;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ImpossibleSchemaTests {
    private Schema testSchema;
    private SchemaEventListener schemaObserver = new SchemaEventListener() {
        @Override
        public void treadTeared() {
            schemaObserver.treadTeared();
        }

        @Override
        public void noCrossings() {
            schemaObserver.noCrossings();
        }
    };
    private SchemaFactory factory = new SchemaFactory();

    @BeforeEach
    void setUp() {
        testSchema = factory.getSchemaFromJson("impossible");
        testSchema.addSchemaListener(schemaObserver);

        schemaObserver = mock(SchemaEventListener.class);
    }

    @Test
    public void correct_knots_amount() {
        assertEquals(testSchema.getKnots().size(), Constants.IMPOSSIBLE_KNOTS_AMOUNT);
    }

    @Test
    public void get_existing_knot() {
        assertTrue(testSchema.getSelectedKnot(new Point2D.Double(386.0, 366.0)));
    }

    @Test
    public void get_not_existing_knot() {
        assertFalse(testSchema.getSelectedKnot(new Point2D.Double(0.0, 0.0)));
    }

    @Test
    public void all_threads_are_tearing() {
        for (Thread thread : testSchema.getThreads()) {
            assertTrue(thread instanceof TearingThread);
        }
    }

    @Test
    public void all_knots_are_connected() {
        assertEquals(Constants.IMPOSSIBLE_KNOTS_AMOUNT, testSchema.getThreads().size());
    }

    @Test
    public void knot_is_moving_out_of_field() {
        testSchema.getSelectedKnot(new Point2D.Double(386.0, 366.0));

        assertFalse(testSchema.dragSelectedKnot(new Point2D.Double(500.0, 500.0)));
    }

    @Test
    public void one_tread_teared() {
        testSchema.getSelectedKnot(new Point2D.Double(149.0, 235.0));
        testSchema.dragSelectedKnot(new Point2D.Double(149.0, 390.0));

        verify(schemaObserver).treadTeared();
    }

    @Test
    public void two_tread_teared() {
        testSchema.getSelectedKnot(new Point2D.Double(149.0, 235.0));
        testSchema.dragSelectedKnot(new Point2D.Double(100.0, 390.0));

        verify(schemaObserver, times(2)).treadTeared();
    }

    @Test
    public void schema_has_no_crossings() {
        testSchema.getSelectedKnot(new Point2D.Double(175.8, 413.0));
        testSchema.dragSelectedKnot(new Point2D.Double(353.0, 125.0));
        testSchema.releaseSelectedKnot();

        testSchema.getSelectedKnot(new Point2D.Double(42.0, 402.0));
        testSchema.dragSelectedKnot(new Point2D.Double(374.0, 268.0));
        testSchema.releaseSelectedKnot();

        testSchema.getSelectedKnot(new Point2D.Double(21.9, 24.6));
        testSchema.dragSelectedKnot(new Point2D.Double(252.0, 242.0));
        testSchema.releaseSelectedKnot();

        testSchema.getSelectedKnot(new Point2D.Double(149.0, 235.0));
        testSchema.dragSelectedKnot(new Point2D.Double(158.0, 89.0));
        testSchema.releaseSelectedKnot();

        testSchema.getSelectedKnot(new Point2D.Double(100.0, 150.0));
        testSchema.dragSelectedKnot(new Point2D.Double(270.0, 83.0));
        testSchema.releaseSelectedKnot();

        testSchema.getSelectedKnot(new Point2D.Double(290.0, 220.0));
        testSchema.dragSelectedKnot(new Point2D.Double(303.0, 161.0));
        testSchema.releaseSelectedKnot();

        verify(schemaObserver).noCrossings();
    }
}