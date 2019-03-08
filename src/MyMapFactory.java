import java.util.HashMap;
import java.util.TreeMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MyMapFactory {

    public Map<Integer, Card> getMap(String param) {

        switch (param) {
            case MapUtils.HashMapType:
                return new HashMap<>();
            case MapUtils.TreeMapType:
                return new TreeMap<>();
            case MapUtils.LinkedHashMapType:
                return new LinkedHashMap<>();
            default:
                return null;
        }
    }
}
