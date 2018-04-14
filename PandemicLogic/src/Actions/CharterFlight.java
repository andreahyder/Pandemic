package Actions;

import Server.*;

public class CharterFlight implements Action{

	@Override
	public void execute(String[] args) {
		GameManager.CharterFlight(args[2],args[3]);
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "CharterFlight";
	}

}