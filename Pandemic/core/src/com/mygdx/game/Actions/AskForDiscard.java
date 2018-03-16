package com.mygdx.game.Actions;

import com.mygdx.game.GameScreen;

public class AskForDiscard implements Action {

	@Override
	public void execute(String[] args) {
		GameScreen.AskForDiscard();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "AskForDiscard";
	}

}
