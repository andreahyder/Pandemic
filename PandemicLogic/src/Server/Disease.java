package Server;
import java.util.ArrayList;

public class Disease {
	Color color;
	int max;
	Boolean virulent;
	Boolean cured;
	Boolean eradicated;
	ArrayList<DiseaseCube> cubes;
	
	Disease(String s){
		virulent = false;
		cured = false;
		eradicated = false;
		cubes = new ArrayList<DiseaseCube>();
		if(s.matches("black")) {
			max = 24;
			color = Color.black;
			for(int i = 0; i < 24; i++) {
				cubes.add(new DiseaseCube(this));
			}
		}
		else if(s.matches("blue")) {
			max = 24;
			color = Color.blue;
			for(int i = 0; i < 24; i++) {
				cubes.add(new DiseaseCube(this));
			}
		}
		else if(s.matches("purple")) {
			max = 12;
			color = Color.purple;
			for(int i = 0; i < 12; i++) {
				cubes.add(new DiseaseCube(this));
			}
		}
		else if(s.matches("red")) {
			max = 24;
			color = Color.red;
			for(int i = 0; i < 24; i++) {
				cubes.add(new DiseaseCube(this));
			}
		}
		else if(s.matches("yellow")) {
			max = 24;
			color = Color.yellow;
			for(int i = 0; i < 24; i++) {
				cubes.add(new DiseaseCube(this));
			}
		}
	}
}

enum Color{
	black,blue,purple,red,yellow
}
