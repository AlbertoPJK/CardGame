import java.util.ArrayList;

public class Player {

    ArrayList<Card> hand = new ArrayList<>();
    private int points;
    private String name;

    public Player(String name){
        this.name = name;
        this.points = 0;
    }

    public Player(String name, ArrayList<Card> hand){
        this.name = name;
        this.hand = hand;
        this.points = 0;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void addPoints(int numPoints){
        points += numPoints;
    }

    public void addCard(Card card){
        hand.add(card);
    }

    public String toString(){
        return name + " has " + points + " points.\n" + name + "'s cards: " + hand;

    }



}
