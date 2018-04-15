package Server;
import java.util.ArrayList;

public class Disease {
	Color color;
	Boolean virulent;
	Boolean cured;
	ArrayList<DiseaseCube> cubes;
	
	Disease(String s){
		virulent = false;
		cured = false;
		cubes = new ArrayList<DiseaseCube>();
		if(s.matches("black")) {
			color = Color.black;
			for(int i = 0; i < 24; i++) {
				cubes.add(new DiseaseCube(this));
			}
		}
		else if(s.matches("blue")) {
			color = Color.blue;
			for(int i = 0; i < 24; i++) {
				cubes.add(new DiseaseCube(this));
			}
		}
		else if(s.matches("purple")) {
			color = Color.purple;
			for(int i = 0; i < 12; i++) {
				cubes.add(new DiseaseCube(this));
			}
		}
		else if(s.matches("red")) {
			color = Color.red;
			for(int i = 0; i < 24; i++) {
				cubes.add(new DiseaseCube(this));
			}
		}
		else if(s.matches("yellow")) {
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
