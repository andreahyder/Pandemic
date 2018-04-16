package Actions;

import Server.*;

public class BuildQuarantine implements Action{

	@Override
	public void execute(String[] args) {
		GameManager.BuildQuarantine(args[2]);
	}

	@Override
	public String getName() {
		return "BuildQuarantine";
	}
	
}