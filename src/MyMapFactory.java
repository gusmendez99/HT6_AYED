import java.util.HashMap;
import java.util.TreeMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MyMapFactory {

    public Map<String, Card> getMap(String param) {

        switch (param) {
            case "HashMap":
                return new HashMap<String, Card>();
            case "TreeMap":
                return new TreeMap<String, Card>();
            case "LinkedHashMap":
                return new LinkedHashMap<String, Card>();
            default:
                return null;
        }
    }
}
