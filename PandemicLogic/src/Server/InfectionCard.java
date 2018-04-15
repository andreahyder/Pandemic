package Server;

public class InfectionCard {
	Type type;
	City city;
	
	InfectionCard(City c, Type t){
		if(t.compareTo(Type.City) == 0) {
			city = c;
			type = t;
		}
		else{
			city = null;
			type = t;
		}
	}
}
