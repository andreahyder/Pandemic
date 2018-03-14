
public class Pawn {
	int actions;
	City city;
	
	Pawn(City c){
		actions = 4;
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
