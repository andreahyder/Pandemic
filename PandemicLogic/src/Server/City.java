package Server;
import java.util.ArrayList;

public class City {
	Game game;
	String name;
	ArrayList<Pawn> pawns;
	ArrayList<City> connected;
	Disease disease;
	ArrayList<DiseaseCube> cubes;
	ResearchStation research;
	Boolean outbroken;
	
	City(String n,Disease d,Game g){
		game = g;
		name = n;
		disease = d;
		cubes = new ArrayList<DiseaseCube>();
		research = null;
		connected = new ArrayList<City>();
		pawns = new ArrayList<Pawn>();
		outbroken = false;
	}
	
	void addDiseaseCube(Color color) {
		DiseaseCube t1 = game.getDisease(color).cubes.remove(0);
		cubes.add(t1);
		t1.location = this;
	}
	
	void removeDiseaseCube(Color color) {
		Boolean done = false;
		int count = 0;
		while(!done) {
			if(cubes.get(count).disease.color.compareTo(color) == 0) {
				DiseaseCube t1 = cubes.remove(count);
				t1.disease.cubes.add(t1);
				t1.location = null;
				done = true;
			}
			count++;
		}
	}
	
	int countDiseaseCube(Color color) {
		int count = 0;
		for(DiseaseCube c:cubes) {
			if(c.disease.color.equals(color)) {
				count++;
			}
		}
		return count;
	}
	
	public void addResearchStation(){
		ResearchStation t1 = game.research.remove(0);
		research = t1;
		t1.location = this;
	}
	
	public void removeResearchStation() {
		game.research.add(research);
		research.location = null;
		research = null;
	}
}
