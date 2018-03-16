package com.mygdx.game.Actions;

import com.mygdx.game.GameScreen;

public class IncOutbreakCounter implements Action{

	@Override
	public void execute(String[] args) {
		GameScreen.IncOutbreakCounter();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "IncOutbreakCounter";
	}

}
