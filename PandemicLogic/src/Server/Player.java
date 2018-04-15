package Server;
import java.util.ArrayList;

public class Player {
	static int IDcount = 0;
	int ID;
	String username;
	String password;
	Status status;
	Game game;
	Boolean ready;
	Pawn pawn;
	ArrayList<PlayerCard> hand;
	
	Player(String n){
		game = null;
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
			Pawn t1 = pawn;
			game.pawns.add(t1);
			pawn = p;
			
			pawn.player = this;
			pawn.city = t1.city;
			pawn.actions = t1.actions;
			pawn.roleactions = t1.roleactions;
			pawn.stash = t1.stash;
			
			t1.city.pawns.remove(t1);
			t1.city = null;
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
