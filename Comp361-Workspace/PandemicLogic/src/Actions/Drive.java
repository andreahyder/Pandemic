package Actions;

public class Drive implements Action{
	public void execute(String[] args){
		GameManager.drive(args[2]);
	}

	@Override
	public String getName() {
		return "Drive";
	}
	
}