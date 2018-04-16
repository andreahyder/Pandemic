package com.mygdx.game.Actions;

import com.mygdx.game.GameScreen;

public class DecrementActionsBio implements Action{

	@Override
	public void execute(String[] args) {
		GameScreen.DecrementActionsBio();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "DecrementActionsBio";
	}
	

}
