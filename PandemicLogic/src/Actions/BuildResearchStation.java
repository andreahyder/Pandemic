package Actions;

import Server.*;

public class BuildResearchStation implements Action{
	public void execute(String[] args){
		GameManager.BuildResearchStation(args[2]);
	}

	@Override
	public String getName() {
		return "BuildResearchStation";
	}
	
}
