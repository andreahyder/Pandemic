package Actions;

import Server.*;

public class BioterroristDraw implements Action{

	@Override
	public void execute(String[] args) {
		GameManager.BioterroristDraw();
	}

	@Override
	public String getName() {
		return "BioterroristDraw";
	}
	
}
