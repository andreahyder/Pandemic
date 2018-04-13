package Server;

public class PlayerCard {
	Type type;
	City city;
	String name;
	
	PlayerCard(City c, Type t, String n){
		if(t.compareTo(type.City) == 0) {
			city = c;
			type = t;
		}
		else if(t.compareTo(type.Event) == 0 || t.compareTo(type.Virulent) == 0 || t.compareTo(type.Mutation) == 0) {
			name = n;
			type = t;
		}
		else {
			type = t;
		}
	}
}

enum Type{
	City,Epidemic,Event,Virulent,Mutation
}
