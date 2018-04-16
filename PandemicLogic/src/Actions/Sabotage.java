package Actions;

import Server.*;

public class Sabotage implements Action{

	@Override
	public void execute(String[] args) {
		GameManager.Sabotage(args[2]);
	}

	@Override
	public String getName() {
		return "Sabotage";
	}
	
}
