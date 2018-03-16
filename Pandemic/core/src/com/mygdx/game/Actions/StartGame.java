package com.mygdx.game.Actions;

import com.mygdx.game.GameScreen;

public class StartGame implements Action{

	@Override
	public void execute(String[] args) {
		GameScreen.StartGame();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "StartGame";
	}

}
