package com.mygdx.game.Actions;

import com.mygdx.game.GameScreen;

public class RemovePlayerCardFromDiscard implements Action{

	@Override
	public void execute(String[] args) {
		GameScreen.RemoveCardFromInfectionDiscard( args[1] );			
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "RemoveCardFromInfectionDiscard";
	}

}
