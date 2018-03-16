package com.mygdx.game.Actions;

import com.mygdx.game.GameScreen;

public class UpdatePlayerLocation implements Action{

	@Override
	public void execute(String[] args) {
		GameScreen.UpdatePlayerLocation( args[1], args[2] );
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "UpdatePlayerLocation";
	}
	

}
