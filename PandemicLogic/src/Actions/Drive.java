package Actions;

import Server.*;

public class Drive implements Action{
	public void execute(String[] args){
		GameManager.Drive(args[2], args[3]);
	}

	@Override
	public String getName() {
		return "Drive";
	}
	
}
