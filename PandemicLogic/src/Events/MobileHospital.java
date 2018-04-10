package Events;

import Server.GameManager;

public class MobileHospital implements Event {

	@Override
	public void executeEvent(String[] args) {
		GameManager.game.changeMobileHospitalFlag(true);
		//TODO send flag toggle to client
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "MobileHospital";
	}

}
