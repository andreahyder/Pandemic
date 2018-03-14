import java.util.ArrayList;

public class Player {
	String username;
	String password;
	Status status;
	Boolean ready;
	Pawn pawn;
	ArrayList<PlayerCard> hand;
	
	Player(String n){
		username = n;
		status = Status.InGame;
		ready = false;
		pawn = null;
		hand = new ArrayList<PlayerCard>();
	}
	
	void givePawn(City c){
		pawn = new Pawn(c);
	}
	
	int getCard(String s){
		int i = 0;
		Boolean found = false;
		while(i < hand.size() && !found) {
			if(hand.get(i).city.name.matches(s)) {
				found = true;
			}
			else{
				i++;
			}
		}
		if(found) {
			return i;
		}
		else {
			return -1;
		}
	}
}

enum Status{
	Offline, Online, InGame
}
