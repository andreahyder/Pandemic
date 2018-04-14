package Actions;
import Server.GameManager;

public class ToggleSetting implements Action{
	@Override
	public void execute(String[] args) {
		GameManager.ToggleSetting(args);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "ToggleSetting";
	}	
}
