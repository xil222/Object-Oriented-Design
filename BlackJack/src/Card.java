
public class Card {

	private int value;
	private Suit suit;
	
	public Card(int value, Suit suit) {
		this.value = value;
		this.suit = suit;
	}
	
	public int getVal(){
		return value;
	}
	
	public Suit suit(){
		return suit;
	}

}
