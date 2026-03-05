import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class Game {

    //declare variables
    private Player player1;
    private Player player2;
    private Deck deck;

    private GameView window;

    public Game() {
        window = new GameView(this);
        setupGame();
    }

    public void playWar() {

        Scanner input = new Scanner(System.in);

        int roundCounter;

        roundCounter = 0;

        //prints instructions
        printInstructions();

        while (true) {//keeps playing round until one player runs out of cards
            playRound();

            if (player1.getHand().isEmpty()) {
                player1.addPoints(1);
                break;
            } else if (player2.getHand().isEmpty()) {
                player2.addPoints(1);
                break;
            }

            System.out.println("New Round?");

            String confirm = input.nextLine();

            if(!confirm.equals("y")){
                break;
            }

            roundCounter++;
            System.out.println(roundCounter);

        }
    }

    public Deck getDeck() {
        return deck;
    }

    // Method that sets up the game by creating players, creating and shuffling deck, and handing equal card number
    private void setupGame() {

        ArrayList<Integer> value = new ArrayList<>();
        for (int i = 1; i < 14; i++) {
            value.add(i);
        }

        ArrayList<String> rank = new ArrayList<>();
        rank.add("Ace");
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

        ArrayList<String> suit = new ArrayList<>();
        suit.add("Spades");
        suit.add("Hearts");
        suit.add("Diamonds");
        suit.add("Clubs");

        ArrayList<Image> images = new ArrayList<>();
        for(int i = 1; i <= 52; i++) {
            Image image = new ImageIcon("src/main/resources/Cards/" + i + ".png").getImage();
            images.add(image);
        }

        // Creates universal deck (52 cards)
        deck = new Deck(rank, suit, value, images);

        // Shuffles deck
        deck.shuffle();

        ArrayList<Card> hand1 = new ArrayList<>();//first hand
        ArrayList<Card> hand2 = new ArrayList<>();//second hand

        while (deck.getCardsLeft() > 0) {
            hand1.add(deck.deal());
            hand2.add(deck.deal());
        }

        player1 = new Player("Computer", hand1);
        player2 = new Player("You", hand2);

    }

    private static void printInstructions(){
        System.out.println("this is war game\nwar game is simple\nyou have a set of cards and you place it against your opponent whoever has the highest card keeps them");
    }

    private static String evaluateWinner(Card card1, Card card2){
        // checks which card has greater value
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


    private void playRound(){
        //runs evaluateWinner class, if one winner is decided, adds point, cards go to him, playRound ends

        Scanner input = new Scanner(System.in);

        ArrayList<Card> cardPot = new ArrayList<>();

        ArrayList<Card> hand1 = player1.getHand();
        ArrayList<Card> hand2 = player2.getHand();

        Card cFirst = hand1.remove(0);
        Card pFirst = hand2.remove(0);


        System.out.println("You have a " + pFirst.getRank());
        System.out.println("Computer has a " + cFirst.getRank());

        String result = evaluateWinner(pFirst, cFirst);

        if (result.equals("Win")) {
            cardPot.add(pFirst);
            cardPot.add(cFirst);
            Collections.shuffle(cardPot);
            hand2.addAll(cardPot);
            System.out.println("You win. You have: " + hand2.size() + "\nComputer has: " + hand1.size());
            window.results(cFirst, pFirst, "Win", hand1.size(), hand2.size());
        }
        else if (result.equals("Lose")) {
            cardPot.add(pFirst);
            cardPot.add(cFirst);
            Collections.shuffle(cardPot);
            hand1.addAll(cardPot);
            System.out.println("You LooooyST. You have: " + hand2.size() + "\nComputer has: " + hand1.size());
            window.results(cFirst, pFirst, "Lose", hand1.size(), hand2.size());
        }
        else {

            war(hand1, hand2, cardPot);

        }
    }


    private void war(ArrayList<Card> hand1, ArrayList<Card> hand2, ArrayList<Card> pot) {

        Scanner input = new Scanner(System.in);

        // If there is less than 4 cards, which is how many are used to go to war, all cards go to other and game ends
        while (true) {
            if (hand2.size() < 4) {
                System.out.println("You don't have enough. The computer wins the pot!");
                hand1.addAll(pot);
                return;
            }
            if (hand1.size() < 4) {
                System.out.println("Computer doesn't have enough cards for war. You win the pot!");
                hand2.addAll(pot);
                return;
            }

            // Adds 3 unknown cards each to pot and removes from deck
            for (int i = 0; i < 3; i++) {
                pot.add(hand1.remove(0));
                pot.add(hand2.remove(0));
            }

            // Fourth card is war card, it is saved as p and c WarCard and also added to pot
            Card cWarCard = hand1.remove(0);
            Card pWarCard = hand2.remove(0);

            pot.add(pWarCard);
            pot.add(cWarCard);

            System.out.println("WAR cards: Three face down fourth ones fight\nThe Computer places\nOne...\nTwo...\nThree..." +
                    "\nYou Place...\nOne...\nTwo...\nThree...");

            System.out.println(player1.getName() + " plays: " + cWarCard);
            System.out.println(player2.getName() + " play: " + pWarCard);

            // Sees result of war cards
            String result = evaluateWinner(pWarCard, cWarCard);

            // Returns if there is a winner, if not, loops through again, until there is.
            // Pot keeps incrementing if there isn't a winner
            if (result.equals("Win")) {
                System.out.println("You win the war and take the pot!");
                Collections.shuffle(pot);
                hand2.addAll(pot);
                window.results(cWarCard, pWarCard, "You survived the War.\nWin!!!", hand1.size(), hand2.size());
                return;
            } else if (result.equals("Lose")) {
                System.out.println("Computer wins the war");
                Collections.shuffle(pot);
                hand1.addAll(pot);
                window.results(cWarCard, pWarCard, "You Lost the War :(", hand1.size(), hand2.size());
                return;
            } else {
                System.out.println("WAR again!");
            }
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.playWar();
    }
}


