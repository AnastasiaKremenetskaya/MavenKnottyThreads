package knottythreadsgame.model;

import knottythreadsgame.json_handler.ReadJSON;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Schema {
    private final int FIELD_WIDTH = 50;
    private final int FIELD_HEIGHT = 50;

    private List<Thread> threads = new ArrayList<>();
    private List<Knot> knots = new ArrayList<>();

    public void generateSchema(String difficultyLevel) {
        ReadJSON jsonReader = new ReadJSON();

        knots = new ArrayList<>(jsonReader.getPositionsFromJson(difficultyLevel));
    }

    public List<Thread> getThreads() {
        return this.threads;
    }

    public List<Knot> getKnots() {
        return this.knots;
    }
}
