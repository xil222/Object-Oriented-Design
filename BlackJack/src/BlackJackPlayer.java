import java.util.List;
import java.util.ArrayList;


public class BlackJackPlayer {

	private List<Card> player;
	public BlackJackPlayer() {
		player = new ArrayList<Card>();
	}
	
	//return the value most close to 21
	public int score() {
		int count = 0; //count number of Ace
		int score = 0;
		for(Card card: player) {
			if (isAce(card)) {
				count++;
				score += 11;
			} else if (isTen(card)) {
				score += 10;
			} else {
				score += card.getVal();
			}
		}
		
		while (count > 0 && score > 21) {
			count--;
			score -= 10; //transfer an 11 to 1
		}
		
		return score;
	}
	
	public boolean busted() {
		return this.score() > 21;
	}
	
	public void addCards(Card c) {
		player.add(c);
	}
	
	public boolean isBlackJack(){
		if (player.size() != 2) {
			return false;
		}
		Card one = player.get(0);
		Card two = player.get(1);
		return (isAce(one) && isTen(two)) || (isAce(two) && isTen(one));
	}
	
	private static boolean isAce(Card a) {
		return a.getVal() == 1;
	}
	
	private static boolean isTen(Card a) {
		return a.getVal() >= 10 && a.getVal() <= 13;
	}
	
}
