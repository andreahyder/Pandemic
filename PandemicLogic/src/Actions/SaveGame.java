package Actions;

import Server.*;

public class SaveGame implements Action{
	//index/SaveGame/saveName
	public void execute(String[] args){
		GameManager.SaveGame(args[2]);
	}

	@Override
	public String getName() {
		return "SaveGame";
	}
	
}