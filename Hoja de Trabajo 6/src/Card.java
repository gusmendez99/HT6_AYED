public class Card{

    private String name;
    private String type;

    public Card(String name, String type){
        this.name = name;
        this.type = type;
    }

    public String getType(){
        return this.type;
    }

    public String getName(){
        return this.name;
    }

    @Override
    public String toString() {
        return "Nombre: " + name + " - Tipo: " + type;
    }
}
