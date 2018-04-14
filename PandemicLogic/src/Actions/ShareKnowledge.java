package Actions;

import Server.*;

public class ShareKnowledge implements Action{

	@Override
	public void execute(String[] args) { //params PlayerIndex/ShareKnowledge/TargetName/CityCardName
		GameManager.ShareKnowledge(args[2], args[3]);
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "ShareKnowledge";
	}
	

}
