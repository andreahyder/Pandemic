package com.mygdx.game.Actions;

import com.mygdx.game.GameScreen;

public class NotifyTurn implements Action{

	@Override
	public void execute(String[] args) {
		GameScreen.NotifyTurn( args[1], args[2] );
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "NotifyTurn";
	}

}
