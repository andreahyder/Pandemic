package Actions;

import Server.*;

public class CureDisease implements Action{
	public void execute(String[] args){
		GameManager.CureDisease(args);
	}

	@Override
	public String getName() {
		return "CureDisease";
	}
	
}

