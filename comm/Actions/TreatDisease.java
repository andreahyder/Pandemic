package Actions;

public class TreatDisease implements Action{

	@Override
	public void execute(String[] args) {
		GameManager.TreatDisease(args[2]);
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "TreatDisease";
	}

}
