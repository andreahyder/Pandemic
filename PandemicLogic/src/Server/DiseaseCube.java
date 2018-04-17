package Server;

import java.io.Serializable;

public class DiseaseCube implements Serializable{
	Disease disease;
	Pawn held;
	City location;
	
	DiseaseCube(Disease d){
		disease = d;
		held = null;
		location = null;
	}
}
