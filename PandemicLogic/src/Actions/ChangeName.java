package Actions;

import Server.*;

public class ChangeName implements Action{

	@Override
	public void execute(String[] args) {
		GameManager.ChangeName(args[0], args[2]);
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "ChangeName";
	}
	
}
