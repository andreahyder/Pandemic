package Server;

import java.util.ArrayList;

public class Pawn {
	Role role;
	int actions;
	int roleactions;
	Player player;
	City city;
	
	boolean captured;
	
	ArrayList<DiseaseCube> stash;
	PlayerCard card;
	
	Pawn(Role r){
		captured = false;
		stash = new ArrayList<DiseaseCube>();
		card = null;
		role = r;
		actions = 4;
		roleactions = 1;
		if(role.compareTo(Role.Generalist) == 0) {
			actions = 5;
		}
		if(role.compareTo(Role.Bioterrorist) == 0) {
			actions = 2;
		}
		player = null;
		city = null;
	}
	
	void move(City c, Boolean free) {
		
		boolean Q = (role.compareTo(Role.QuarantineSpecialist) == 0);
		boolean C = (role.compareTo(Role.Colonel) == 0);
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
		
		if(C) {
			if(city.quarantine == 1) {
				city.quarantine = 2;
				
				//update quarantine count for all players
				ServerComm.sendToAllClients("UpdateQuarantine/"+city.name+"/2/");
			}
		}
		if(role==Role.Medic){
			//Check for cured diseases and remove their cubes. If all removed turn on eradicated
			for(Color color:Color.values()){
				if(GameManager.game.getDisease(color).cured){
					int noCubesToRemove = city.countDiseaseCube(color);
					treat(color, noCubesToRemove, true);
				}
			}
		}
		if(role==Role.ContainmentSpecialist){
			for(Color color: Color.values()){
				int cubeCount = city.countDiseaseCube(color);
				if (cubeCount>=2){
					treat(color, 1, true);
				}
			}
		}
	}
	
	void treat(Color c, int count, Boolean free) {
		for(int i = 0; i < count; i++) {
			city.removeDiseaseCube(c);
		}
		if(player.game.getDisease(c).cubes.size() == player.game.getDisease(c).max && player.game.getDisease(c).cured) {
			player.game.getDisease(c).eradicated = true;
			
			String mes = "EradicateDisease/" + c + "/";
			for(int j = 0; j < player.game.players.size(); j++) {
				ServerComm.sendMessage(mes, j);
			}
		}
		if(!free){
			actions--;
		}
	}
	
	void stock(Color c) {
		city.removeDiseaseCube(c);
		DiseaseCube t1 = player.game.getDisease(c).cubes.remove(0);
		stash.add(t1);
	}
	
	void useStash(Color c) {
		int count = 0;
		while(count < 3) {
			for(int i = 0; i < stash.size(); i++) {
				if(stash.get(i).disease.color.equals(c)) {
					count++;
					player.game.getDisease(c).cubes.add(stash.remove(i));
					if(player.game.getDisease(c).cubes.size() == player.game.getDisease(c).max && player.game.getDisease(c).cured) {
						player.game.getDisease(c).eradicated = true;
						String mes = "EradicateDisease/" + c + "/";
						for(int j = 0; j < player.game.players.size(); j++) {
							ServerComm.sendMessage(mes, j);
						}
					}
					break;
				}
			}
		}
	}
}

enum Role{
	Archivist, Bioterrorist, Colonel, ContainmentSpecialist, ContingencyPlanner, Dispatcher, Epidemiologist, FieldOperative, Generalist, Medic, OperationsExpert, QuarantineSpecialist, FirstResponder, Scientist, Troubleshooter
}
