import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Deck {

    //Types of cards

    public static final String NAME_SORT = "Name";
    public static final String TYPE_SORT = "Type";
    
    public static final String TRAP_CARD_TYPE = "Trampa";
    public static final String MAGIC_CARD_TYPE = "Hechizo";
    public static final String MONSTER_CARD_TYPE = "Monstruo";
    public static final String ALL_CARD_TYPE = "Todos";

    private MyMapFactory factory = new MyMapFactory();
    private Map<Integer, Card> unusedDeck; //Cards in memory
    private Map<Integer, Card> playerDeck; // Cards of the user

    private AtomicInteger atomicInteger = new AtomicInteger();
    private String mapType = "";

    public Deck(String mapType) {
        this.unusedDeck = factory.getMap(mapType);
        this.playerDeck = factory.getMap(mapType);
        this.mapType = mapType;

    }

    public Map<Integer, Card> getUnusedDeck() {
        return unusedDeck;
    }

    public Map<Integer, Card> getPlayerDeck() {
        return playerDeck;
    }


    public void add(int id, Card card, boolean isUnusedDeck) {
        if (isUnusedDeck) {
            unusedDeck.put(id, card);
        } else {
            playerDeck.put(id, card);
        }
    }

    public int getId(){
        return atomicInteger.getAndIncrement();
    }



    public Map sortDeck(boolean isUnusedDeck, String sortType, String cardType) {

        if(isUnusedDeck){
            switch (sortType){
                case NAME_SORT:
                    return this.unusedDeck.entrySet().stream().sorted(Comparator.comparing(e -> e.getValue().getName()))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                    (e1, e2) -> e1, HashMap::new));
                case TYPE_SORT:
                    return this.unusedDeck.entrySet().stream().sorted(Comparator.comparing(e -> e.getValue().getType()))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                    (e1, e2) -> e1, LinkedHashMap::new));
                default:
                    //Means a filter, not a sort
                    switch(cardType){
                        case TRAP_CARD_TYPE:
                            return unusedDeck.entrySet()
                                    .stream()
                                    .filter(a->a.getValue().getType().equals(TRAP_CARD_TYPE))
                                    .collect(Collectors.toMap(e->e.getKey(),e->e.getValue()));

                        case MAGIC_CARD_TYPE:
                            return unusedDeck.entrySet()
                                    .stream()
                                    .filter(a->a.getValue().getType().equals(MAGIC_CARD_TYPE))
                                    .collect(Collectors.toMap(e->e.getKey(),e->e.getValue()));

                        case MONSTER_CARD_TYPE:
                            return unusedDeck.entrySet()
                                    .stream()
                                    .filter(a->a.getValue().getType().equals(MONSTER_CARD_TYPE))
                                    .collect(Collectors.toMap(e->e.getKey(),e->e.getValue()));
                        default:
                            return unusedDeck;

                    }

            }
        } else {
            switch (sortType){
                case NAME_SORT:
                    return this.playerDeck.entrySet().stream().sorted(Comparator.comparing(e -> e.getValue().getName()))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                    (e1, e2) -> e1, LinkedHashMap::new));
                case TYPE_SORT:
                    return this.playerDeck.entrySet().stream().sorted(Comparator.comparing(e -> e.getValue().getType()))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                    (e1, e2) -> e1, LinkedHashMap::new));
                default:
                    //Means a filter, not a sort
                    switch(cardType){
                        case TRAP_CARD_TYPE:
                            return playerDeck.entrySet()
                                    .stream()
                                    .filter(a->a.getValue().getType().equals(TRAP_CARD_TYPE))
                                    .collect(Collectors.toMap(e->e.getKey(),e->e.getValue()));

                        case MAGIC_CARD_TYPE:
                            return playerDeck.entrySet()
                                    .stream()
                                    .filter(a->a.getValue().getType().equals(MAGIC_CARD_TYPE))
                                    .collect(Collectors.toMap(e->e.getKey(),e->e.getValue()));

                        case MONSTER_CARD_TYPE:
                            return playerDeck.entrySet()
                                    .stream()
                                    .filter(a->a.getValue().getType().equals(MONSTER_CARD_TYPE))
                                    .collect(Collectors.toMap(e->e.getKey(),e->e.getValue()));
                        default:
                            return playerDeck;

                    }

            }
        }

    }


}
