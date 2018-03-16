package com.mygdx.game.Actions;

import com.mygdx.game.GameScreen;

public class IncInfectionRate implements Action {

	@Override
	public void execute(String[] args) {
		GameScreen.IncInfectionRate();
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "IncInfectionRate";
	}
	
}
