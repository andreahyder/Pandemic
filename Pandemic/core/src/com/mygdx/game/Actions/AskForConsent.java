package com.mygdx.game.Actions;

import com.mygdx.game.GameScreen;

public class AskForConsent implements Action{

	@Override
	public void execute(String[] args) {
		GameScreen.AskForConsent();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "AskForConsent";
	}

}
