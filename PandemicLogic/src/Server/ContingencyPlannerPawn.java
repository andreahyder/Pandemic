package Server;

import java.util.ArrayList;

public class ContingencyPlannerPawn extends Pawn {
	PlayerCard heldEvent = null;
	ArrayList<String> playedEvents;
	ContingencyPlannerPawn(Role r) {
		super(r);
		playedEvents = new ArrayList<String>();
	}
	public void takeEvent(PlayerCard event){
		if(heldEvent == null){
			heldEvent = event;
			super.actions--;
		}
	}
	//index/RoleAction/ContingencyPlanner/Take or Play/targetCard/args for target card
	//params: PlayerIndex/EventAction/Forecast
	public void playEvent(String[] args){
		for(int j = 0; j< player.game.players.size(); j++){
					ServerComm.sendMessage("RemoveCardFromStash/", j);
				}
		heldEvent = null;
		String[] newargs = new String[args.length];
		newargs[0] = args[0];
		newargs[1] = "EventAction";
		for (int i = 4; i<args.length; i++){
			newargs[i-2] = args[i];
		}
		GameManager.EventAction(newargs);
		for(PlayerCard c: GameManager.game.playerDiscardPile){
			if(c.name.equals(args[4])){
				GameManager.game.playerDiscardPile.remove(c);
				for(int j = 0; j< player.game.players.size(); j++){
					ServerComm.sendMessage("RemoveCardFromPlayerDiscard/" + args[4] + "/", j);
				}
				break;
			}
		}
	}
	public void addToPlayed(String s){
		playedEvents.add(s);
	}
}
