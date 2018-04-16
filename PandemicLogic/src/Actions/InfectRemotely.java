package Actions;

import Server.*;

public class InfectRemotely implements Action{

	@Override
	public void execute(String[] args) {
		GameManager.InfectRemotely(args[2]);
	}

	@Override
	public String getName() {
		return "InfectRemotely";
	}
	
}
