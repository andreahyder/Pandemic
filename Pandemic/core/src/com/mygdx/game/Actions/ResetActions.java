package com.mygdx.game.Actions;

import com.mygdx.game.GameScreen;

public class ResetActions implements Action{

	@Override
	public void execute(String[] args) {
		GameScreen.ResetActions( );
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "ResetActions";
	}
	

}
