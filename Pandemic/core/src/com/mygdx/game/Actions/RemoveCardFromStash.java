package com.mygdx.game.Actions;

import com.mygdx.game.GameScreen;

public class RemoveCardFromStash implements Action{

	@Override
	public void execute(String[] args) {
		GameScreen.RemoveCardFromStash();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "RemoveCubeFromStash";
	}
	

}
