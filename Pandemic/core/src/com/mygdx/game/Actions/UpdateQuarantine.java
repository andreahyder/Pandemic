package com.mygdx.game.Actions;

import com.mygdx.game.GameScreen;

public class UpdateQuarantine implements Action{

	@Override
	public void execute(String[] args) {
		GameScreen.UpdateQuarantine( args[1], args[2] );
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "UpdateQuarantine";
	}
	

}
