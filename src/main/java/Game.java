import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Game {

    //declare variables
    private Player player1;
    private Player player2;
    private Deck deck;
    private GameView window;
    private int roundCounter = 0;

    public Game() {
        setupGame(); // Do this FIRST so the deck isn't null
        window = new GameView(this);
    }

    public void startGame() {
        printInstructions();
    }

    // Triggered by the "Next Round" button in the UI
    public void playNextRound() {
        if (player1.getHand().isEmpty() || player2.getHand().isEmpty()) {
            System.out.println("Game Over! Please hit Restart Game.");
            window.results(null, null, "Game Over! Hit Restart Game.", player1.getHand().size(), player2.getHand().size());
            return;
        }

        roundCounter++;
        System.out.println("Round: " + roundCounter);
        playRound();
    }

    // Triggered by the "Restart Game" button in the UI
    public void restartGame() {
        setupGame();
        roundCounter = 0;
        window.resetView();
        System.out.println("\n--- GAME RESTARTED ---");
    }

    public Deck getDeck() {
        return deck;
    }

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

        deck = new Deck(rank, suit, value, images);
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

    private static void printInstructions(){
        System.out.println("this is war game\nwar game is simple\nyou have a set of cards and you place it against your opponent whoever has the highest card keeps them");
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

    private void playRound(){
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
            System.out.println("You Lost. You have: " + hand2.size() + "\nComputer has: " + hand1.size());
            window.results(cFirst, pFirst, "Lose", hand1.size(), hand2.size());
        }
        else {
            war(hand1, hand2, cardPot);
        }
    }

    private void war(ArrayList<Card> hand1, ArrayList<Card> hand2, ArrayList<Card> pot) {
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

            for (int i = 0; i < 3; i++) {
                pot.add(hand1.remove(0));
                pot.add(hand2.remove(0));
            }

            Card cWarCard = hand1.remove(0);
            Card pWarCard = hand2.remove(0);

            pot.add(pWarCard);
            pot.add(cWarCard);

            System.out.println("WAR cards: Three face down fourth ones fight\nThe Computer places\nOne...\nTwo...\nThree..." +
                    "\nYou Place...\nOne...\nTwo...\nThree...");
            System.out.println(player1.getName() + " plays: " + cWarCard);
            System.out.println(player2.getName() + " play: " + pWarCard);

            String result = evaluateWinner(pWarCard, cWarCard);

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
        game.startGame();
    }
}