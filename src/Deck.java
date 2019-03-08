import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Deck {

    //Types of cards
    
    
    public static final String TRAP_CARD_TYPE = "Trampa";
    public static final String MAGIC_CARD_TYPE = "Hechizo";
    public static final String MONSTER_CARD_TYPE = "Monstruo";

    private MyMapFactory factory = new MyMapFactory();
    private Map<Integer, Card> unusedDeck; //Cards in memory
    private Map<Integer, Card> playerDeck; // Cards of the user

    private AtomicInteger atomicInteger = new AtomicInteger();

    public Deck(String mapType) {
        this.unusedDeck = factory.getMap(mapType);
        this.playerDeck = factory.getMap(mapType);

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

    public String getDeck(boolean isUnusedDeck, boolean sort) {
        String formattedResponse = "";
        ArrayList<Card> currentDeck = new ArrayList<Card>();
        if (isUnusedDeck) {
            Iterator it = unusedDeck.keySet().iterator();
            while (it.hasNext()) {
                Object key = it.next();
                currentDeck.add(unusedDeck.get(key));
            }
        } else {
            Iterator it = playerDeck.keySet().iterator();
            while (it.hasNext()) {
                Object key = it.next();
                currentDeck.add(playerDeck.get(key));
            }
        }


        for (int i = 0; i < currentDeck.size(); i++) {
            if (currentDeck.get(i).getType().equals(TRAP_CARD_TYPE)) {
                formattedResponse += "\nNombre: " + unusedDeck.get(i).getName() + " - Tipo: " + unusedDeck.get(i).getType();
            } else if (currentDeck.get(i).getType().equals(MAGIC_CARD_TYPE)) {
                formattedResponse += "\nNombre: " + unusedDeck.get(i).getName() + " - Tipo: " + unusedDeck.get(i).getType();
            } else if (currentDeck.get(i).getType().equals(MONSTER_CARD_TYPE)) {
                formattedResponse += "\nNombre: " + unusedDeck.get(i).getName() + " - Tipo: " + unusedDeck.get(i).getType();
            }
        }
        return formattedResponse;
    }

    public String getType(Card card) {
        switch (card.getType()) {
            case TRAP_CARD_TYPE:
                return "1";
            case MAGIC_CARD_TYPE:
                return "2";
            case MONSTER_CARD_TYPE:
                return "3";
            default:
                return "";
        }

    }

    public String getCardType(String cardName) {
        String type = "";
        Iterator it = unusedDeck.keySet().iterator();
        while (it.hasNext()) {
            Object key = it.next();
            if (unusedDeck.get(key).getName().equals(cardName)) {
                type = unusedDeck.get(key).getType();
            }
        }
        return type;
    }

    public String getDeck(boolean isUnusedDeck) {
        String formattedResponse = "";
        Iterator iterator = null;
        if (isUnusedDeck) {
            iterator = unusedDeck.keySet().iterator();
        } else {
            iterator = playerDeck.keySet().iterator();
        }

        while (iterator.hasNext()) {
            Object key = iterator.next();
            formattedResponse += "\nNombre: " + unusedDeck.get(key).getName() + " - Tipo: " + unusedDeck.get(key).getType();
        }
        return formattedResponse;
    }



    public int size() {
        return unusedDeck.size();
    }

}
