package Actions;

import Server.*;

public class CreateGame implements Action{

	@Override
	public void execute(String[] args) {
		GameManager.CreateGame(args[0]);
		
	}

	@Override
	public String getName() {
		return "CreateGame";
	}
	
}
