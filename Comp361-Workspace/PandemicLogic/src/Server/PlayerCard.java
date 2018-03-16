package Server;

public class PlayerCard {
	Type type;
	City city;
	
	PlayerCard(City c){
		city = c;
		if(c == null) {
			type = Type.Epidemic;
		}
		else {
			type = Type.City;
		}
	}
}

enum Type{
	City,Epidemic,Event
}
