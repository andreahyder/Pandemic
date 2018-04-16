package com.mygdx.game.Actions;

import com.mygdx.game.GameScreen;

public class RemoveQuarantine implements Action{

	@Override
	public void execute(String[] args) {
		GameScreen.RemoveQuarantine( args[1] );		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "RemoveQuarantine";
	}
	
}
