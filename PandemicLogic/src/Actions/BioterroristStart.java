package Actions;

import Server.*;

public class BioterroristStart implements Action{

	@Override
	public void execute(String[] args) {
		GameManager.BioterroristStart(args[2]);
		
	}

	@Override
	public String getName() {
		return "BioterroristStart";
	}
	
}
