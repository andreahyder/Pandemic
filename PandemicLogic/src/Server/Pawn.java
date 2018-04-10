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
	
	void move(City c, Boolean free) {
		city.pawns.remove(this);
		city = c;
		city.pawns.add(this);
		if(!free){
			actions--;
		}
	}
	
	void treat(Color c, Boolean free) {
		city.removeDiseaseCube(c);
		if(!free){
			actions--;
		}
	}
	/*void mobileHospitalMove(City c, Color d) {
		city.pawns.remove(this);
		city = c;
		city.pawns.add(this);
		city.removeDiseaseCube(d);
		actions--;
	}*/
	public void incrementAction(int i){
		actions += i;
	}
}
