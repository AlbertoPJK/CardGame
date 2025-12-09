import java.util.ArrayList;

public class Deck {

    ArrayList<Card> cards = new ArrayList<>();
    private int cardsLeft;


    public Deck(ArrayList<String> rank, ArrayList<String> suit, ArrayList<Integer> value){
        // Generates cards with a nested for loop using ranks, suit, value
        for(int i = 0; i < rank.size(); i++){
            for(int j = 0; j < suit.size(); j++){
                Card card = new Card(rank.get(i), suit.get(j), value.get(i));

                cards.add(card);
                cardsLeft++;
            }
        }

    }

    public boolean isEmpty() {
        return cardsLeft == 0;
    }

    public int getCardsLeft(){
        return cardsLeft;
    }

    public Card deal(){
        if(cardsLeft == 0){
            return null;
        }
        cardsLeft--;
        return cards.get(cardsLeft);

    }

    public void shuffle(){
        for(int i = cards.size() - 1; i > 0; i--){
            int random = (int)(Math.random()*i);
            Card copy = cards.get(i);
            cards.set(i, cards.get(random));
            cards.set(random, copy);
        }
    }



}
