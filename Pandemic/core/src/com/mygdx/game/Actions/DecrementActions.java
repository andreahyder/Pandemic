package com.mygdx.game.Actions;

import com.mygdx.game.GameScreen;

public class DecrementActions implements Action {

		
	@Override
	public void execute(String[] args) {
		GameScreen.DecrementActions();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "DecrementActions";
	}

}