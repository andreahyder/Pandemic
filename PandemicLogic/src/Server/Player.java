package Server;
import java.util.ArrayList;

public class Player {
	static int IDcount = 0;
	int ID;
	String username;
	String password;
	Status status;
	Boolean ready;
	Pawn pawn;
	ArrayList<PlayerCard> hand;
	
	Player(String n){
		ID = IDcount;
		IDcount++;
		username = n;
		status = Status.InGame;
		ready = false;
		pawn = null;
		hand = new ArrayList<PlayerCard>();
	}
	
	void share(Player p, int c, Boolean give) {
		p.hand.add(hand.remove(c));
		if(give) {
			pawn.actions--;
		}
		else {
			p.pawn.actions--;
		}
	}
	
	void givePawn(Pawn p, City c){
		if(pawn == null) {
			pawn = p;
			pawn.player = this;
			pawn.city = c;
			if(pawn.city != null) {
				c.pawns.add(p);
			}
		}
		else {
			
		}
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
	public Pawn getPawn(){
		return pawn;
	}
}

enum Status{
	Offline, Online, InGame
}
