package Actions;

import Server.*;

public class JoinGame implements Action{

	@Override
	public void execute(String[] args) {
		GameManager.JoinGame(args[0]);
		
	}

	@Override
	public String getName() {
		return "JoinGame";
	}
	
}
