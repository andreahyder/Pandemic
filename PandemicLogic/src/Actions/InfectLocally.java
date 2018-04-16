package Actions;

import Server.*;

public class InfectLocally implements Action{

	@Override
	public void execute(String[] args) {
		GameManager.InfectLocally();
	}

	@Override
	public String getName() {
		return "InfectLocally";
	}
	
}
