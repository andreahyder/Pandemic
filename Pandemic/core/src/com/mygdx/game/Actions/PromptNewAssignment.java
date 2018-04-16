package com.mygdx.game.Actions;

import com.mygdx.game.GameScreen;

public class PromptNewAssignment implements Action{

	@Override
	public void execute(String[] args) {
		GameScreen.SpecialOrdersActivate( args[1] );
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "SpecialOrdersActivate";
	}
	

}
