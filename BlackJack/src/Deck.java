import java.util.Random;

//two main functions, dealCard and Shuffle
public class Deck {

	private Card[] cardDeck;
	private int counter;
	private Random rand;
	private int dealNumber;
 	
	public Deck() {
		dealNumber = 0;
		rand = new Random();
		cardDeck = new Card[52];
		counter = 0;
		for (int num = 1; num <= 13; num++) {
			for (Suit suit: Suit.values()){
				cardDeck[counter++] = new Card(num, suit);
			}
		}
	}

	//shuffle the entire card set
	public void shuffle() {
		for (int i = 0; i < counter; i++) {
			int swapIndex = rand.nextInt(counter - i) + i;
			swap(cardDeck, i, swapIndex);
		}
	}
	
	private void swap(Card[] cardDeck, int a, int b) {
		Card temp = cardDeck[a];
		cardDeck[a] = cardDeck[b];
		cardDeck[b] = temp;
	}
	
	private int remainingCards() {
		return cardDeck.length - dealNumber;
	}
	
	public Card dealCard() {
		Card temp = cardDeck[dealNumber];
		dealNumber++;
		return temp;
	}
	
	//when finishing a game --> automatically shuffle if using more than 60% cards
	public void reset() {
		if (remainingCards() < 0.4*cardDeck.length) {
			this.shuffle();
			dealNumber = 0;
		}
	}
	
}
