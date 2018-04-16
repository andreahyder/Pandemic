package com.mygdx.game.Actions;

import com.mygdx.game.GameScreen;

public class UpdateRole implements Action{

	@Override
	public void execute(String[] args) {
		GameScreen.UpdateRole( args[1], args[2] );
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "UpdateRole";
	}
	

}
