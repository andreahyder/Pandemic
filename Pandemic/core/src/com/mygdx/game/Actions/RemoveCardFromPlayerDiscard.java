package com.mygdx.game.Actions;

import com.mygdx.game.GameScreen;

public class RemoveCardFromPlayerDiscard implements Action{

	@Override
	public void execute(String[] args) {
		GameScreen.RemoveCardFromPlayerDiscard( args[1] );			
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "RemoveCardFromPlayerDiscard";
	}

}
