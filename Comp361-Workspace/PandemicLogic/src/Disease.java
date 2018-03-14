import java.util.ArrayList;

public class Disease {
	Color color;
	ArrayList<DiseaseCube> cubes;
	
	Disease(String s){
		cubes = new ArrayList<DiseaseCube>();
		if(s.matches("Black")) {
			color = Color.Black;
			for(int i = 0; i < 24; i++) {
				cubes.add(new DiseaseCube(this));
			}
		}
		else if(s.matches("Blue")) {
			color = Color.Blue;
			for(int i = 0; i < 24; i++) {
				cubes.add(new DiseaseCube(this));
			}
		}
		else if(s.matches("Purple")) {
			color = Color.Purple;
			for(int i = 0; i < 12; i++) {
				cubes.add(new DiseaseCube(this));
			}
		}
		else if(s.matches("Red")) {
			color = Color.Red;
			for(int i = 0; i < 24; i++) {
				cubes.add(new DiseaseCube(this));
			}
		}
		else if(s.matches("Yellow")) {
			color = Color.Yellow;
			for(int i = 0; i < 24; i++) {
				cubes.add(new DiseaseCube(this));
			}
		}
	}
}

enum Color{
	Black,Blue,Purple,Red,Yellow
}
