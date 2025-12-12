import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    private static Player player1;
    private static Player player2;


    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        setupGame();

        while (true) {
            playRound();

            if (player1.getHand().isEmpty()) {
                player1.addPoints(1);
                break;
            } else if (player2.getHand().isEmpty()) {
                player2.addPoints(1);
                break;
            }


            System.out.print("Play another round? (y/n): ");
            String response = input.nextLine().toLowerCase();

            if (!response.equals("y")) {
                break;
            }
        }
        System.out.println("Player has " + player1.getPoints() + " Computer has " + player2.getPoints());

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

        while (deck.getCardsLeft() > 0) {
            hand1.add(deck.deal());
            hand2.add(deck.deal());
        }

        player1 = new Player("Computer", hand1);
        player2 = new Player("You", hand2);


    }

    private static String evaluateWinner(Card card1, Card card2){
        if (card1.getValue() > card2.getValue()) {
            return "Win";
        }
        else if (card1.getValue() < card2.getValue()) {
            return "Lose";
        }
        else {
            return "Tie";
        }
    }


    private static void playRound(){

        Scanner input = new Scanner(System.in);

        ArrayList<Card> hand1 = player1.getHand();
        ArrayList<Card> hand2 = player2.getHand();

        Card pFirst = hand1.remove(0);
        Card cFirst = hand2.remove(0);


        System.out.println("You have a " + pFirst.getRank());
        System.out.println("Computer has a " + cFirst.getRank());

        String result = evaluateWinner(pFirst, cFirst);

        if (result.equals("Win")) {
            hand1.add(pFirst);
            hand1.add(cFirst);
            System.out.println("You win. You have: " + hand1.size() + "\nComputer has: " + hand2.size());
        }
        else if (result.equals("Lose")) {
            hand2.add(pFirst);
            hand2.add(cFirst);
            System.out.println("You LooooyST. You have: " + hand1.size() + "\nComputer has: " + hand2.size());
        }
        else {
            System.out.print("Wanna go to war? You might lose! (y/n) ");
            String response = input.nextLine().toLowerCase();

            if (!response.equals("y")) {
                return;
            }

            ArrayList<Card> cardPot = new ArrayList<>();
            cardPot.add(pFirst);
            cardPot.add(cFirst);
            war(hand1, hand2, cardPot);

        }
    }


    private static void war(ArrayList<Card> hand1, ArrayList<Card> hand2, ArrayList<Card> pot) {

        Scanner input = new Scanner(System.in);

        while (true) {
            if (hand1.size() < 4) {
                System.out.println("Computer doesn't have enough cards for war. You win the pot!");
                hand2.addAll(pot);
                return;
            }
            if (hand2.size() < 4) {
                System.out.println("You don't have enough. The computer wins the pot!");
                hand1.addAll(pot);
                return;
            }

            for (int i = 0; i < 3; i++) {
                pot.add(hand1.remove(0));
                pot.add(hand2.remove(0));
            }

            Card pWarCard = hand1.remove(0);
            Card cWarCard = hand2.remove(0);
            pot.add(pWarCard);
            pot.add(cWarCard);

            System.out.println("WAR cards: Three face down fourth ones fight\nThe Computer places\nOne...\nTwo...\nThree..." +
                    "\nYou Place...\nOne...\nTwo...\nThree...");

            System.out.print("Wanna reveal the last cards? (y/n) ");
            String response = input.nextLine().toLowerCase();
            if (!response.equals("y")) {
                return;
            }

            System.out.println(player1.getName() + " plays: " + cWarCard);
            System.out.println(player2.getName() + " play: " + pWarCard);

            String result = evaluateWinner(pWarCard, cWarCard);

            if (result.equals("Win")) {
                System.out.println("You win the war and take the pot!");
                hand1.addAll(pot);
                return;
            } else if (result.equals("Lose")) {
                System.out.println("Computer wins the war");
                hand2.addAll(pot);
                return;
            } else {
                System.out.println("WAR again!");
            }
        }
    }

}


