package Actions;

import Server.*;

public class SaveGame implements Action{
	public void execute(String[] args){
		GameManager.SaveGame();
	}

	@Override
	public String getName() {
		return "SaveGame";
	}
	
}