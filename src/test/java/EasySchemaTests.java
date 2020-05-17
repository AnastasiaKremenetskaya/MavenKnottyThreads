import knottythreadsgame.constants.Constants;
import knottythreadsgame.listeners.SchemaEventListener;
import knottythreadsgame.model.Schema;
import knottythreadsgame.model.SchemaFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EasySchemaTests {
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
        testSchema = factory.getSchemaFromJson("easy");
        testSchema.addSchemaListener(schemaObserver);

        schemaObserver = mock(SchemaEventListener.class);
    }

    @Test
    public void correct_knots_amount() {
        assertEquals(testSchema.getKnots().size(), Constants.EASY_KNOTS_AMOUNT);
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
    public void all_knots_are_connected() {
        assertEquals(Constants.EASY_KNOTS_AMOUNT, testSchema.getThreads().size());
    }

    @Test
    public void knot_is_moving_out_of_field() {
        testSchema.getSelectedKnot(new Point2D.Double(386.0, 366.0));

        assertFalse(testSchema.dragSelectedKnot(new Point2D.Double(500.0, 500.0)));
    }

    @Test
    public void schema_has_no_crossings() {
        testSchema.getSelectedKnot(new Point2D.Double(175.8, 413.0));
        testSchema.dragSelectedKnot(new Point2D.Double(289.0, 105.0));
        testSchema.releaseSelectedKnot();

        testSchema.getSelectedKnot(new Point2D.Double(386.0, 366.0));
        testSchema.dragSelectedKnot(new Point2D.Double(99.0, 287.0));
        testSchema.releaseSelectedKnot();

        verify(schemaObserver).noCrossings();
    }
}