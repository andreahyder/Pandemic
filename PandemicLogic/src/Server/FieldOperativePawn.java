package Server;

import java.util.ArrayList;

public class FieldOperativePawn extends Pawn {
	ArrayList<DiseaseCube> FOStash;
	FieldOperativePawn(Role r) {
		super(r);
		FOStash = new ArrayList<DiseaseCube>();
	}
	public void addToStash(Color c){
		for(DiseaseCube cube: super.city.cubes){
			if(cube.disease.color==c){
				FOStash.add(cube);
				super.city.cubes.remove(cube);
				for(int j = 0; j< player.game.players.size(); j++){
					ServerComm.sendMessage("RemoveCube/" + super.city.name + "/" + c + "/" + 1 + "/", j);
				}
				for(int j = 0; j< player.game.players.size(); j++){
					ServerComm.sendMessage("AddCubeToStash/" + c.name(), j);
				}
			}
		}
	}
	//index/RoleAction/FieldOperative/Add-Cure/Color/Cards,List
	public void discoverCure(String[] args){
		String[] cardsToDiscard = args[5].split(",");
		for(int i = 0; i<3; i++){
			for(DiseaseCube c: FOStash){
				if(c.disease.color==Color.valueOf(args[4])){
					FOStash.remove(c);
					for(int j = 0; j< player.game.players.size(); j++){
					ServerComm.sendMessage("RemoveCubeFromStash/"+args[4], j);
				}
					c.disease.cubes.add(c);
					break;
				}
			}
		}
		for(String s: cardsToDiscard){
			for(PlayerCard c: super.player.hand){
				if(c.city.name.equals(s)){
					super.player.hand.remove(c);
					GameManager.game.playerDiscardPile.add(c);
					for(int j = 0; j< player.game.players.size(); j++){
					ServerComm.sendMessage("RemoveCardFromHand"+super.player.username+"/"+c.city.name+"/true/", j);
				}
				}
			}
		}
		GameManager.game.getDisease(Color.valueOf(args[4])).cured = true;
	}
	
}
