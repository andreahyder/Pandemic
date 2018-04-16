package com.mygdx.game.Actions;

import com.mygdx.game.GameScreen;

public class Capture implements Action{

	@Override
	public void execute(String[] args) {
		GameScreen.Capture();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Capture";
	}
	

}
