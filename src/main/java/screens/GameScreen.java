package screens;

import game_entities.Card;
import game_entities.Game;
import game_entities.Player;
import game_use_case.CheckResponseModel;

import javax.swing.*;
import java.awt.*;

public class GameScreen extends JPanel implements Screen {

    int currentPlayer;
    int firstPlayer;
    int lastToBet;
    int[] playerBalance;
    String[] card1;
    String[] card2;
    String[] boardCard;
    String[] card1PNG;
    String[] card2PNG;
    String[] boardCardPNG;
    int currentBet;
    boolean[] isActive;
    int[] playerBets;
    String[] deck;
    boolean isInteract = false;

    public boolean isInteract() {
        return isInteract;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public int getFirstPlayer() {
        return firstPlayer;
    }

    public int getLastToBet() {
        return lastToBet;
    }

    public int[] getPlayerBalance() {
        return playerBalance;
    }

    public String[] getCard1() {
        return card1;
    }

    public String[] getCard2() {
        return card2;
    }

    public String[] getBoardCard() {
        return boardCard;
    }

    public String[] getCard1PNG() {
        return card1PNG;
    }

    public String[] getCard2PNG() {
        return card2PNG;
    }

    public String[] getBoardCardPNG() {
        return boardCardPNG;
    }

    public int getCurrentBet() {
        return currentBet;
    }

    public boolean[] getIsActive() {
        return isActive;
    }

    public int[] getPlayerBets() {
        return playerBets;
    }

    public String[] getDeck() {
        return deck;
    }

    private final int CARD_WIDTH = 60;
    private final int CARD_HEIGHT = 100;
    private final JFrame frame;
    private final CheckController cController;


    /**
     * Updates the current window to contain the necessary items in the game
     * @param frame The current main window being used
     * @param currentPlayer
     * @param firstPlayer
     * @param lastToBet
     * @param playerBalance
     * @param card1
     * @param card2
     * @param boardCard
     * @param card1PNG
     * @param card2PNG
     * @param boardCardPNG
     * @param currentBet
     * @param isActive
     * @param playerBets
     * @param cController
     */
    public GameScreen(JFrame frame,
                      int currentPlayer, int firstPlayer, int lastToBet, int[] playerBalance,
                      String[] card1, String[] card2, String[] boardCard, String[] card1PNG, String[] card2PNG,
                      String[] boardCardPNG, int currentBet, boolean[] isActive, int[] playerBets, String[] deck,
                      CheckController cController) {

        this.frame = frame;
        this.cController = cController;

        this.currentPlayer = currentPlayer;
        this.firstPlayer = firstPlayer;
        this.lastToBet = lastToBet;
        this.playerBalance = playerBalance;
        this.card1 = card1;
        this.card2 = card2;
        this.boardCard = boardCard;
        this.card1PNG = card1PNG;
        this.card2PNG = card2PNG;
        this.boardCardPNG = boardCardPNG;
        this.currentBet = currentBet;
        this.isActive = isActive;
        this.playerBets = playerBets;
        this.deck = deck;

        this.setLayout(new BorderLayout());

        this.add(loadBackground());
        this.add(this.loadButtons(), BorderLayout.SOUTH);

    }

    /**
     * Creates all the cards that are visible to the player and puts them into an array
     *
     * @return An array of the images of cards that are visible to the player
     */
    private ImagePanel[] loadSeenCards() {
        String[] handCards = new String[]{
                this.card1PNG[currentPlayer], this.card2PNG[currentPlayer]
        };
        String[] boardCards = this.boardCardPNG;
        ImagePanel[] cards = new ImagePanel[2 + boardCards.length];
        // TODO delete this
//        System.out.println("Cards" + boardCards);

//        for (Card card : boardCards) {
//            System.out.println(card);
//        }

        for (int i = 0; i < boardCards.length + handCards.length; i++){
            if (i < 2) {
                cards[i] = new ImagePanel(handCards[i], 0, 0, CARD_WIDTH, CARD_HEIGHT);
                cards[i].setBounds(435 + 65 * i, 500, CARD_WIDTH, CARD_HEIGHT);
            }
            else {
                cards[i] = new ImagePanel(boardCards[i - 2], 0, 0, CARD_WIDTH, CARD_HEIGHT);
                cards[i].setBounds(335 + (i - 2)*65, 310, CARD_WIDTH, CARD_HEIGHT);
            }
        }

        return cards;
    }

    /**
     * Creates all the card backs for the hands of the opposing players
     *
     * @return An array of the images of the card backs for the opposing players
     */
    private ImagePanel[] loadHiddenCards(){
        int players = card1.length * 2 - 2;
        String cardBack = "images/game_entities.Card Back.png";
        ImagePanel[] oppCards = new ImagePanel[players];
        for (int i = 0; i < players; i+=2){
            oppCards[i] = new ImagePanel(cardBack, 0, 0, CARD_WIDTH, CARD_HEIGHT);
            oppCards[i + 1] = new ImagePanel(cardBack, 0, 0, CARD_WIDTH, CARD_HEIGHT);
            if (i == 2){
                oppCards[i].setBounds(435, 125, CARD_WIDTH, CARD_HEIGHT);
                oppCards[i + 1].setBounds(500, 125, CARD_WIDTH, CARD_HEIGHT);
            }
            else{
                oppCards[i].setBounds((int)(185 + i*(128.75)), 150 + (315 * i/6), CARD_WIDTH, CARD_HEIGHT);
                oppCards[i + 1].setBounds((int)(250 + i*(128.75)), 150 + (315 * i/6), CARD_WIDTH, CARD_HEIGHT);
            }
        }
        return oppCards;
    }

    /**
     * Puts all the background elements into a JPanel for the frame to have
     * @return The JPanel with all the background elements
     */
    @Override
    public JPanel loadBackground(){
        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setLayout(new BorderLayout());

        ImagePanel background = new ImagePanel("images/Poker Table.png", 0, 0, 1000, 800);
        ImagePanel[] cards = new ImagePanel[loadSeenCards().length + loadHiddenCards().length];
        ImagePanel[] seenCards = loadSeenCards();
        ImagePanel[] hiddenCards = loadHiddenCards();

        for (int i = 0; i <= seenCards.length + hiddenCards.length - 1; i++){
            if (i < hiddenCards.length){
                cards[i] = hiddenCards[i];
            }
            else{
                cards[i] = seenCards[i - hiddenCards.length];
            }
        }
        JLabel betPrompt = new JLabel("Bet amount:");
        JTextField betAmount = new JTextField();
        JLabel balance = new JLabel(Integer.toString(this.playerBalance[this.currentPlayer]));

        background.setBounds(0, 0, 1000, 800);
        betPrompt.setBounds(370, 640, 240, 40);
        betAmount.setBounds(440, 640, 110, 40);

        backgroundPanel.add(betPrompt);
        backgroundPanel.add(betAmount);
        backgroundPanel.add(balance);
        for (ImagePanel card: cards){
            //System.out.println(card);
            backgroundPanel.add(card);
        }
        backgroundPanel.add(background);

        return backgroundPanel;
    }

    /**
     * Creates all the necessary buttons for this screen and puts them into a JPanel
     * @return The JPanel with all the necessary button elements
     */
    @Override
    public JPanel loadButtons() {
        int BUTTON_WIDTH = 80;
        int BUTTON_HEIGHT = 60;
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton[] buttons = {new JButton("Check"), new JButton("Bet"), new JButton("Call"),
                new JButton("Fold"), new JButton("Menu")};
        //TODO ADD ACTION LISTENERS FOR BUtTONS TO CALL GAME CLASS DECISION MAKER METHOD
        buttons[0].addActionListener(e -> {
            try {
                CheckResponseModel response = cController.create(currentPlayer, firstPlayer, lastToBet, playerBalance,
                        card1, card2, boardCard, card1PNG, card2PNG, boardCardPNG, currentBet, isActive, playerBets,
                        deck);
                this.currentPlayer = response.getCurrentPlayer();
                this.firstPlayer = response.getFirstPlayer();
                this.lastToBet = response.getLastToBet();
                this.playerBalance = response.getPlayerBalance();
                this.card1 = response.getCard1();
                this.card2 = response.getCard2();
                this.boardCard = response.getBoardCard();
                this.card1PNG = response.getCard1PNG();
                this.card2PNG = response.getCard2PNG();
                this.boardCardPNG = response.getBoardCardPNG();
                this.currentBet = response.getCurrentBet();
                this.isActive = response.getIsActive();
                this.playerBets = response.getPlayerBets();
                System.out.println(card1PNG[currentPlayer]);
                System.out.println(currentPlayer);
                this.isInteract = response.isInteract();
            } catch (Exception ee) {
                JOptionPane.showMessageDialog(frame, ee.getMessage());
            }
        });
        for (JButton button: buttons){
            button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
            buttonPanel.add(button);
        }

        return buttonPanel;
    }
}
