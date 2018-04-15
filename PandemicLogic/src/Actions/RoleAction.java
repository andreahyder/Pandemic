package Actions;

import Server.*;

public class RoleAction implements Action{

	@Override
	public void execute(String[] args) {
		GameManager.RoleAction(args);
	}

	@Override
	public String getName() {
		return "RoleAction";
	}
	
}