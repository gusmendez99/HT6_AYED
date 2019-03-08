import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

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



    public Map sortDeck(boolean isUnusedDeck, String sortType, String cardType) {
        Map map = null ;

        if(isUnusedDeck){
            switch (sortType){
                case NAME_SORT:

                    break;
                case TYPE_SORT:

                    break;
                default:
                    //Means a filter, not a sort
                    switch(cardType){
                        case TRAP_CARD_TYPE:

                            break;
                        case MAGIC_CARD_TYPE:

                            break;
                        case MONSTER_CARD_TYPE:

                            break;

                    }

            }
        } else {
            switch (sortType){
                case NAME_SORT:

                    break;
                case TYPE_SORT:

                    break;
                default:
                    //Means a filter, not a sort
                    switch(cardType){
                        case TRAP_CARD_TYPE:

                            break;
                        case MAGIC_CARD_TYPE:

                            break;
                        case MONSTER_CARD_TYPE:

                            break;

                    }

            }
        }
        return map;

    }


}
