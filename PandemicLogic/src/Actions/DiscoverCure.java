package Actions;

import Server.*;

public class DiscoverCure implements Action{
	public void execute(String[] args){
		GameManager.DiscoverCure(args[2],args[3],args[4],args[5],args[6],args[7]);
	}

	@Override
	public String getName() {
		return "DiscoverCure";
	}
	
}

