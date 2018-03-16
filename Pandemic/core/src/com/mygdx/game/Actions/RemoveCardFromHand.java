package com.mygdx.game.Actions;

import com.mygdx.game.GameScreen;

public class RemoveCardFromHand implements Action{

	@Override
	public void execute(String[] args) {
		GameScreen.RemoveCardFromHand( args[1], args[2], args[3] );
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "RemoveCardFromHand";
	}
	
}
