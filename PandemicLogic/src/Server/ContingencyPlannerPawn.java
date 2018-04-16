package Server;

public class ContingencyPlannerPawn extends Pawn {
	private PlayerCard heldEvent = null;
	ContingencyPlannerPawn(Role r) {
		super(r);
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
		ServerComm.sendToAllClients("RemoveCardFromStash/");
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
				ServerComm.sendToAllClients("RemoveCardFromPlayerDiscard/" + args[4] + "/");
				break;
			}
		}
	}
}
