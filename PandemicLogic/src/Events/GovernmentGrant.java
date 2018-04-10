package Events;
import Server.*;

import Server.GameManager;

public class GovernmentGrant implements Event {

	@Override
	public void executeEvent(String[] args) { //params: PlayerIndex/EventAction/GovernmentGrant/TargetCity/CityToRemoveRSFrom
		// TODO Auto-generated method stub
		City targetCity = GameManager.game.getCity(args[3]);
		if (args[4].equals("none")){
			targetCity.addResearchStation();
		}
		else{
			City cityToRemoveRSFrom = GameManager.game.getCity(args[4]);
			cityToRemoveRSFrom.removeResearchStation();
			targetCity.addResearchStation();
		}
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "GovernmentGrant";
	}

}
