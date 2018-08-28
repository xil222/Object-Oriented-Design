import java.util.List;
import java.util.ArrayList;


//players have to pick until their scores exceed 16
public class BlackJack {

	private Deck deck;
	private BlackJackPlayer[] hands;
	private static final int continueUntil = 16;
	private boolean normalGame;
	//private int count; //count for CardDeck 
	
	
	public BlackJack(int numPlayers) {
		if (numPlayers > 5) {
			throw new IndexOutOfBoundsException("players can't exceed 5");
		} 
		normalGame = true;
		hands = new BlackJackPlayer[numPlayers];
		for (int i = 0; i < numPlayers; i++) {
			hands[i] = new BlackJackPlayer();
		}	
	}
	
	public void initialize() {
		deck = new Deck();
		deck.shuffle();
	}
	
	public void setUp() {
		List<Integer> winner = new ArrayList<Integer>();
		for (int i = 0; i < hands.length; i++) {
			hands[i].addCards(deck.dealCard());
			hands[i].addCards(deck.dealCard());
			if (hands[i].isBlackJack()) {
				winner.add(new Integer(i));
				System.out.println("player " + i + " is Black Jack!");
			}
		}
		for (int i = 0; i < winner.size(); i++) {
			System.out.println("Winner is " + winner.get(i));
		}
		normalGame = false;
	}
	
	public void drawCards() {
		if (!normalGame) {
			List<Integer> winList = new ArrayList<Integer>(); 
			for (int i = 0; i < hands.length; i++) {
				while (hands[i].score() <= continueUntil) {
					hands[i].addCards(deck.dealCard());
				}
				if (hands[i].score() > 21) {
					System.out.println("player " + i + " gets busted");
				} else {
					System.out.println("player " + i + " gets " + hands[i].score());
					if (winList.size() == 0) {
						winList.add(new Integer(i));
					} else {
						if (hands[winList.get(0)].score() > hands[i].score()) {
							continue;
						} else if (hands[winList.get(0)].score() == hands[i].score()) {
							winList.add(new Integer(i));
						} else {
							winList = new ArrayList<Integer>();
							winList.add(new Integer(i));
						}
					}
				
				}	
			}
			for (int i = 0; i < winList.size(); i++) {
				System.out.println("Winner is " + winList.get(i));
			}
			
		} 
		
	}
	
	public static void main(String[] args) {
		BlackJack newGame = new BlackJack(3);
		newGame.initialize();
		newGame.setUp();
		newGame.drawCards();
		
	}
}
