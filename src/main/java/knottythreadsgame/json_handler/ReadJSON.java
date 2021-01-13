package knottythreadsgame.json_handler;

import java.awt.geom.Point2D;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import knottythreadsgame.model.Knot;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReadJSON {

    /**
     * @param difficultyLevel выбранный уровень сложности
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Knot> getPositionsFromJson(String difficultyLevel) {
        List<Knot> knots = new ArrayList<>();

        JSONArray requiredLevelKnotsList = new JSONArray();

        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("src/main/resources/knots_positions.json")) {
            requiredLevelKnotsList.add(jsonParser.parse(reader));

            List<Object> valuesForGivenKey = getValuesForGivenKey(requiredLevelKnotsList, difficultyLevel);

            //Распарсить позицию каждого узла в контейнер узлов
            parsePositionToArrayList((List<Object>) valuesForGivenKey.get(0), knots);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return knots;
    }

    /**
     * Получить значения по заданному ключу
     *
     * @param jsonArray
     * @param key
     * @return
     */
    private List<Object> getValuesForGivenKey(JSONArray jsonArray, String key) {
        return IntStream.range(0, jsonArray.size())
                .mapToObj(index -> ((JSONObject) jsonArray.get(index)).get(key))
                .collect(Collectors.toList());
    }

    /**
     * @param valuesForGivenKey данные для уровня
     * @param knots             контейнер узлов
     */
    private static void parsePositionToArrayList(List<Object> valuesForGivenKey, List<Knot> knots) {
        Double x;
        Double y;
        Point2D.Double position;
        for (int i = 0; i < valuesForGivenKey.size() - 1; i = i+2) {
            x = (Double) valuesForGivenKey.get(i);
            y = (Double) valuesForGivenKey.get(i+1);
            position = new Point2D.Double(x, y);
            knots.add(new Knot(position));
        }
    }
}
