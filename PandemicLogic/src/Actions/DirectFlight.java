package Actions;

import Server.*;

public class DirectFlight implements Action{

	@Override
	public void execute(String[] args) {
		GameManager.DirectFlight(args[2],args[3]);
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "DirectFlight";
	}

}
