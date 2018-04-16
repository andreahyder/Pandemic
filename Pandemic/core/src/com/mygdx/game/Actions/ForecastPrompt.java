package com.mygdx.game.Actions;

import com.mygdx.game.GameScreen;

public class ForecastPrompt implements Action{

	@Override
	public void execute(String[] args) {
		GameScreen.ForecastPrompt(args[1]);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "ForecastPrompt";
	}

}
