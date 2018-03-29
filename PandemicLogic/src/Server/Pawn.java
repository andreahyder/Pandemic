package Server;

public class Pawn {
	int actions;
	Player player;
	City city;
	
	Pawn(Player p, City c){
		actions = 4;
		player = p;
		city = c;
		c.pawns.add(this);
	}
	
	void move(City c) {
		city.pawns.remove(this);
		city = c;
		city.pawns.add(this);
		actions--;
	}
	
	void treat(Color c) {
		city.removeDiseaseCube(c);
		actions--;
	}
}
