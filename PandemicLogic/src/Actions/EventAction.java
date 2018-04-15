package Actions;
import Server.GameManager;

public class EventAction implements Action{
	@Override
	public void execute(String[] args) {
		GameManager.EventAction(args);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "EventAction";
	}	
}
