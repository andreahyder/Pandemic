package Actions;

import Server.*;

public class ShareKnowledge implements Action{

	@Override
	public void execute(String[] args) {
		GameManager.ShareKnowledge(args[2]);
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "ShareKnowledge";
	}
	

}
