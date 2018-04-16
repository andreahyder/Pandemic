package Actions;

import Server.*;

public class BuildQuarantine implements Action{

	@Override
	public void execute(String[] args) {
		if(args.length < 3){
			GameManager.BuildQuarantine("none");
		}
		else{
			GameManager.BuildQuarantine(args[2]);
		}
		
	}

	@Override
	public String getName() {
		return "BuildQuarantine";
	}
	
}