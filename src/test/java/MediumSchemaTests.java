import knottythreadsgame.constants.GameSetUpConstants;
import knottythreadsgame.listeners.SchemaEventListener;
import knottythreadsgame.model.Schema;
import knottythreadsgame.model.SchemaFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MediumSchemaTests {
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
        testSchema = factory.getSchemaFromJson("medium");
        testSchema.addSchemaListener(schemaObserver);

        schemaObserver = mock(SchemaEventListener.class);
    }

    @Test
    public void correct_knots_amount() {
        assertEquals(testSchema.getKnots().size(), GameSetUpConstants.MEDIUM_KNOTS_AMOUNT);
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
        assertEquals(GameSetUpConstants.MEDIUM_KNOTS_AMOUNT, testSchema.getThreads().size());
    }

    @Test
    public void knot_is_moving_out_of_field() {
        testSchema.getSelectedKnot(new Point2D.Double(149.0, 235.0));

        assertFalse(testSchema.dragSelectedKnot(new Point2D.Double(500.0, 500.0)));
    }

    @Test
    public void schema_has_no_crossings() {
        testSchema.getSelectedKnot(new Point2D.Double(42.0, 402.0));
        testSchema.dragSelectedKnot(new Point2D.Double(386.0, 114.0));
        testSchema.releaseSelectedKnot();

        testSchema.getSelectedKnot(new Point2D.Double(386.0, 366.0));
        testSchema.dragSelectedKnot(new Point2D.Double(225.0, 42.0));
        testSchema.releaseSelectedKnot();

        verify(schemaObserver).noCrossings();
    }
}