package Actions;

import Server.*;

public class Print implements Action{

	@Override
	public void execute(String[] args) {
		System.out.print(args[2]);		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Print";
	}
	
}
