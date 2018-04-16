package Actions;

import Server.*;

public class Capture implements Action{

	@Override
	public void execute(String[] args) {
		GameManager.Capture();
		
	}

	@Override
	public String getName() {
		return "Capture";
	}
	
}
