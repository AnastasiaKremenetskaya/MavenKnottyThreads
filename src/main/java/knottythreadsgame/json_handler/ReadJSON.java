package knottythreadsgame.json_handler;
import java.awt.geom.Point2D;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import knottythreadsgame.model.Knot;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReadJSON {

    @SuppressWarnings("unchecked")
    public List<Knot> getPositionsFromJson(String DifficultyLevel)
    {
        List<Knot> knots = new ArrayList<>();

        JSONArray requiredLevelKnotsList = null;

        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("knots_positions.json"))
        {
            //Read JSON file
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            //Считать из него позиции для нужного уровня
            requiredLevelKnotsList = (JSONArray) jsonObject.get(DifficultyLevel);

            //Распарсить позицию каждого узла в контейнер узлов
            parsePositionToArrayList(requiredLevelKnotsList, knots);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return knots;
    }

    private static void parsePositionToArrayList(JSONArray requiredLevelKnotsList, List<Knot>knots)
    {
        Iterator<Point2D> iterator = requiredLevelKnotsList.iterator();
        while (iterator.hasNext()) {
            knots.add(new Knot( iterator.next() ));
        }
    }
}
