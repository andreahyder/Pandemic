package Server;

public class Pawn {
	Role role;
	int actions;
	Player player;
	City city;
	
	Pawn(Role r){
		role = r;
		actions = 4;
		if(role.compareTo(Role.Gen) == 0) {
			actions = 5;
		}
		player = null;
		city = null;
	}
	
	void move(City c, Boolean free) {
		boolean Q = (role.compareTo(role.Qua) == 0);
		if(Q) {
			city.QS = false;
			for(City t: city.connected) {
				t.QS = false;
			}
		}
		
		city.pawns.remove(this);
		city = c;
		city.pawns.add(this);
		if(!free){
			actions--;
		}
		
		if(Q) {
			city.QS = true;
			for(City t: city.connected) {
				t.QS = true;
			}
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

enum Role{
	Arch, Bio, Col, ContS, ContP, Disp, Epi, Field, Gen, Med, Op, Qua, Res, Sci, Troub
}
