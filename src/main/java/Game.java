import java.util.ArrayList;

public class Game {

    private static Player player1;
    private static Player player2;

    public static void main(String[] args) {

        setupGame();

        while(!player1.getHand().isEmpty() && !player2.getHand().isEmpty()){

        }


    }

    // Method that sets up the game by creating players, creating and shuffling deck, and handing equal card number
    private static void setupGame(){

        ArrayList<Integer> value = new ArrayList<>();
        for (int i = 2; i < 15; i++) {
            value.add(i);
        }

        ArrayList<String> rank = new ArrayList<>();
        rank.add("2");
        rank.add("3");
        rank.add("4");
        rank.add("5");
        rank.add("6");
        rank.add("7");
        rank.add("8");
        rank.add("9");
        rank.add("10");
        rank.add("Jack");
        rank.add("Queen");
        rank.add("King");
        rank.add("Ace");

        ArrayList<String> suit = new ArrayList<>();
        suit.add("Clubs");
        suit.add("Hearts");
        suit.add("Spades");
        suit.add("Diamonds");

        // Creates universal deck (52 cards)
        Deck deck = new Deck(rank, suit, value);

        // Shuffles deck
        deck.shuffle();

        ArrayList<Card> hand1 = new ArrayList<>();
        ArrayList<Card> hand2 = new ArrayList<>();

        while(deck.getCardsLeft() > 0){
            hand1.add(deck.deal());
            hand2.add(deck.deal());
        }

        player1 = new Player("Computer", hand1);
        player2 = new Player("You", hand2);


    }

    private String evaluateWinner(Card card1, Card card2){
        if(card1.getValue() > card2.getValue()){
            return card1.getRank();
        }
        else if(card1.getValue() < card2.getValue()) {
            return card2.getRank();
        }
        else{
            return "Tie";
        }
    }
}
