package Actions;

import Server.GameManager;

public class Chat implements Action {

	@Override
	public void execute(String[] args) {
		GameManager.chat(args);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Chat";
	}

}
