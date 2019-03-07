import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class Deck{

    public static final String TRAP_CARD_TYPE = "Trampa";
    public static final String MAGIC_CARD_TYPE = "Hechizo";
    public static final String MONSTER_CARD_TYPE = "Monstruo";

    private MyMapFactory factory = new MyMapFactory();
    private Map<String, Card> unusedDeck; //Cards in memory
    private Map <String, Card> playerDeck; // Cards of the user

    public Deck(String mapType){
        this.unusedDeck = factory.getMap(mapType);
        this.playerDeck = factory.getMap(mapType);

    }

    public int getType(Card card){
        switch (card.getType()){
            case TRAP_CARD_TYPE:
                return 1;
            case MAGIC_CARD_TYPE:
                return 2;
            case MONSTER_CARD_TYPE:
                return 3;
            default:
                return 0;
        }

    }

    public void add(String key, Card card, boolean isUnusedDeck){
        if(isUnusedDeck){
            unusedDeck.put(key, card);
        } else {
            playerDeck.put(key, card);
        }
    }

    public String getDeck(boolean isUnusedDeck, boolean sort){
        String formattedResponse = "";
        ArrayList<Card> currentDeck = new ArrayList<Card>();
        if (isUnusedDeck){
            Iterator it = unusedDeck.keySet().iterator();
            while(it.hasNext()){
                Object key = it.next();
                currentDeck.add(unusedDeck.get(key));
            }
        } else{
            Iterator it = playerDeck.keySet().iterator();
            while(it.hasNext()){
                Object key = it.next();
                currentDeck.add(playerDeck.get(key));
            }
        }


        for(int i=0;i<currentDeck.size();i++){
            if (currentDeck.get(i).getType().equals(TRAP_CARD_TYPE)){
                formattedResponse += "\nNombre: " + unusedDeck.get(i).getName() + " - Tipo: " + unusedDeck.get(i).getType();
            } else if (currentDeck.get(i).getType().equals(MAGIC_CARD_TYPE)){
                formattedResponse += "\nNombre: " + unusedDeck.get(i).getName() + " - Tipo: " + unusedDeck.get(i).getType();
            } else if (currentDeck.get(i).getType().equals(MONSTER_CARD_TYPE)){
                formattedResponse += "\nNombre: " + unusedDeck.get(i).getName() + " - Tipo: " + unusedDeck.get(i).getType();
            }
        }
        return formattedResponse;
    }

    public String getCardType(String cardName){
        String type = "";
        Iterator it = unusedDeck.keySet().iterator();
        while(it.hasNext()){
            Object key = it.next();
            if(unusedDeck.get(key).getName().equals(cardName)){
                type = unusedDeck.get(key).getType();
            }
        }
        return type;
    }

    public String getDeck(boolean isUnusedDeck){
        String formattedResponse = "";
        Iterator iterator = null;
        if (isUnusedDeck){
            iterator = unusedDeck.keySet().iterator();
        } else{
            iterator = playerDeck.keySet().iterator();
        }

        while(iterator.hasNext()){
            Object key = iterator.next();
            formattedResponse += "\nNombre: " + unusedDeck.get(key).getName() + " - Tipo: " + unusedDeck.get(key).getType();
        }
        return formattedResponse;
    }

    public Map<String, Card> getPlayerDeck(){
        return playerDeck;
    }

    public int size(){
        return unusedDeck.size();
    }

}
