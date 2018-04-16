package com.mygdx.game.Actions;

import com.mygdx.game.GameScreen;

public class RemoveCubeFromStash implements Action{

	@Override
	public void execute(String[] args) {
		GameScreen.RemoveCubeFromStash( args[1] );
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "RemoveCubeFromStash";
	}
	

}
