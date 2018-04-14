package Server;

public class PlayerCard {
	Type type;
	City city;
	String name;
	
	PlayerCard(City c, Type t, String n){
		if(t.compareTo(Type.City) == 0) {
			city = c;
			type = t;
		}
		else if(t.compareTo(Type.Event) == 0 || t.compareTo(Type.Virulent) == 0 || t.compareTo(Type.Mutation) == 0) {
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
