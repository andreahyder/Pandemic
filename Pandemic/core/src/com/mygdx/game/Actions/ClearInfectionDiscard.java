package com.mygdx.game.Actions;

import com.mygdx.game.GameScreen;

public class ClearInfectionDiscard implements Action{

	@Override
	public void execute(String[] args) {
		GameScreen.ClearInfectionDiscard();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "ClearInfectionDiscard";
	}

}
