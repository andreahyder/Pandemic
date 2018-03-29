package Actions;

import Server.*;

public class ToggleReady implements Action{

	@Override
	public void execute(String[] args) {
		GameManager.ToggleReady(args[0]);
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "ToggleReady";
	}
	
}
